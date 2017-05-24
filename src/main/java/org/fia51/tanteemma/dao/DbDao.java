package org.fia51.tanteemma.dao;

import java.sql.SQLException;

/**
 * Created by ffigorstoljarow on 18.05.2017.
 */
public interface DbDao<T> {
    void setConnection(String url, String driver, String user, String password) throws SQLException;
    int runUpdateQuery(String query) throws SQLException;
    //int runMultipleUpdateQueries(List<String> queries) throws SQLException;
    T runSelectQuery(String query) throws SQLException;
}
