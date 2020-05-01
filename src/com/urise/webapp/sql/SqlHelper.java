package com.urise.webapp.sql;

import com.urise.webapp.Config;
import com.urise.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    public final ConnectionFactory connectionFactory;

    public SqlHelper() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connectionFactory = () -> DriverManager.getConnection(Config.get().getDbUrl(),
                Config.get().getDbUser(), Config.get().getDbPassword());
    }

    public <T> T executeQuery(SqlExecutor<T> sqlExecutor, String query) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            return sqlExecutor.getResult(preparedStatement);
        } catch (SQLException e) {
            throw ExceptionUtil.convertException(e);
        }
    }

    public <T> T transactionalExecute(SqlTransaction<T> executor) {
        try (Connection conn = connectionFactory.getConnection()) {
            try {
                conn.setAutoCommit(false);
                T res = executor.execute(conn);
                conn.commit();
                return res;
            } catch (SQLException e) {
                conn.rollback();
                throw ExceptionUtil.convertException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
