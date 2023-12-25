package main.java.areaEsportiva.equip;

import java.util.Objects;

/**
 * Classe amb les dades dels equips
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class Equip {
    private long id;
    private long idTemporada;
    private String nom;
    private String categoria;
    private String grup;
    private boolean eliminat;

    // Constructor
    public Equip() { }

    public Equip(long id, long idTemporada, String nom, String categoria, String grup, boolean eliminat) {
        this.id = id;
        this.idTemporada = idTemporada;
        this.nom = nom;
        this.categoria = categoria;
        this.grup = grup;
        this.eliminat = eliminat;
    }

    // Mètodes accessors
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getIdTemporada() { return idTemporada; }
    public void setIdTemporada(long idTemporada) { this.idTemporada = idTemporada; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getGrup() { return grup; }
    public void setGrup(String grup) { this.grup = grup; }

    public boolean esEliminat() { return eliminat; }
    public void setEliminat(boolean eliminat) { this.eliminat = eliminat; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equip equip = (Equip) o;
        return id == equip.id && idTemporada == equip.idTemporada && eliminat == equip.eliminat && Objects.equals(nom, equip.nom) && Objects.equals(categoria, equip.categoria) && Objects.equals(grup, equip.grup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idTemporada, nom, categoria, grup, eliminat);
    }
}
