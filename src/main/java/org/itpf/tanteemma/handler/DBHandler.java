package org.itpf.tanteemma.handler;

import org.itpf.tanteemma.configuration.PropertiesConfig;
import org.itpf.tanteemma.dao.DbDao;
import org.itpf.tanteemma.dao.DbDaoImplJdbc;
import org.itpf.tanteemma.models.*;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by ffigorstoljarow on 23.05.2017.
 * Handles all the I/O between DAO and this application.
 */
@SuppressWarnings("synthetic-access")
public class DBHandler {
    private DbDao dbDao;

    public DBHandler() {
        dbDao = new DbDaoImplJdbc();
        PropertiesConfig config = new PropertiesConfig();
        try {
            // first connect without database name, to create one if not exists
            dbDao.setConnection(config.get("jdbc.url"), config.get("jdbc.driver"), config.get("jdbc.user"), config.get("jdbc.password"));
            this.createDatabase(config.get("jdbc.database"));
            // now reset connection to use the created databasename
            String url = config.get("jdbc.url");
            if(url.endsWith("/")) {
                url += config.get("jdbc.database");
            } else {
                url += "/" + config.get("jdbc.database");
            }
            dbDao.setConnection(url, config.get("jdbc.driver"), config.get("jdbc.user"), config.get("jdbc.password"));
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

    public int dropAlltables() {
        int updatedRows = 0;
        updatedRows += this.dropTable("bestellzuordnung");
        updatedRows += this.dropTable("artikel");
        updatedRows += this.dropTable("auslieferung");
        updatedRows += this.dropTable("bestellung");
        updatedRows += this.dropTable("person");
        return updatedRows;
    }

    public int createAllTables() {
        int updatedRows = 0;
        updatedRows += this.createPersonTable();
        updatedRows += this.createBestellungTable();
        updatedRows += this.createAuslieferungTable();
        updatedRows += this.createArtikelTable();
        updatedRows += this.createBestellzuordnungTable();
        return updatedRows;
    }

    // --- PERSON

    public int createPersonTable() {
        return tryRunUpdateQuery("create table if not exists person (pn_id mediumint not null auto_increment, v_name varchar(256), v_vorname varchar(256), v_geschlecht varchar(256), v_telefon varchar(256), v_email varchar(256), v_rolle varchar(256), v_strasse varchar(256), v_ort varchar(256), v_plz varchar(256), primary key (pn_id))");

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
        String query = "insert into person (v_name, v_vorname, v_geschlecht, v_telefon, v_email, v_rolle, v_strasse, v_ort, v_plz) values ('"
                + person.getName() + "', '"
                + person.getVorname() + "', '"
                + person.getGeschlecht() + "', '"
                + person.getTelefon() + "', '"
                + person.getEmail() + "', '"
                + person.getRolle() + "', '"
                + person.getStrasse() + "', '"
                + person.getOrt() + "', '"
                + person.getPlz() + "');";
        tryRunUpdateQuery(query);
        return rowsUpdated;
    }

    // --- Artikel

    public int createArtikelTable() {
        return tryRunUpdateQuery("create table if not exists artikel (pn_id mediumint not null auto_increment, v_artikelname varchar(256), v_bezeichnung varchar(256), n_preis double, n_menge integer, primary key (pn_id))");
    }

    public int insertNewArtikel(Artikel artikel) {
        String query = "insert into artikel (v_artikelname, v_bezeichnung, n_preis, n_menge) values ('" +
                artikel.getArtikelname() + "', '" +
                artikel.getBezeichnung() + "', '" +
                artikel.getPreis() + "', '" +
                artikel.getMenge() + "')";
        return this.tryRunUpdateQuery(query);
    }

    public int insertSomeNewArtikels(List<Artikel> artikelList) {
        int updatedRows = 0;
        for (Artikel artikel : artikelList) {
            updatedRows += insertNewArtikel(artikel);
        }
        return updatedRows;
    }

    // --- Bestellung

    public int createBestellungTable() {
        return this.tryRunUpdateQuery("create table if not exists bestellung (pn_id mediumint not null auto_increment, n_personid_fk mediumint, d_datum date, v_status varchar(256), primary key (pn_id), foreign key (n_personid_fk) references person(pn_id))");
    }

    public int insertNewBestellung(Bestellung bestellung) {
        String query = "insert into bestellung (n_personid_fk, d_date, v_status) values ('" +
                bestellung.getPerson().getId() + "', '" +
                bestellung.getDate() + "', '" +
                bestellung.getState() + "')";
        return this.tryRunUpdateQuery(query);
    }

    public int insertSomeBestellungen(List<Bestellung> bestellungen) {
        int updatedRows = 0;
        for (Bestellung bestellung : bestellungen) {
            updatedRows += insertNewBestellung(bestellung);
        }
        return updatedRows;
    }

    // --- Auslieferung

    public int createAuslieferungTable() {
        return this.tryRunUpdateQuery("create table if not exists auslieferung (pn_id mediumint not null auto_increment, n_personid_fk mediumint, n_bestellungid_fk mediumint, primary key (pn_id), foreign key (n_personid_fk) references person(pn_id), foreign key (n_bestellungid_fk) references bestellung(pn_id))");
    }

    public int insertNewAuslieferung(Auslieferung auslieferung) {
        String query = "insert into auslieferung (n_personid_fk, n_bestellungid_fk) values ('" +
                auslieferung.getPerson().getId() + "', '" +
                auslieferung.getBestellung().getId() + "')";
        return this.tryRunUpdateQuery(query);
    }

    public int createSomeNewAuslieferungen(List<Auslieferung> auslieferungen) {
        int updatedRows = 0;
        for (Auslieferung auslieferung : auslieferungen) {
            updatedRows += this.insertNewAuslieferung(auslieferung);
        }
        return updatedRows;
    }

    // --- Bestellzuordnung

    public int createBestellzuordnungTable() {
        return this.tryRunUpdateQuery("create table if not exists bestellzuordnung (pn_id mediumint not null auto_increment, n_bestellungid_fk mediumint, n_artikelid_fk mediumint, n_menge integer, primary key(pn_id), foreign key(n_bestellungid_fk) references bestellung(pn_id), foreign key(n_artikelid_fk) references artikel(pn_id))");
    }

    public int insertNewBestellzuordnung(Bestellzuordnung bestellzuordnung) {
        String query = "insert into bestellzuordnung (n_bestellungid_fk, n_artikelid_fk) values ('" +
                bestellzuordnung.getBestellung().getId() + "', '" +
                bestellzuordnung.getArtikel() + "')";
        return this.tryRunUpdateQuery(query);
    }

    public int insertSomeNewBestellzuordnungen(List<Bestellzuordnung> bestellzuordnungen) {
        int updatedRows = 0;
        for (Bestellzuordnung zuordnung : bestellzuordnungen) {
            updatedRows += this.insertNewBestellzuordnung(zuordnung);
        }
        return updatedRows;
    }

    // --- else

    public void createDatabase(String dbName) {
        this.tryRunUpdateQuery("create database if not exists " + dbName + ";");
    }
}
