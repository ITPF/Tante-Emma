package org.itpf.tanteemma.backend.models;

import java.sql.Date;

/**
 * Created by ffigorstoljarow on 24.05.2017.
 */
public class Auslieferung {
    private long id;
    private Person person;
    private Bestellung bestellung;
    private Date erstellt;

    public Auslieferung(long auslieferungid, Person person, Bestellung bestellung, Date erstellt) {
        this.id = auslieferungid;
        this.person = person;
        this.bestellung = bestellung;
        this.erstellt = erstellt;
    }

    public Auslieferung() {
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

    public Bestellung getBestellung() {
        return bestellung;
    }

    public void setBestellung(Bestellung bestellung) {
        this.bestellung = bestellung;
    }

    public Date getErstellt() {
        return erstellt;
    }

    public void setErstellt(Date erstellt) {
        this.erstellt = erstellt;
    }
}
