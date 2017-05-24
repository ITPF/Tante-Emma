package org.itpf.tanteemma.dao;

import java.sql.SQLException;

/**
 * Created by ffigorstoljarow on 18.05.2017.
 */
public interface DbDao {
    void setConnection(String url, String driver, String user, String password) throws SQLException;
    int runUpdateQuery(String query) throws SQLException;
    //int runMultipleUpdateQueries(List<String> queries) throws SQLException;
    <T> T runSelectQuery(Class<T> modelclass, String query) throws SQLException;
}
