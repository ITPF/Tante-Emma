package org.itpf.tanteemma.handler;

import org.itpf.tanteemma.configuration.PropertiesConfig;
import org.itpf.tanteemma.dao.DbDao;
import org.itpf.tanteemma.dao.DbDaoImplJdbc;
import org.itpf.tanteemma.models.*;

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
        return tryRunUpdateQuery("create table if not exists person (id mediumint not null auto_increment(), name varchar(256), vorname varchar(256), geschlecht varchar(256), telefon varchar(256), email varchar(256), rolle varchar(256), primary key (id))");

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
        return tryRunUpdateQuery("create table if not exists artikel (id mediumint not null auto_increment(), artikelname varchar(256), bezeichnung varchar(256), preis integer, menge integer, primary key (id))");
    }

    public int insertNewArtikel(Artikel artikel) {
        String query = "insert into artikel (artikelname, bezeichnung, preis, meinge) values ('" +
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
        return this.tryRunUpdateQuery("create table if not exists bestellung (id mediumint not null auto_increment(), person_id mediumint, datum date, status varchar(256), primary key (id), foreign key (person_id) references person(id))");
    }

    public int insertNewBestellung(Bestellung bestellung) {
        String query = "insert into bestellung (person_id, date, status) values ('" +
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
        return this.tryRunUpdateQuery("create table if not exists auslieferung (id mediumint not null auto_increment(), person_id mediumint, bestellung_id mediumint, primary key (id), foreign key (person_id) references person(id), foreign key (bestellung_id) references bestellung(id))");
    }

    public int insertNewAuslieferung(Auslieferung auslieferung) {
        String query = "insert into auslieferung (person_id, bestellung_id) values ('" +
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
        return this.tryRunUpdateQuery("create table if not exists bestellzuordnung (pn_id mediumint not null auto_increment, n_bestellungid_fk mediumint, n_artikelid_fk mediumint, n_menge integer, primary key(pn_id), foreign key(n_bestellungid_fk) references bestellung(id), foreign key(n_artikel_fk) references artikel(id))");
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

}
