package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.AbstractSection;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.SectionType;
import com.urise.webapp.sql.SqlHelper;
import com.urise.webapp.util.JsonParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
        return sqlHelper.executeQuery(preparedStatement -> {
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume resume = new Resume(uuid, resultSet.getString("full_name"));
            do {
                String type = resultSet.getString("type");
                if (type != null) {
                    addContactAndSections(type, resume, resultSet);
                }
            } while (resultSet.next());
            return resume;
        }, "    SELECT * FROM ( " +
                "     SELECT * FROM resume r LEFT JOIN contact c ON r.uuid = c.resume_uuid " +
                "        union " +
                "     select * from resume r left join section s on r.uuid = s.resume_uuid " +
                "        ) A " +
                "     WHERE uuid = ? ");
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
                    deleteRaws(conn, resume, "DELETE from contact where resume_uuid = ?");
                    deleteRaws(conn, resume, "DELETE from section where resume_uuid = ?");
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
        return sqlHelper.executeQuery(preparedStatement -> {
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (!resultSet.next()) {
                        return new ArrayList<>();
                    }
                    Map<String, Resume> resumeMap = new LinkedHashMap<>();
                    do {
                        String uuid = resultSet.getString("uuid").trim();
                        String full_name = resultSet.getString("full_name");
                        Resume resume = resumeMap.computeIfAbsent(uuid, s -> new Resume(uuid, full_name));
                        String type = resultSet.getString("type");
                        if (type != null) {
                            addContactAndSections(type, resume, resultSet);
                        }
                    } while (resultSet.next());
                    return new ArrayList<>(resumeMap.values());
                },
                "    SELECT * FROM ( " +
                        "     SELECT * FROM resume r LEFT JOIN contact c ON r.uuid = c.resume_uuid " +
                        "        union " +
                        "     select * from resume r left join section s on r.uuid = s.resume_uuid " +
                        "        ) A " +
                        "     ORDER BY full_name, uuid");
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

    private void deleteRaws(Connection conn, Resume resume, String query) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(query)) {
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
                    AbstractSection abstractSection = sectionEntry.getValue();
                    ps.setString(2, JsonParser.write(abstractSection, AbstractSection.class));
                    ps.addBatch();
                }
                ps.executeBatch();
            }
        }
    }

    private void addContactAndSections(String type, Resume resume, ResultSet resultSet) throws SQLException {
        if (contactOrSectionCheck(type)) {
            resume.addSection(SectionType.valueOf(type), JsonParser.read(resultSet.getString("value"), AbstractSection.class));
        } else {
            resume.addContact(ContactType.valueOf(type), resultSet.getString("value"));
        }
    }

    private boolean contactOrSectionCheck(String type) {
        for (SectionType sectionType : SectionType.values()) {
            if (sectionType.name().equals(type)) {
                return true;
            }
        }
        return false;
    }
}
