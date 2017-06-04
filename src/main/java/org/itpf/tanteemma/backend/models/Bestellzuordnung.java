package org.itpf.tanteemma.backend.models;

import org.itpf.tanteemma.backend.models.internal.BestellzuordnungIntern;

/**
 * Created by ffigorstoljarow on 24.05.2017.
 */
public class Bestellzuordnung {
    private long id;
    private Bestellung bestellung;
    private Artikel artikel;
    private int menge;

    public Bestellzuordnung() {
    }

    public Bestellzuordnung(long id, Bestellung bestellung, Artikel artikel, int menge) {
        this.id = id;
        this.bestellung = bestellung;
        this.artikel = artikel;
        this.menge = menge;
    }

    public Bestellung getBestellung() {
        return bestellung;
    }

    public void setBestellung(Bestellung bestellung) {
        this.bestellung = bestellung;
    }

    public Artikel getArtikel() {
        return artikel;
    }

    public void setArtikel(Artikel artikel) {
        this.artikel = artikel;
    }

    public int getMenge() {
        return menge;
    }

    public void setMenge(int menge) {
        this.menge = menge;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
