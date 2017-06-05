package org.itpf.tanteemma.backend.services;

import org.itpf.tanteemma.backend.handler.DBHandler;
import org.itpf.tanteemma.backend.models.Person;

import java.util.List;

/**
 * Created by User on 6/4/2017.
 */
public class PersonService {
    private DBHandler dbHandler;

    public PersonService() {
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

    public Person findPersonsByName(String vorname, String name) {
        return dbHandler.selectPersonByName(vorname, name);
    }

    public Person findPersonByNameAndAddress(String vorname, String name, String strasse, String ort, String plz) {
        Person person = new Person(0, name, vorname, "", "", "", "", strasse, ort, plz);
        return dbHandler.selectPerson(person);
    }
}
