package org.fia51.tanteemma.dao;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbDaoImplJdbc implements DbDao {
    private QueryRunner runner;
    private String url;

    public DbDaoImplJdbc() {
        this.runner = new QueryRunner();
    }

    public void setConnection(String url, String driver, String user, String password) throws SQLException {
        DbUtils.loadDriver(driver);
        this.url = url;
    }

    public <T> T runSelectQuery(Class<T> modelClass, String query) throws SQLException {
        Connection connection = DriverManager.getConnection(url);
        ResultSetHandler<T> resultHandler = new BeanHandler<T>(modelClass);
        T model = runner.query(connection, query, resultHandler);
        DbUtils.close(connection);
        return model;
    }

    public int runUpdateQuery(String query) throws SQLException {
        Connection connection = DriverManager.getConnection(url);
        int rowsUpdated = runner.update(connection, query);
        DbUtils.close(connection);
        return rowsUpdated;
    }

    /*public int runMultipleUpdateQueries(List<String> queries) throws SQLException {
        int rowsUpdated = 0;
        Connection connection = DriverManager.getConnection(url);
        for (String query : queries) {
            rowsUpdated += runner.update(connection, query);
        }
        DbUtils.close(connection);
        return rowsUpdated;
    }*/
}
