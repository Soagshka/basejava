package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

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

    protected void deleteContacts(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE from contact where resume_uuid = ?")) {
            ps.setString(1, resume.getUuid());
            ps.executeUpdate();
        }
    }

    protected void insertContacts(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (type, value, resume_uuid) VALUES (?,?,?)")) {
            if (resume.getContactMap() != null) {
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
                String contactType = resultSet.getString("type");
                if (contactType != null) {
                    String value = resultSet.getString("value");
                    ContactType type = ContactType.valueOf(contactType);
                    resume.addContact(type, value);
                }
            } while (resultSet.next());
            return resume;
        }, "    SELECT * FROM resume r " +
                "     LEFT JOIN contact c " +
                "        ON r.uuid = c.resume_uuid " +
                "     WHERE r.uuid =? ");
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
                    deleteContacts(conn, resume);
                    insertContacts(conn, resume);
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
                        Resume resume = resumeMap.computeIfAbsent(uuid, s -> {
                            try {
                                return new Resume(uuid, resultSet.getString("full_name"));
                            } catch (SQLException throwables) {
                                throw new StorageException(throwables);
                            }
                        });
                        resume.addContact(ContactType.valueOf(resultSet.getString("type")), resultSet.getString("value"));
                    } while (resultSet.next());
                    return new ArrayList<>(resumeMap.values());
                },
                "    SELECT * FROM resume r " +
                        "     LEFT JOIN contact c " +
                        "     ON r.uuid = c.resume_uuid " +
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
}
