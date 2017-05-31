package org.itpf.tanteemma.dao;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.itpf.tanteemma.dao.DbDao;

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

    public DbDaoImplJdbc() {
        this.runner = new QueryRunner();
    }

    public void setConnection(String url, String driver, String user, String password) throws SQLException {
        DbUtils.loadDriver(driver);
        this.url = url;
    }

    public <T> T runSelectQuery(Class<T> tClass, String query) throws SQLException {
        Connection connection = DriverManager.getConnection(url);
        ResultSetHandler<T> resultHandler = new BeanHandler<T>(tClass);
        T model = runner.query(connection, query, resultHandler);
        DbUtils.close(connection);
        return model;
    }

    public <T> List runSelectQueryMultipleResults(Class<T> tClass, String query) throws SQLException {
        // List mapList = (List) query.query(conn, "select * from user", new MapListHandler()
        Connection connection = DriverManager.getConnection(url);
        ArrayListHandler resultHandler = new ArrayListHandler();
        List list = (List) runner.query(connection, query, new BeanListHandler(tClass));
        DbUtils.close(connection);
        return list;
    }

    public int runUpdateQuery(String query) throws SQLException {
        Connection connection = DriverManager.getConnection(url);
        int rowsUpdated = runner.update(connection, query);
        DbUtils.close(connection);
        return rowsUpdated;
    }

    public int runMultipleUpdateQueries(List<String> queries) throws SQLException {
        int rowsUpdated = 0;
        Connection connection = DriverManager.getConnection(url);
        for (String query : queries) {
            rowsUpdated += runner.update(connection, query);
        }
        DbUtils.close(connection);
        return rowsUpdated;
    }
}
