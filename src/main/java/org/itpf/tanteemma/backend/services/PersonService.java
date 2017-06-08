package org.itpf.tanteemma.backend.services;

import org.itpf.tanteemma.backend.handler.DBHandler;
import org.itpf.tanteemma.backend.handler.ErrorHandler;
import org.itpf.tanteemma.backend.models.Person;

import java.util.List;

/**
 * Used to handle Person data.
 */
public class PersonService {

    public static PersonService instance;

    public static PersonService getInstance(){
        if(instance == null){
            instance = new PersonService();
        }
        return instance;
    }

    private DBHandler dbHandler;

    private PersonService() {
        this.dbHandler = new DBHandler();
    }

    public PersonService(DBHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    public Person createNewPerson(String name, String vorname, String geschlecht, String telefon, String email, String rolle, String strasse, String ort, String plz) {
        Person person = new Person(0, name, vorname, geschlecht, telefon, email, rolle, strasse, ort, plz);
        dbHandler.insertNewPerson(person);
        return dbHandler.selectPerson(person);
    }

    public Person createNewPerson(Person person) {
        if(person != null && person.getPn_id() != 0) {
            dbHandler.insertNewPerson(person);
            return dbHandler.selectPerson(person);
        } else {
            ErrorHandler.showError("Konnte Person nicht erstellen, da diese nicht existiert!");
        }
        return person;
    }

    public Person updatePerson(Person person) {
        if(person != null && person.getPn_id() != 0) {
            dbHandler.updatePerson(person);
        } else {
            ErrorHandler.showError("Konnte Person nicht aktualisieren, da dieser nicht existiert!");
        }
        return person;
    }

    /**
     * Searches for a person by given name. Attention, there might be some persons with the same name.
     * @param vorname
     * @param name
     * @return found Person
     */
    public Person findPersonByName(String vorname, String name) {
        return dbHandler.selectPersonByName(vorname, name);
    }

    public Person findPersonByNameAndAddress(String vorname, String name, String strasse, String ort, String plz) {
        Person person = new Person(0, name, vorname, "", "", "", "", strasse, ort, plz);
        return dbHandler.selectPerson(person);
    }

    public List<Person> getAllPersons() {
        return dbHandler.selectAllFromPerson();
    }
}
