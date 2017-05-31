package org.itpf.tanteemma.app;

import java.io.File;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.itpf.tanteemma.filler.DBFiller;

/**
 * Created by ffigorstoljarow on 17.05.2017.
 */
public class AppTest {
    Configurations configs;
    Configuration config;

    public AppTest() {
        configs = new Configurations();
        try {
            config = configs.properties(new File("src/main/resources/application.properties"));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        DBFiller filler = new DBFiller();
        filler.emptyDBAndFillWithJunk();
    }
}
