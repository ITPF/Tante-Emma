package org.itpf.tanteemma.backend.models.internal;

import org.itpf.tanteemma.backend.models.Bestellung;

import java.sql.Date;

/**
 * Created by User on 6/4/2017.
 */
public class BestellungIntern {
    private long pn_id;
    private long n_personid_fk;
    private Date d_datum;
    /**
     * TODO: add private Time time or formatting d_datum after datetime
     * Currently users can't order multiple time a day.
     */
    private String v_status;

    public BestellungIntern(long pn_id, long n_personid_fk, Date d_datum, String v_status) {
        this.pn_id = pn_id;
        this.n_personid_fk = n_personid_fk;
        this.d_datum = d_datum;
        this.v_status = v_status;
    }

    public BestellungIntern() {
    }

    public BestellungIntern(Bestellung bestellung) {
        this.pn_id = bestellung.getId();
        this.n_personid_fk = bestellung.getPerson().getPn_id();
        this.d_datum = bestellung.getDate();
    }

    public long getPn_id() {
        return pn_id;
    }

    public void setPn_id(long pn_id) {
        this.pn_id = pn_id;
    }

    public long getN_personid_fk() {
        return n_personid_fk;
    }

    public void setN_personid_fk(long n_personid_fk) {
        this.n_personid_fk = n_personid_fk;
    }

    public Date getD_datum() {
        return d_datum;
    }

    public void setD_datum(Date d_datum) {
        this.d_datum = d_datum;
    }

    public String getV_status() {
        return v_status;
    }

    public void setV_status(String v_status) {
        this.v_status = v_status;
    }
}
