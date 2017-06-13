package org.itpf.tanteemma.backend.services;

import org.itpf.tanteemma.backend.handler.DBHandler;
import org.itpf.tanteemma.backend.handler.ErrorHandler;
import org.itpf.tanteemma.backend.models.Auslieferung;
import org.itpf.tanteemma.backend.models.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 6/5/2017.
 */
public class DeliveryService {
    private DBHandler dbHandler;

    private static DeliveryService instance;

    public static DeliveryService getInstance(){
        if(instance == null){
            instance = new DeliveryService();
        }
        return instance;
    }

    private DeliveryService() {
        this.dbHandler = new DBHandler();
    }

    public DeliveryService(DBHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    public List<Auslieferung> findAllOpenDeliveries() {
        return dbHandler.selectAllFromAuslieferungByBestellungStatus("open") ;
    }

    public List<Auslieferung> findAllDeliveriesByPerson(Person person) {
        if(person != null && person.getPn_id() != 0) {
            return dbHandler.selectAllfromAusliefrungByPerson(person);
        } else {
            ErrorHandler.showError("Konnte keine Auslieferungen für die Person finden, da diese nicht existiert.");
        }
        return new ArrayList<>();
    }

    public List<Auslieferung> findAllDeliveriesByPerson(String vorname, String name) {
        Person person = dbHandler.selectPersonByName(vorname, name);
        return this.findAllDeliveriesByPerson(person);
    }

    public List<Auslieferung> findAllOpenDeliveriesByPerson(Person person) {
        List<Auslieferung> foundDeliveries = this.findAllDeliveriesByPerson(person);
        List<Auslieferung> onlyOpenOnes = new ArrayList<>();
        for (Auslieferung auslieferung : foundDeliveries) {
            if(auslieferung.getBestellung().getState().equals("open")) {
                onlyOpenOnes.add(auslieferung);
            }
        }
        return onlyOpenOnes;
    }

    public List<Auslieferung> findAllOpenDeliveriesByPerson(String vorname, String name) {
        Person person = dbHandler.selectPersonByName(vorname, name);
        return this.findAllOpenDeliveriesByPerson(person);
    }

    public List<Auslieferung> findAllReadyDeliveries() {
        return dbHandler.selectAllFromAuslieferungByBestellungStatus("ready") ;
    }
}
