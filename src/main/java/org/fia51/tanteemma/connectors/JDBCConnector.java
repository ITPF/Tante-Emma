package org.fia51.tanteemma.connectors;


import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.fia51.tanteemma.models.User;
import sun.plugin2.main.server.ResultHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by ffigorstoljarow on 17.05.2017.
 *
 * TODO: Dynamische Queries und Query Templates erstellen
 */
public class JDBCConnector {

    private String url;
    private String driver;
    private String user;
    private String password;
    private QueryRunner runner;
    private Connection connection;

    public JDBCConnector(String url, String driver, String user, String password) {
        this.url = url;
        this.driver = driver;
        this.user = user;
        this.password = password;
        this.runner = new QueryRunner();
    }

    public void connect() throws SQLException {
        DbUtils.loadDriver(driver);
        connection = DriverManager.getConnection(url);
    }

    public User runQuery(String query, String where) throws SQLException {
        ResultSetHandler<User> resultHandler = new BeanHandler<User>(User.class);

        User person = null;
        try {
            person = runner.query(connection, query, resultHandler, where);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(connection);
        }
        return person;
    }

}
