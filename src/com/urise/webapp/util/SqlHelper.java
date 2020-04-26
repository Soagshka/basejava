package com.urise.webapp.util;

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
            throw new StorageException(e);
        }
    }
}
