package main.java.informacioContacte.correuElectronic;

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
}