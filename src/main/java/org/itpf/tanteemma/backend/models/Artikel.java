package org.itpf.tanteemma.backend.models;

/**
 * Created by ffigorstoljarow on 24.05.2017.
 */
public class Artikel {
    private long pn_id;
    private String v_artikelname;
    private String v_bezeichnung;
    private double n_preis;
    /**
     * TODO: convert int n_menge to double n_menge due to kilogramm n_menge.
     */
    private int n_menge;

    public Artikel() {
    }

    public Artikel(long pn_id, String v_artikelname, String v_bezeichnung, double n_preis, int n_menge) {
        this.pn_id = pn_id;
        this.v_artikelname = v_artikelname;
        this.v_bezeichnung = v_bezeichnung;
        this.n_preis = n_preis;
        this.n_menge = n_menge;
    }

    public long getPn_id() {
        return pn_id;
    }

    public void setPn_id(long pn_id) {
        this.pn_id = pn_id;
    }

    public String getV_artikelname() {
        return v_artikelname;
    }

    public void setV_artikelname(String v_artikelname) {
        this.v_artikelname = v_artikelname;
    }

    public String getV_bezeichnung() {
        return v_bezeichnung;
    }

    public void setV_bezeichnung(String v_bezeichnung) {
        this.v_bezeichnung = v_bezeichnung;
    }

    public double getN_preis() {
        return n_preis;
    }

    public void setN_preis(double n_preis) {
        this.n_preis = n_preis;
    }

    public int getN_menge() {
        return n_menge;
    }

    public void setN_menge(int n_menge) {
        this.n_menge = n_menge;
    }
}
