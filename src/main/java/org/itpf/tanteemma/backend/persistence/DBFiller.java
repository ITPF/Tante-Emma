package org.itpf.tanteemma.backend.persistence;

import org.itpf.tanteemma.backend.configuration.PropertiesConfig;
import org.itpf.tanteemma.backend.handler.DBHandler;
import org.itpf.tanteemma.backend.models.Artikel;
import org.itpf.tanteemma.backend.models.Person;

/**
 * Created by ffigorstoljarow on 24.05.2017.
 * Fills the database with some entities. USed for the first initialization.
 */
public class DBFiller {
    private DBHandler dbHandler;
    private PropertiesConfig conf;

    public DBFiller() {
        dbHandler = new DBHandler();
        conf = new PropertiesConfig();
    }

    public DBFiller(DBHandler dbHandler) {
        this.dbHandler = dbHandler;
        conf = new PropertiesConfig();
    }

    public void emptyDBAndFillWithJunk() {
        dbHandler.dropAlltables();
        dbHandler.createAllTables();

        dbHandler.insertNewPerson(new Person(0, "Neumann", "Filiz", "weiblich", "02133977866", "filiz.neumann@gmail.com", "Mitarbeiter", "Bluemental 1", "Neuss", "41545"));
        dbHandler.insertNewPerson(new Person(0, "Kraemer", "Tatjana", "weiblich", "02133977654", "tatjana.kraemer@gmail.com", "Mitarbeiter", "Ebertplatz 7", "Köln", "33442"));
        dbHandler.insertNewPerson(new Person(0, "Lindner", "Patrick", "maennlich", "02133977344", "patrick.lindner@gmail.com", "Mitarbeiter", "Po der Welt 1", "Ewigen", "666"));
        dbHandler.insertNewPerson(new Person(0, "Lindmann", "Patrick", "maennlich", "02133977666", "lin@web.de", "Kunde", "Weilerstr. 1", "Dormagen", "41540"));
        dbHandler.insertNewPerson(new Person(0, "Stoljarow", "Igor", "unbekannt", "02144567567", "igor@web.de", "Kunde", "Teuschsweg 666", "Dormagen", "51539"));

        dbHandler.insertNewArtikel(new Artikel(0, "Klopapier", "Packung mit 20 Klopapiereinheiten", 3.95, 100));
        dbHandler.insertNewArtikel(new Artikel(0, "Bananen", "Eine Packung Bananen", 7.99, 50));
        dbHandler.insertNewArtikel(new Artikel(0, "Aepfel", "Ein Kilo Apfel", 2.99, 30));
        dbHandler.insertNewArtikel(new Artikel(0, "Gurken", "Ein Kilo Gurken", 2.45, 40));
        dbHandler.insertNewArtikel(new Artikel(0, "Airways Kaugummi", "Eine Packung Kaugummi", 2.45, 100));
    }
}
