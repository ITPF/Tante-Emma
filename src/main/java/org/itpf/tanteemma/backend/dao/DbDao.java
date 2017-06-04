package org.itpf.tanteemma.backend.dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by ffigorstoljarow on 24.05.2017.
 */
public interface DbDao {
    void setConnection(String url, String driver, String user, String password) throws SQLException;
    int runUpdateQuery(String query) throws SQLException;
    int runMultipleUpdateQueries(List<String> queries) throws SQLException;
    <T> T runSelectQuery(Class<T> tClass, String query) throws SQLException;
    <T> List runSelectQueryMultipleResults(Class<T> tclass, String query) throws SQLException;
}
