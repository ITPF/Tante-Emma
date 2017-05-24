package org.itpf.tanteemma.handler;

import org.itpf.tanteemma.configuration.PropertiesConfig;
import org.itpf.tanteemma.dao.DbDao;
import org.itpf.tanteemma.dao.DbDaoImplJdbc;
import org.itpf.tanteemma.models.Person;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by ffigorstoljarow on 23.05.2017.
 */
public class DBHandler {
    private DbDao dbDao;

    public DBHandler() {
        dbDao = new DbDaoImplJdbc();
        PropertiesConfig config = new PropertiesConfig();
        try {
            dbDao.setConnection(config.get("jdbc.url"), config.get("jdbc.driver"), config.get("jdbc.user"), config.get("jdbc.password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int createNewPersonTable() {
        try {
            return dbDao.runUpdateQuery("create table if not exists person (personid mediumint, name varchar(256), vorname varchar(256), geschlecht varchar(256), telefon varchar(256), email varchar(256), rolle varchar(256), primary key (personid))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int dropTable(String tableName) {
        int rowsUpdated = 0;
        try {
            rowsUpdated += dbDao.runUpdateQuery("drop table if exists " + tableName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsUpdated;
    }

    public int inserSomeNewPersons(List<Person> personen) {
        int rowsUpdated = createNewPersonTable();

        for (Person person : personen) {
            rowsUpdated = insertNewPerson(person);
        }
        return rowsUpdated;
    }

    public int insertNewPerson(Person person) {
        int rowsUpdated = createNewPersonTable();
        String query = "insert into person (name, vorname, geschlecht, telefon, email, rolle) values ('"
                + person.getName() + "', '"
                + person.getVorname() + "', '"
                + person.getGeschlecht() + "', '"
                + person.getTelefon() + "', '"
                + person.getEmail() + "', '"
                + person.getRolle() + "')";
        try {
            rowsUpdated = dbDao.runUpdateQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsUpdated;
    }
}
