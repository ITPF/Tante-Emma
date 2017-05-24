package org.fia51.tanteemma.models;

/**
 * Created by ffigorstoljarow on 17.05.2017.
 */
public class Person {
    private long personid;
    private String name;
    private String vorname;
    private String geschlecht;
    private String telefon;
    private String email;
    private String rolle;

    public Person(long personid, String name, String vorname, String geschlecht, String telefon, String email, String rolle) {
        this.personid = personid;
        this.name = name;
        this.vorname = vorname;
        this.geschlecht = geschlecht;
        this.telefon = telefon;
        this.email = email;
        this.rolle = rolle;
    }

    public long getPersonid() {
        return personid;
    }

    public void setPersonid(long personid) {
        this.personid = personid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getGeschlecht() {
        return geschlecht;
    }

    public void setGeschlecht(String geschlecht) {
        this.geschlecht = geschlecht;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRolle() {
        return rolle;
    }

    public void setRolle(String rolle) {
        this.rolle = rolle;
    }
}
