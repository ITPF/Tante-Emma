package org.fia51.tanteemma.configuration;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;

/**
 * Created by ffigorstoljarow on 22.05.2017.
 */
public class PropertiesConfig {
    private Configurations configs;
    private Configuration config;

    public PropertiesConfig() {
        loadProperties("src/main/resources/application.properties");
    }

    private void loadProperties(String propertiesFilePath) {
        configs = new Configurations();
        try {
            config = configs.properties(new File(propertiesFilePath));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    public String get(String config) {
        return this.config.getString(config);
    }

    public String get(String conf1, String conf2) {
        return this.config.getString(conf1, conf2);
    }
}
