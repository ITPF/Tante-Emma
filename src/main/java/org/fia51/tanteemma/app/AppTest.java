package org.fia51.tanteemma.app;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.fia51.tanteemma.connectors.JDBCConnector;
import org.fia51.tanteemma.models.User;

import java.io.File;
import java.sql.SQLException;

/**
 * Created by ffigorstoljarow on 17.05.2017.
 */
public class AppTest {
    Configurations configs;
    Configuration config;

    public AppTest() {
        configs = new Configurations();
        try {
            config = configs.properties(new File("application.properties"));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        // String url, String driver, String user, String password
        JDBCConnector jdbc = new JDBCConnector(config.getString("jdbc.url"), config.getString("jdbc.driver"), config.getString("jdbc.user"), config.getString("jdbc.password"));
        try {
            jdbc.connect();
            User newUser = jdbc.runQuery("select * from user where name =?", "Igor");
            System.out.println("User: " + newUser.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
