package com.urise.webapp.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlExecutor<T> {
    T getResult(PreparedStatement preparedStatement) throws SQLException;
}
