package com.urise.webapp.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface GetStatement<T> {
    T getResultSet(PreparedStatement preparedStatement) throws SQLException;
}
