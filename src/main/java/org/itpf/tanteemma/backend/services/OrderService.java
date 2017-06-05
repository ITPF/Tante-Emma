package org.itpf.tanteemma.backend.services;

import org.itpf.tanteemma.backend.handler.DBHandler;
import org.itpf.tanteemma.backend.handler.ErrorHandler;
import org.itpf.tanteemma.backend.models.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Used for searching, demanding, managing Artikel.
 */
public class OrderService {
    private DBHandler dbHandler;

    public OrderService() {
        this.dbHandler = new DBHandler();
    }

    public OrderService(DBHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    /**
     * This function links the Artikel to Bestellung and updates the Artikel.menge
     * @param bestellung must exist in db
     * @param artikel must exist in db
     * @param menge can't be greater than artikel.menge
     * @return a list of Artikel relate to bestellung
     */
    public List<Artikel> addArtikelToBestellung(Bestellung bestellung, Artikel artikel, int menge) {
        if(bestellung != null & bestellung.getId() != 0 && artikel != null && artikel.getPn_id() != 0) {
            if(artikel.getN_menge() >= menge) {
                // create Bestellzuordnung
                Bestellzuordnung bestellzuordnung = new Bestellzuordnung(0, bestellung, artikel, menge);
                // Before saving, update amount on Artikel
                artikel.setN_menge(artikel.getN_menge() - menge);
                dbHandler.updateArtikel(artikel);
                // save
                dbHandler.insertNewBestellzuordnung(bestellzuordnung);

            } else {
                ErrorHandler.showError("Artikel konnte der Bestellugn nicht hinzugefügt werden. Es sind nur noch " + artikel.getN_menge() + " von " + artikel.getV_artikelname() + " übrig.");
            }
        } else {
            ErrorHandler.showError("Artikel konnte der Bestellung nicht hinzugefügt werden. Bestellung oder Artikel nicht vorhanden.");
        }
        return dbHandler.selectAllArtikelFrombestellung(bestellung);
    }

    /**
     * This function searches for Artikel by given name and links it with Bestellung, then updates Artikel.menge
     * @param bestellung must exist in db
     * @param artikelName used to find Artikel in DB
     * @param menge can't be greater than artikel.menge
     * @return a list of Artikel relate to bestellung
     */
    public List<Artikel> addArtikelToBestellung(Bestellung bestellung, String artikelName, int menge) {
        Artikel artikel = dbHandler.selectArtikelByName(artikelName);
        if(artikel != null && artikel.getPn_id() != 0) {
            return this.addArtikelToBestellung(bestellung, artikel, menge);
        } else {
            ErrorHandler.showError("Artikel konnte der Bestellung nicht hinzugefügt werden. Esgibt kein Artikel mit dem Namen \"" + artikelName + "\".");
        }
        return new ArrayList<>();
    }

    /**
     * Creates a new empty Bestellung and links it with person
     * @param person
     * @return new Bestellung
     */
    public Bestellung createNewBestelung(Person person) {
        if(person != null && person.getPn_id() != 0) {
            java.util.Date utilDate = new java.util.Date();
            Bestellung bestellung = new Bestellung(0, person, new Date(utilDate.getTime()), "open");
            dbHandler.insertNewBestellung(bestellung);
            // database will generate while inserting, so here we return the found Bestellung - idiotprooving
            return dbHandler.selectBestellung(bestellung);
        } else {
            ErrorHandler.showError("Bestellung kann nicht erstellt werden, da die Person nicht existiert.");
        }
        return new Bestellung();
    }

    /**
     * Deletes all Bestellzuordnungen related to Bestellung. Then deletes Bestellung itself.
     * Afterwards the Artikel.menge is reset to it's previous quantity.
     * @param bestellung is an existent Bestellung from database with valid id.
     */
    public void cancelBestellung(Bestellung bestellung) {
        if(bestellung != null & bestellung.getId() != 0) {
            // Before deleting actual Bestellung, we have to ajust the amount of Artikel, then delete Bestellzuordnungen
            List<Bestellzuordnung> bestellzuordnungList = dbHandler.selectAllBestellzurodnungenByBestellung(bestellung); // why not ArtikelList? Bestellzuordnungen contains menge.
            for (Bestellzuordnung bestellzuordnung : bestellzuordnungList) {
                // update each artikel for it's quantity, due to cancellation
                Artikel artikel = bestellzuordnung.getArtikel();
                artikel.setN_menge(artikel.getN_menge() + bestellzuordnung.getMenge());
                dbHandler.updateArtikel(artikel);
                // now we can safe delete the bestellzuordnung
                dbHandler.deleteBestellZuordnung(bestellzuordnung);
            }
            dbHandler.deleteBestellung(bestellung);
        } else {
            ErrorHandler.showError("Kann die Bestellung nicht löschen, da diese nicht existiert!");
        }
    }

    /**
     * Finishes up a Bestellung / order and creates an Auslieferung / delivery.
     * Aterwards it assign some random worker to the delivery.
     * @param bestellung
     * @return new delivery / Auslieferung
     */
    public Auslieferung orderBestellung(Bestellung bestellung) {
        if(bestellung != null && bestellung.getId() != 0) {
            bestellung.setState("ready");
            dbHandler.updateBestellung(bestellung);
            java.util.Date utilDate = new java.util.Date();
            // pick some random worker to care for this order.
            List<Person> workers = dbHandler.selectAllPersonsByRolle("Mitarbeiter");
            Person worker = workers.get(this.generateRandomNumber(0, workers.size()-1));

            Auslieferung auslieferung = new Auslieferung(0, worker, bestellung, new Date(utilDate.getTime()));
            dbHandler.insertNewAuslieferung(auslieferung);
            return dbHandler.selectAuslieferung(auslieferung);
        } else {
            ErrorHandler.showError("Konnte die Bestellung nicht abschließen, da diese nicht existiert.");
        }
        return new Auslieferung();
    }

    private int generateRandomNumber(int min, int max) {
        if(max == -1)
            return 0;
        Random random = new Random();

        long range = (long) max - (long) min + 1;
        long fraction = (long)(range * random.nextDouble());
        return (int)(fraction + min);
    }

}
