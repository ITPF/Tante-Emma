package org.itpf.tanteemma.backend.models;

/**
 * Created by ffigorstoljarow on 17.05.2017.
 */
public class Person {
    private long pn_id;
    private String v_name;
    private String v_vorname;
    private String v_geschlecht;
    private String v_telefon;
    private String v_email;
    private String v_rolle;
    private String v_strasse;
    private String v_ort;
    private String v_plz;

    public Person(long personid, String v_name, String v_vorname, String v_geschlecht, String v_telefon, String email, String v_rolle, String v_strasse, String v_ort, String v_plz) {
        this.pn_id = personid;
        this.v_name = v_name;
        this.v_vorname = v_vorname;
        this.v_geschlecht = v_geschlecht;
        this.v_telefon = v_telefon;
        this.v_email = email;
        this.v_rolle = v_rolle;
        this.v_strasse = v_strasse;
        this.v_ort = v_ort;
        this.v_plz = v_plz;
    }

    public String getV_strasse() {
        return v_strasse;
    }

    public void setV_strasse(String v_strasse) {
        this.v_strasse = v_strasse;
    }

    public String getV_ort() {
        return v_ort;
    }

    public void setV_ort(String v_ort) {
        this.v_ort = v_ort;
    }

    public String getV_plz() {
        return v_plz;
    }

    public void setV_plz(String v_plz) {
        this.v_plz = v_plz;
    }

    public Person() {
    }

    public long getPn_id() {
        return pn_id;
    }

    public void setPn_id(long pn_id) {
        this.pn_id = pn_id;
    }

    public String getV_name() {
        return v_name;
    }

    public void setV_name(String v_name) {
        this.v_name = v_name;
    }

    public String getV_vorname() {
        return v_vorname;
    }

    public void setV_vorname(String v_vorname) {
        this.v_vorname = v_vorname;
    }

    public String getV_geschlecht() {
        return v_geschlecht;
    }

    public void setV_geschlecht(String v_geschlecht) {
        this.v_geschlecht = v_geschlecht;
    }

    public String getV_telefon() {
        return v_telefon;
    }

    public void setV_telefon(String v_telefon) {
        this.v_telefon = v_telefon;
    }

    public String getV_email() {
        return v_email;
    }

    public void setV_email(String v_email) {
        this.v_email = v_email;
    }

    public String getV_rolle() {
        return v_rolle;
    }

    public void setV_rolle(String v_rolle) {
        this.v_rolle = v_rolle;
    }
}
