package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.sql.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private SqlHelper sqlHelper = new SqlHelper();

    @Override
    public void clear() {
        sqlHelper.executeQuery(preparedStatement -> {
            preparedStatement.execute();
            return null;
        }, "DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        Resume resumeMain = sqlHelper.executeQuery(preparedStatement -> {
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume resume = new Resume(uuid, resultSet.getString("full_name"));
            do {
                String type = resultSet.getString("type");
                if (type != null) {
                    String value = resultSet.getString("value");
                    resume.addContact(ContactType.valueOf(type), value);
                }
            } while (resultSet.next());
            return resume;
        }, "    SELECT * FROM resume r " +
                "     LEFT JOIN contact c " +
                "        ON r.uuid = c.resume_uuid " +
                "     WHERE r.uuid =? ");

        getSection(resumeMain);
        return resumeMain;
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("UPDATE resume set full_name = ? where uuid =?")) {
                        ps.setString(1, resume.getFullName());
                        ps.setString(2, resume.getUuid());
                        if (ps.executeUpdate() == 0) {
                            throw new NotExistStorageException(resume.getUuid());
                        }
                    }
                    deleteContacts(conn, resume, "contact");
                    deleteContacts(conn, resume, "section");
                    insertContacts(conn, resume);
                    insertSection(conn, resume);
                    return null;
                }
        );
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                        ps.setString(1, resume.getUuid());
                        ps.setString(2, resume.getFullName());
                        ps.execute();
                    }
            insertContacts(conn, resume);
            insertSection(conn, resume);
                    return null;
                }
        );
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.executeQuery(preparedStatement -> {
            preparedStatement.setString(1, uuid);
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        }, "DELETE FROM resume where uuid = ?");
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = sqlHelper.executeQuery(preparedStatement -> {
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (!resultSet.next()) {
                        return new ArrayList<>();
                    }
                    List<Resume> resumeList = new ArrayList<>();
                    do {
                        resumeList.add(new Resume(resultSet.getString("uuid").trim(), resultSet.getString("full_name")));
                    } while (resultSet.next());
                    return new ArrayList<>(resumeList);
                },
                "    SELECT * FROM resume " +
                        "     ORDER BY full_name, uuid");
        for (Resume resume : resumes) {
            sqlHelper.executeQuery(preparedStatement -> {
                        preparedStatement.setString(1, resume.getUuid());
                        ResultSet resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                            resume.addContact(ContactType.valueOf(resultSet.getString("type")), resultSet.getString("value"));
                        }
                        return null;
                    },
                    "    SELECT * FROM contact " +
                            "     WHERE resume_uuid = ? " +
                            "     ORDER BY resume_uuid ");
            getSection(resume);
        }
        return resumes;
    }

    @Override
    public int size() {
        return sqlHelper.executeQuery(preparedStatement -> {
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (!resultSet.next()) {
                        return 0;
                    }
                    return resultSet.getInt(1);
                },
                "SELECT COUNT(*) FROM resume");
    }

    private void deleteContacts(Connection conn, Resume resume, String tableName) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE from " + tableName + " where resume_uuid = ?")) {
            ps.setString(1, resume.getUuid());
            ps.executeUpdate();
        }
    }

    private void insertContacts(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (type, value, resume_uuid) VALUES (?,?,?)")) {
            if (!resume.getContactMap().isEmpty()) {
                for (Map.Entry<ContactType, String> contact : resume.getContactMap().entrySet()) {
                    ps.setString(1, contact.getKey().name());
                    ps.setString(2, contact.getValue());
                    ps.setString(3, resume.getUuid());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
        }
    }

    private void insertSection(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (type, value, resume_uuid) VALUES (?,?,?)")) {
            if (!resume.getSectionMap().isEmpty()) {
                for (Map.Entry<SectionType, AbstractSection> sectionEntry : resume.getSectionMap().entrySet()) {
                    ps.setString(1, sectionEntry.getKey().name());
                    ps.setString(3, resume.getUuid());
                    switch (sectionEntry.getKey()) {
                        case PERSONAL:
                        case OBJECTIVE:
                            insertSectionType(sectionEntry.getValue(), section -> ps.setString(2, ((SimpleTextSection) section).getInformation()));
                            ps.addBatch();
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            insertSectionType(sectionEntry.getValue(), section -> {
                                StringBuilder stringBuilder = new StringBuilder();
                                for (String stringValue : ((ListTextSection) section).getInformation()) {
                                    stringBuilder.append(stringValue);
                                    stringBuilder.append("\n");
                                }
                                ps.setString(2, stringBuilder.toString());
                            });
                            ps.addBatch();
                            break;
                        case EDUCATION:
                        case EXPERIENCE:
                            break;
                    }
                }
                ps.executeBatch();
            }
        }
    }

    void getSection(Resume resume) {
        sqlHelper.executeQuery(preparedStatement -> {
            preparedStatement.setString(1, resume.getUuid());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageException(resume.getUuid());
            }
            do {
                String type = resultSet.getString("type");
                if (type != null) {
                    resume.addSection(SectionType.valueOf(type), getSectionDependsOnSectionType(resultSet, SectionType.valueOf(type)));
                }
            } while (resultSet.next());
            return resume;
        }, "    SELECT * from section c " +
                "     WHERE resume_uuid =? ");
    }

    private AbstractSection getSectionDependsOnSectionType(ResultSet resultSet, SectionType sectionType) throws SQLException {
        AbstractSection abstractSection = null;
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                abstractSection = new SimpleTextSection(resultSet.getString("value"));
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                abstractSection = new ListTextSection(Arrays.asList(resultSet.getString("value").split("\n")));
                break;
            case EDUCATION:
            case EXPERIENCE:
                break;
        }
        return abstractSection;
    }

    private <T> void insertSectionType(T section, SectionInsertStrategy sectionInsertStrategy) throws SQLException {
        sectionInsertStrategy.insertSectionDependsOnType(section);
    }

    private interface SectionInsertStrategy<T> {
        void insertSectionDependsOnType(T section) throws SQLException;
    }
}
