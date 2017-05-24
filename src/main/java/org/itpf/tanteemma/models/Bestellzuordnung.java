package org.itpf.tanteemma.models;

/**
 * Created by ffigorstoljarow on 24.05.2017.
 */
public class Bestellzuordnung {
    private Bestellung bestellung;
    private Artikel artikel;
    private int menge;

    public Bestellzuordnung() {
    }

    public Bestellzuordnung(Bestellung bestellung, Artikel artikel, int menge) {
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
}
