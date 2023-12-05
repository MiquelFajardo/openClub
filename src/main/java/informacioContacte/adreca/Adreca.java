package main.java.informacioContacte.adreca;

/**
 * Dades adreça usuari
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class Adreca {
    private long id;
    private long idPropietari;
    private String tipus;
    private String nomCarrer;
    private String numero;
    private String pis;
    private long idPoblacio;
    private boolean eliminat;

    // Constructor
    public Adreca() { }

    public Adreca(long id, long idPropietari, String tipus, String nomCarrer, String numero, String pis, long idPoblacio, boolean eliminat) {
        this.id = id;
        this.idPropietari = idPropietari;
        this.tipus = tipus;
        this.nomCarrer = nomCarrer;
        this.numero = numero;
        this.pis = pis;
        this.idPoblacio = idPoblacio;
        this.eliminat = eliminat;
    }

    // Mètodes accessors
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getIdPropietari() { return idPropietari; }
    public void setIdPropietari(long idPropietari) { this.idPropietari = idPropietari; }

    public String getTipus() { return tipus; }
    public void setTipus(String tipus) { this.tipus = tipus; }

    public String getNomCarrer() { return nomCarrer; }
    public void setNomCarrer(String nomCarrer) { this.nomCarrer = nomCarrer; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getPis() { return pis; }
    public void setPis(String pis) { this.pis = pis; }

    public long getIdPoblacio() { return idPoblacio; }
    public void setIdPoblacio(long idPoblacio) { this.idPoblacio = idPoblacio; }

    public boolean esEliminat() { return eliminat; }
    public void setEliminat(boolean eliminat) { this.eliminat = eliminat; }
}