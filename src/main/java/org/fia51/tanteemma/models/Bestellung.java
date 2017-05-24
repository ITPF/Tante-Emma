package org.fia51.tanteemma.models;

import java.sql.Date;

/**
 * Created by ffigorstoljarow on 24.05.2017.
 */
public class Bestellung {
    private long id;
    private Person person;
    private Date datum;
    private String status;

    public Bestellung(long bestellid, Person person, Date datum, String status) {
        this.id = bestellid;
        this.person = person;
        this.datum = datum;
        this.status = status;
    }

    public Bestellung() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}


