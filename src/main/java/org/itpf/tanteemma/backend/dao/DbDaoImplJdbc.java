package org.itpf.tanteemma.backend.dao;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by ffigorstoljarow on 24.05.2017.
 */
public class DbDaoImplJdbc implements DbDao {
    private QueryRunner runner;
    private String url;
    private Connection connection;
    private String user, password;

    public DbDaoImplJdbc() {
        this.runner = new QueryRunner();
    }

    public void setConnection(String url, String driver, String user, String password) throws SQLException {
        this.user = user;
        this.password = password;
        DbUtils.loadDriver(driver);
        this.url = url;
    }

    public <T> T runSelectQuery(Class<T> tClass, String query) throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
        ResultSetHandler<T> resultHandler = new BeanHandler<>(tClass);
        T model = runner.query(connection, query, resultHandler);
        DbUtils.close(connection);
        return model;
    }

    public <T> List runSelectQueryMultipleResults(Class<T> tClass, String query) throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
        List list = (List) runner.query(connection, query, new BeanListHandler(tClass));
        DbUtils.close(connection);
        return list;
    }

    public int runUpdateQuery(String query) throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
        int rowsUpdated = runner.update(connection, query);
        DbUtils.close(connection);
        return rowsUpdated;
    }

    public int runMultipleUpdateQueries(List<String> queries) throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
        int rowsUpdated = 0;
        for (String query : queries) {
            rowsUpdated += runner.update(connection, query);
        }
        DbUtils.close(connection);
        return rowsUpdated;
    }
}
