package org.itpf.tanteemma.backend.models;

import java.sql.Date;

/**
 * Created by ffigorstoljarow on 24.05.2017.
 */
public class Bestellung {
    private long id;
    private Person person;
    private Date date;
    /**
     * TODO: add private Time time or formatting date after datetime
     * Currently users can't order multiple time a day.
     */
    private String state;

    public Bestellung(long id, Person person, Date date, String state) {
        this.id = id;
        this.person = person;
        this.date = date;
        this.state = state;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}


