package com.urise.webapp.util;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class SqlHelper {

    public <T> T executeQuery(ConnectionFactory connectionFactory, GetStatement<T> getStatement, String query) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            return getStatement.getResultSet(preparedStatement);
        } catch (Exception e) {
            if (e instanceof NotExistStorageException) {
                throw new NotExistStorageException(e);
            }
            if (e instanceof ExistStorageException) {
                throw new ExistStorageException(e);
            }
            throw new StorageException(e);
        }
    }
}
