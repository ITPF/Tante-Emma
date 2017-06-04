package org.itpf.tanteemma.backend.models.internal;

import org.itpf.tanteemma.backend.models.Bestellzuordnung;

/**
 * Created by User on 6/4/2017.
 */
public class BestellzuordnungIntern {
    private long pn_id;
    private long n_bestellungid_fk;
    private long n_artikelid_fk;
    private int n_menge;

    public BestellzuordnungIntern() {
    }

    public BestellzuordnungIntern(Bestellzuordnung bestellzuordnung) {
        this.pn_id = bestellzuordnung.getId();
        this.n_bestellungid_fk = bestellzuordnung.getBestellung().getId();
        this.n_artikelid_fk = bestellzuordnung.getArtikel().getPn_id();
        this.n_menge = bestellzuordnung.getMenge();
    }

    public BestellzuordnungIntern(long pn_id, long n_bestellungid_fk, long n_artikelid_fk, int n_menge) {
        this.pn_id = pn_id;
        this.n_bestellungid_fk = n_bestellungid_fk;
        this.n_artikelid_fk = n_artikelid_fk;
        this.n_menge = n_menge;
    }

    public long getN_bestellungid_fk() {
        return n_bestellungid_fk;
    }

    public void setN_bestellungid_fk(long n_bestellungid_fk) {
        this.n_bestellungid_fk = n_bestellungid_fk;
    }

    public long getN_artikelid_fk() {
        return n_artikelid_fk;
    }

    public void setN_artikelid_fk(long n_artikelid_fk) {
        this.n_artikelid_fk = n_artikelid_fk;
    }

    public int getN_menge() {
        return n_menge;
    }

    public void setN_menge(int n_menge) {
        this.n_menge = n_menge;
    }

    public long getPn_id() {
        return pn_id;
    }

    public void setPn_id(long pn_id) {
        this.pn_id = pn_id;
    }
}
