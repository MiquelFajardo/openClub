package main.java.informacioContacte.telefon;

import java.util.Objects;

/**
 * Telefons usuaris
 *
 * * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class Telefon {
    private long id;
    private long idPropietari;
    private String prefixPais;
    private String numero;
    private String tipus;
    private boolean eliminat;

    // Constructor
    public Telefon() { }

    public Telefon(long id, long idPropietari, String prefixPais, String numero, String tipus, boolean eliminat) {
        this.id = id;
        this.idPropietari = idPropietari;
        this.prefixPais = prefixPais;
        this.numero = numero;
        this.tipus = tipus;
        this.eliminat = eliminat;
    }

    // Mètodes accessors
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getIdPropietari() { return idPropietari; }
    public void setIdPropietari(long idPropietari) { this.idPropietari = idPropietari; }

    public String getPrefixPais() { return prefixPais; }
    public void setPrefixPais(String prefixPais) { this.prefixPais = prefixPais; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getTipus() { return tipus; }
    public void setTipus(String tipus) { this.tipus = tipus; }

    public boolean esEliminat() { return eliminat; }
    public void setEliminat(boolean eliminat) { this.eliminat = eliminat; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Telefon telefon = (Telefon) o;
        return id == telefon.id && idPropietari == telefon.idPropietari;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idPropietari);
    }

}