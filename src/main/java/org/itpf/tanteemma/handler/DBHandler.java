package org.itpf.tanteemma.handler;

import org.itpf.tanteemma.configuration.PropertiesConfig;
import org.itpf.tanteemma.dao.DbDao;
import org.itpf.tanteemma.dao.DbDaoImplJdbc;
import org.itpf.tanteemma.models.Artikel;
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

    private int tryRunUpdateQuery(String query) {
        try {
            return dbDao.runUpdateQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private <T> T tryRunSelectQuery(Class<T> modelclass, String query) {
        try {
            return dbDao.runSelectQuery(modelclass, query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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

    // --- PERSON

    public int createPersonTable() {
        return tryRunUpdateQuery("create table if not exists person (personid mediumint, name varchar(256), vorname varchar(256), geschlecht varchar(256), telefon varchar(256), email varchar(256), rolle varchar(256), primary key (personid))");

    }

    public int inserSomeNewPersons(List<Person> personen) {
        int rowsUpdated = createPersonTable();

        for (Person person : personen) {
            rowsUpdated = insertNewPerson(person);
        }
        return rowsUpdated;
    }

    public int insertNewPerson(Person person) {
        int rowsUpdated = createPersonTable();
        String query = "insert into person (name, vorname, geschlecht, telefon, email, rolle) values ('"
                + person.getName() + "', '"
                + person.getVorname() + "', '"
                + person.getGeschlecht() + "', '"
                + person.getTelefon() + "', '"
                + person.getEmail() + "', '"
                + person.getRolle() + "')";

        tryRunUpdateQuery(query);
        return rowsUpdated;
    }

    // --- Artikel

    public int createArtikelTable() {
        return tryRunUpdateQuery("create table artikel (id mediumint, artikelname varchar(256), bezeichnung varchar(256), preis integer, menge integer, primary key (id))");
    }

    public int insertNewArtikel(Artikel artikel) {

    }
    
}
