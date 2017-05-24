package org.fia51.tanteemma.handler;

import org.fia51.tanteemma.configuration.PropertiesConfig;
import org.fia51.tanteemma.dao.DbDaoImplJdbc;
import org.fia51.tanteemma.models.Person;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by ffigorstoljarow on 23.05.2017.
 */
public class DBPersonHandler implements DBHandler<Person>{
    private DbDaoImplJdbc<Person> personDao;
    private PropertiesConfig config;

    public DBPersonHandler() {
        config = new PropertiesConfig();
        this.personDao = new DbDaoImplJdbc<Person>(Person.class);
        try {
            personDao.setConnection(config.get("jdbc.url"), config.get("jdbc.driver"), config.get("jdbc.user"), config.get("jdbc.password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int createNewTable() {
        try {
            return personDao.runUpdateQuery("create table if not exists person (personid mediumint, name varchar(256), vorname varchar(256), geschlecht varchar(256), telefon varchar(256), email varchar(256), rolle varchar(256), primary key (personid))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int dropTable() {
        int rowsUpdated = 0;
        try {
            rowsUpdated += personDao.runUpdateQuery("drop table if exists " + "person");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsUpdated;
    }

    public int insertNewEntities(List<Person> personen) {
        int rowsUpdated = createNewTable();

        for (Person person : personen) {
            rowsUpdated = insertNewEntity(person);
        }
        return rowsUpdated;
    }

    public int insertNewEntity(Person person) {
        int rowsUpdated = createNewTable();
        String query = "insert into person (name, vorname, geschlecht, telefon, email, rolle) values ('"
                + person.getName() + "', '"
                + person.getVorname() + "', '"
                + person.getGeschlecht() + "', '"
                + person.getTelefon() + "', '"
                + person.getEmail() + "', '"
                + person.getRolle() + "')";
        try {
            rowsUpdated = personDao.runUpdateQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsUpdated;
    }
}
