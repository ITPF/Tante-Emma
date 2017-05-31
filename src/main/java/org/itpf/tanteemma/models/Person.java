package org.itpf.tanteemma.models;

/**
 * Created by ffigorstoljarow on 17.05.2017.
 */
public class Person {
    private long id;
    private String name;
    private String vorname;
    private String geschlecht;
    private String telefon;
    private String email;
    private String rolle;
    private String strasse;
    private String ort;
    private String plz;

    public Person(long personid, String name, String vorname, String geschlecht, String telefon, String email, String rolle, String strasse, String ort, String plz) {
        this.id = personid;
        this.name = name;
        this.vorname = vorname;
        this.geschlecht = geschlecht;
        this.telefon = telefon;
        this.email = email;
        this.rolle = rolle;
        this.strasse = strasse;
        this.ort = ort;
        this.plz = plz;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public Person() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
