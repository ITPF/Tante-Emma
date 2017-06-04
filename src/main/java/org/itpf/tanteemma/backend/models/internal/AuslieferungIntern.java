package org.itpf.tanteemma.backend.models.internal;

import org.itpf.tanteemma.backend.models.Auslieferung;

import java.sql.Date;

/**
 * Created by User on 6/4/2017.
 */
public class AuslieferungIntern {
    private long pn_id;
    private long n_personid_fk;
    private long n_bestellungid_fk;
    private Date d_erstell;

    public AuslieferungIntern(long auslieferungid, long n_personid_fk, long bestellungId, Date d_erstell) {
        this.pn_id = auslieferungid;
        this.n_personid_fk = n_personid_fk;
        this.n_bestellungid_fk = bestellungId;
        this.d_erstell = d_erstell;
    }

    public AuslieferungIntern() {
    }

    public AuslieferungIntern(Auslieferung auslieferung) {
        this.pn_id = auslieferung.getId();
        this.n_personid_fk = auslieferung.getPerson().getPn_id();
        this.n_bestellungid_fk = auslieferung.getBestellung().getId();
        this.d_erstell = auslieferung.getErstellt();
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

    public long getN_bestellungid_fk() {
        return n_bestellungid_fk;
    }

    public void setN_bestellungid_fk(long n_bestellungid_fk) {
        this.n_bestellungid_fk = n_bestellungid_fk;
    }

    public Date getD_erstell() {
        return d_erstell;
    }

    public void setD_erstell(Date d_erstell) {
        this.d_erstell = d_erstell;
    }
}
