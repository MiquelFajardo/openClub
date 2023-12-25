package main.java.informacioContacte.correuElectronic;

import java.util.Objects;

/**
 * Dades correu electrònic propietari
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class CorreuElectronic {
    private long id;
    private long idPropietari;
    private String adreca;
    private String tipus;
    private boolean eliminat;

    // Constructor
    public CorreuElectronic() { }

    public CorreuElectronic(long id, long idPropietari, String adreca, String tipus, boolean eliminat) {
        this.id = id;
        this.idPropietari = idPropietari;
        this.adreca = adreca;
        this.tipus = tipus;
        this.eliminat = eliminat;
    }

    // Mètodes accessors
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getIdPropietari() { return idPropietari; }
    public void setIdPropietari(long idPropietari) { this.idPropietari = idPropietari; }

    public String getAdreca() { return adreca; }
    public void setAdreca(String adreca) { this.adreca = adreca; }

    public String getTipus() { return tipus; }
    public void setTipus(String tipus) { this.tipus = tipus; }

    public boolean esEliminat() { return eliminat; }
    public void setEliminat(boolean eliminat) { this.eliminat = eliminat; }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CorreuElectronic other = (CorreuElectronic) o;
        return id == other.id &&
                idPropietari == other.idPropietari &&
                eliminat == other.eliminat &&
                Objects.equals(adreca, other.adreca) &&
                Objects.equals(tipus, other.tipus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idPropietari, adreca, tipus, eliminat);
    }
}