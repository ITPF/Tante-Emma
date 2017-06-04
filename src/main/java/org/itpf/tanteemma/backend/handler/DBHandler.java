package org.itpf.tanteemma.backend.handler;

import org.itpf.tanteemma.backend.configuration.PropertiesConfig;
import org.itpf.tanteemma.backend.dao.DbDao;
import org.itpf.tanteemma.backend.dao.DbDaoImplJdbc;
import org.itpf.tanteemma.backend.models.*;
import org.itpf.tanteemma.backend.models.internal.AuslieferungIntern;
import org.itpf.tanteemma.backend.models.internal.BestellungIntern;
import org.itpf.tanteemma.backend.models.internal.BestellzuordnungIntern;

import java.sql.SQLException;
import java.util.*;

/**
 * Handles all the I/O between DAO and this application.
 * Attention! References are being ignored while deleting things. Use wisely.
 * DBHandler is using internal Models and maps them manually to more comfortable ones.
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

    private <T> List tryRunMultipleSelectQueries(Class<T> modelclass, String query) {
        try {
            return dbDao.runSelectQueryMultipleResults(modelclass, query);
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

    public int insertSomeNewPersons(List<Person> personen) {
        int rowsUpdated = createPersonTable();

        for (Person person : personen) {
            rowsUpdated = insertNewPerson(person);
        }
        return rowsUpdated;
    }

    public int insertNewPerson(Person person) {
        int rowsUpdated = createPersonTable();
        String query = "insert into person (v_name, v_vorname, v_geschlecht, v_telefon, v_email, v_rolle, v_strasse, v_ort, v_plz) values ('"
                + person.getV_name() + "', '"
                + person.getV_vorname() + "', '"
                + person.getV_geschlecht() + "', '"
                + person.getV_telefon() + "', '"
                + person.getV_email() + "', '"
                + person.getV_rolle() + "', '"
                + person.getV_strasse() + "', '"
                + person.getV_ort() + "', '"
                + person.getV_plz() + "');";
        tryRunUpdateQuery(query);
        return rowsUpdated;
    }

    private Person selectPersonById(long personId) {
        return this.tryRunSelectQuery(Person.class, "select * from Person where pn_id = '2';");
    }

    /**
     * Searches a person for some defined attributes. This is a dynamic method. It searches only for provided attributes.
     * @param person, used attributes are all which are provided. (not null & not an empty string)
     * @return found Person from db.
     */
    public Person selectPerson(Person person) {
        Map<String, String> searchAttributes = new HashMap<String, String>();
        // inserting some attributes
        if(person.getV_name() != null && !person.getV_name().isEmpty()) {
            searchAttributes.put("v_name", person.getV_name());
        }
        if(person.getV_vorname() != null && !person.getV_vorname().isEmpty()) {
            searchAttributes.put("v_vorname", person.getV_vorname());
        }
        if(person.getV_geschlecht() != null && !person.getV_geschlecht().isEmpty()) {
            searchAttributes.put("v_geschlecht", person.getV_geschlecht());
        }
        if(person.getV_telefon() != null & !person.getV_telefon().isEmpty()) {
            searchAttributes.put("v_telefon", person.getV_telefon());
        }
        if(person.getV_email() != null & !person.getV_email().isEmpty()) {
            searchAttributes.put("v_email", person.getV_email());
        }
        if(person.getV_rolle() != null & !person.getV_rolle().isEmpty()) {
            searchAttributes.put("v_rolle", person.getV_rolle());
        }
        if(person.getV_strasse() != null && !person.getV_strasse().isEmpty()) {
            searchAttributes.put("v_strasse", person.getV_strasse());
        }
        if(person.getV_ort() != null && !person.getV_ort().isEmpty()) {
            searchAttributes.put("v_ort", person.getV_ort());
        }
        if(person.getV_plz() != null && !person.getV_plz().isEmpty()) {
            searchAttributes.put("v_plz", person.getV_plz());
        }

        String query = "select * from person where ";

        // Move map to a set to iterate through keys and values.
        Set keys = searchAttributes.entrySet();

        for (Object key : keys) {
            Map.Entry entry = (Map.Entry) key;
            String attribute = (String) entry.getKey();
            String value = (String) entry.getValue();

            query += attribute + " = " + " and ";
        }
        query = query.substring(0, query.lastIndexOf(" and ")) + ";";

        return this.tryRunSelectQuery(Person.class, query);
    }

    public Person selectPersonByName(String vorname, String name) {
        Person person = this.tryRunSelectQuery(Person.class, "select * from person where v_vorname like '" +
                vorname + "' and v_name like '" + name + "';");
        return person;
    }

    public List<Person> selectAllFromPerson() {
        return this.tryRunMultipleSelectQueries(Person.class, "select * from person;");
    }

    public List<Person> selectAllPersonsByRolle(String rolle) {
        return this.tryRunMultipleSelectQueries(Person.class, "select * from person where v_rolle = '" + rolle + "';");
    }

    // --- Artikel

    public int createArtikelTable() {
        return tryRunUpdateQuery("create table if not exists artikel (pn_id mediumint not null auto_increment, v_artikelname varchar(256), v_bezeichnung varchar(256), n_preis double, n_menge integer, primary key (pn_id))");
    }

    public int insertNewArtikel(Artikel artikel) {
        String query = "insert into artikel (v_artikelname, v_bezeichnung, n_preis, n_menge) values ('" +
                artikel.getV_artikelname() + "', '" +
                artikel.getV_bezeichnung() + "', '" +
                artikel.getN_preis() + "', '" +
                artikel.getN_menge() + "')";
        return this.tryRunUpdateQuery(query);
    }

    public int insertSomeNewArtikels(List<Artikel> artikelList) {
        int updatedRows = 0;
        for (Artikel artikel : artikelList) {
            updatedRows += insertNewArtikel(artikel);
        }
        return updatedRows;
    }


    public List selectAllArtikelFrombestellung(Bestellung bestellung) {
        List<Bestellzuordnung> bestellzuordnungList = this.selectAllBestellzurodnungenByBestellung(bestellung);
        List<Artikel> artikelList = new ArrayList<>();
        for (Bestellzuordnung bestellzuordnung : bestellzuordnungList) {
            artikelList.add(bestellzuordnung.getArtikel());
        }
        return artikelList;
    }

    public int updateArtikel(Artikel artikel) {
        return this.tryRunUpdateQuery("update artikel set v_artikelname = '" +
                artikel.getV_artikelname() + "', v_bezeichnung = '" +
                artikel.getV_bezeichnung() + "', n_preis = '" +
                artikel.getN_preis() + "', n_menge = '" +
                artikel.getN_menge() + "' where pn_id = '" +
                artikel.getPn_id() + "';");
    }

    private Artikel selectArtikelById(long artikelId) {
        return this.tryRunSelectQuery(Artikel.class, "select * from artikel where pn_id = '" + artikelId + "';");
    }

    public Artikel selectArtikelByName(String artikelName) {
        return this.tryRunSelectQuery(Artikel.class, "select * from artikel where v_artikelname = '" + artikelName + "';");
    }

    public List<Artikel> selectAllFromArtikel() {
        return this.tryRunMultipleSelectQueries(Artikel.class, "select * from artikel;");
    }

    // --- Bestellung

    public int createBestellungTable() {
        return this.tryRunUpdateQuery("create table if not exists bestellung (pn_id mediumint not null auto_increment, n_personid_fk mediumint, d_datum date, v_status varchar(256), primary key (pn_id), foreign key (n_personid_fk) references person(pn_id))");
    }

    public int insertNewBestellung(Bestellung bestellung) {
        String query = "insert into bestellung (n_personid_fk, d_datum, v_status) values ('" +
                bestellung.getPerson().getPn_id() + "', '" +
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

    public int deleteBestellung(Bestellung bestellung) {
        return this.tryRunUpdateQuery("delete from bestellung where pn_id = " + bestellung.getId() + ";");
    }

    public Bestellung selectBestellung(Bestellung bestellung) {
        BestellungIntern dbBestellung = this.tryRunSelectQuery(BestellungIntern.class, "select * from bestellung where n_personid_fk = '" +
                bestellung.getPerson().getPn_id() + "' and d_datum = '" +
                bestellung.getDate() + "' and v_status like '" +
                bestellung.getState() + "';");
        if(dbBestellung != null) {
            bestellung.setId(dbBestellung.getPn_id());
        }
        return bestellung;
    }

    private Bestellung selectBestellungById(long bestellungId) {
        BestellungIntern bestellungIntern = this.tryRunSelectQuery(BestellungIntern.class, "select * from bestellung where pn_id = '" + bestellungId + "';");
        Person person = this.selectPersonById(bestellungIntern.getN_personid_fk());
        return new Bestellung(bestellungIntern.getPn_id(), person, bestellungIntern.getD_datum(), bestellungIntern.getV_status());
    }

    public int updateBestellung(Bestellung bestellung) {
        return tryRunUpdateQuery("update bestellung set n_personid_fk = " +
                bestellung.getId() + ", d_datum = '" +
                bestellung.getDate() + "', v_status = '" +
                bestellung.getState() + "' where pn_id = '" +
                bestellung.getId() + "';");
    }

    // --- Auslieferung

    public int createAuslieferungTable() {
        return this.tryRunUpdateQuery("create table if not exists auslieferung (pn_id mediumint not null auto_increment, n_personid_fk mediumint, n_bestellungid_fk mediumint, d_erstellt date, primary key (pn_id), foreign key (n_personid_fk) references person(pn_id), foreign key (n_bestellungid_fk) references bestellung(pn_id))");
    }

    public int insertNewAuslieferung(Auslieferung auslieferung) {
        String query = "insert into auslieferung (n_personid_fk, n_bestellungid_fk, d_erstellt) values ('";
        query += auslieferung.getPerson().getPn_id() + "', '" +
                auslieferung.getBestellung().getId() + "', '" +
                auslieferung.getErstellt() + "')";;

        return this.tryRunUpdateQuery(query);
    }

    public Auslieferung selectAuslieferung(Auslieferung auslieferung) {
        AuslieferungIntern auslieferungIntern = tryRunSelectQuery(AuslieferungIntern.class, "select * from auslieferung where n_personid_fk = '" +
                auslieferung.getPerson().getPn_id() + "' and n_bestellungid_fk = '" +
                auslieferung.getBestellung().getId() + "' and d_erstellt = '" +
                auslieferung.getErstellt() + "';");
        Person person = null;
        if(auslieferung.getPerson() != null) {
            person = this.selectPersonById(auslieferung.getPerson().getPn_id());
        }
        Bestellung bestellung = this.selectBestellungById(auslieferung.getBestellung().getId());

        return new Auslieferung(auslieferungIntern.getPn_id(), person, bestellung, auslieferungIntern.getD_erstell());
    }

    // --- Bestellzuordnung

    public int createBestellzuordnungTable() {
        return this.tryRunUpdateQuery("create table if not exists bestellzuordnung (pn_id mediumint not null auto_increment, n_bestellungid_fk mediumint, n_artikelid_fk mediumint, n_menge integer, primary key(pn_id), foreign key(n_bestellungid_fk) references bestellung(pn_id), foreign key(n_artikelid_fk) references artikel(pn_id))");
    }

    public int insertNewBestellzuordnung(Bestellzuordnung bestellzuordnung) {
        String query = "insert into bestellzuordnung (n_bestellungid_fk, n_artikelid_fk, n_menge) values ('" +
                bestellzuordnung.getBestellung().getId() + "', '" +
                bestellzuordnung.getArtikel().getPn_id() + "', '" +
                bestellzuordnung.getMenge() + "');";
        return this.tryRunUpdateQuery(query);
    }

    public int deleteBestellZuordnung(Bestellzuordnung bestellzuordnung) {
        return this.tryRunUpdateQuery("delete from bestellzuordnung where pn_id = " + bestellzuordnung.getId() + ";");
    }

    public int insertSomeNewBestellzuordnungen(List<Bestellzuordnung> bestellzuordnungen) {
        int updatedRows = 0;
        for (Bestellzuordnung zuordnung : bestellzuordnungen) {
            updatedRows += this.insertNewBestellzuordnung(zuordnung);
        }
        return updatedRows;
    }

    public List selectAllBestellzurodnungenByBestellung(Bestellung bestellung) {
        List bestellzuordnungInternList = tryRunMultipleSelectQueries(BestellzuordnungIntern.class, "select * from bestellzuordnung where n_bestellungid_fk = '" +
                bestellung.getId() + "';");
        List<Bestellzuordnung> bestellzuordnungList = new ArrayList<>();
        for (BestellzuordnungIntern bestell : (List<BestellzuordnungIntern>)bestellzuordnungInternList) {
            Bestellung newBestellung = this.selectBestellungById(bestell.getN_bestellungid_fk());
            Artikel newArtikel = this.selectArtikelById(bestell.getN_artikelid_fk());
            bestellzuordnungList.add(new Bestellzuordnung(bestell.getPn_id(), newBestellung, newArtikel, bestell.getN_menge()));
        }
        return bestellzuordnungList;
    }



    // --- else

    public void createDatabase(String dbName) {
        this.tryRunUpdateQuery("create database if not exists " + dbName + ";");
    }


}
