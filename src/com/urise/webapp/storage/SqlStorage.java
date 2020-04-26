package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;
import com.urise.webapp.util.SqlHelper;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;
    private SqlHelper sqlHelper = new SqlHelper();

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        sqlHelper.executeQuery(connectionFactory, preparedStatement -> {
            preparedStatement.execute();
            return null;
        }, "DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.executeQuery(connectionFactory, preparedStatement -> {
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, resultSet.getString("full_name"));
        }, "SELECT * FROM resume r WHERE r.uuid =?");
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.executeQuery(connectionFactory, preparedStatement -> {
            preparedStatement.setString(1, resume.getFullName());
            preparedStatement.setString(2, resume.getUuid());
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
            return null;
        }, "UPDATE resume set full_name = ? where uuid =?");
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.executeQuery(connectionFactory, preparedStatement -> {
            preparedStatement.setString(1, resume.getUuid());
            preparedStatement.setString(2, resume.getFullName());
            preparedStatement.execute();
            return null;
        }, "INSERT INTO resume (uuid, full_name) values (?,?)");
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.executeQuery(connectionFactory, preparedStatement -> {
            preparedStatement.setString(1, uuid);
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        }, "DELETE FROM resume where uuid = ?");
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.executeQuery(connectionFactory, preparedStatement -> {
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (!resultSet.next()) {
                        throw new StorageException("Empty table", null, null);
                    }
                    List<Resume> resumeList = new ArrayList<>();
                    do {
                        resumeList.add(new Resume(resultSet.getString("uuid").trim(), resultSet.getString("full_name")));
                    } while (resultSet.next());
                    return resumeList;
                },
                "select * from resume order by full_name asc");
    }

    @Override
    public int size() {
        return sqlHelper.executeQuery(connectionFactory, preparedStatement -> {
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (!resultSet.next()) {
                        throw new StorageException("Empty table", null, null);
                    }
                    return resultSet.getInt(1);
                },
                "SELECT COUNT(*) FROM resume");
    }
}
