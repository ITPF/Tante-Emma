package org.fia51.tanteemma.models;

/**
 * Created by ffigorstoljarow on 24.05.2017.
 */
public class Auslieferung {
    private long auslieferungid;
    private Person person;
    private Bestellung bestellung;

    public Auslieferung(long auslieferungid, Person person, Bestellung bestellung) {
        this.auslieferungid = auslieferungid;
        this.person = person;
        this.bestellung = bestellung;
    }

    public Auslieferung() {
    }

    public long getAuslieferungid() {
        return auslieferungid;
    }

    public void setAuslieferungid(long auslieferungid) {
        this.auslieferungid = auslieferungid;
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
}
