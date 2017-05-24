package org.fia51.tanteemma.models;

/**
 * Created by ffigorstoljarow on 24.05.2017.
 */
public class Artikel {
    private long id;
    private String artikelname;
    private String bezeichnung;
    private int preis;
    private int menge;

    public Artikel() {
    }

    public Artikel(long id, String artikelname, String bezeichnung, int preis, int menge) {
        this.id = id;
        this.artikelname = artikelname;
        this.bezeichnung = bezeichnung;
        this.preis = preis;
        this.menge = menge;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getArtikelname() {
        return artikelname;
    }

    public void setArtikelname(String artikelname) {
        this.artikelname = artikelname;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public int getPreis() {
        return preis;
    }

    public void setPreis(int preis) {
        this.preis = preis;
    }

    public int getMenge() {
        return menge;
    }

    public void setMenge(int menge) {
        this.menge = menge;
    }
}
