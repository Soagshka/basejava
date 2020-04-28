package com.urise.webapp.util;

import com.urise.webapp.Config;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    public final ConnectionFactory connectionFactory;

    public SqlHelper() {
        connectionFactory = () -> DriverManager.getConnection(Config.get().getDbUrl(),
                Config.get().getDbUser(), Config.get().getDbPassword());
    }

    public <T> T executeQuery(SqlExecutor<T> sqlExecutor, String query) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            return sqlExecutor.getResult(preparedStatement);
        } catch (SQLException e) {
            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);
            if (rootCause instanceof SQLException) {
                if ("23505".equals(((SQLException) rootCause).getSQLState())) {
                    throw new ExistStorageException(e);
                }
            }
            throw new StorageException(e);
        }
    }
}
