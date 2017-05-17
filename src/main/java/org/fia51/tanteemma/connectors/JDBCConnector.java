package org.fia51.tanteemma.connectors;


import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import sun.plugin2.main.server.ResultHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by ffigorstoljarow on 17.05.2017.
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

    public void connect() {
        try {
            DbUtils.loadDriver(driver);
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void query() {
    	
    }


}
