package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.util.SqlHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
            return new Resume(uuid, resultSet.getString("full_name"));
        }, "SELECT * FROM resume r WHERE r.uuid =?");
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.executeQuery(preparedStatement -> {
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
        try {
            sqlHelper.executeQuery(preparedStatement -> {
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.setString(2, resume.getFullName());
                preparedStatement.execute();
                return null;
            }, "INSERT INTO resume (uuid, full_name) values (?,?)");
        } catch (RuntimeException e) {
            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);
            if (rootCause instanceof SQLException) {
                if ("23505".equals(((SQLException) rootCause).getSQLState())) {
                    throw new ExistStorageException(e);
                }
            }
        }
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
                        throw new StorageException("Empty table", null, null);
                    }
                    List<Resume> resumeList = new ArrayList<>();
                    do {
                        resumeList.add(new Resume(resultSet.getString("uuid").trim(), resultSet.getString("full_name")));
                    } while (resultSet.next());
                    return resumeList;
                },
                "select * from resume order by full_name, uuid asc");
    }

    @Override
    public int size() {
        return sqlHelper.executeQuery(preparedStatement -> {
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (!resultSet.next()) {
                        throw new StorageException("Empty table", null, null);
                    }
                    return resultSet.getInt(1);
                },
                "SELECT COUNT(*) FROM resume");
    }
}
