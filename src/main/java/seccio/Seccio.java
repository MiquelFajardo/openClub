package main.java.seccio;

import java.util.Objects;

/**
 * Classe per les seccions del club
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class Seccio {
    private long id;
    private long idClub;
    private String nom;
    private boolean eliminat;

    // Constructor
    public Seccio() { }

    public Seccio(long id, long id_club, String nom, boolean eliminat) {
        this.id = id;
        this.idClub = id_club;
        this.nom = nom;
        this.eliminat = eliminat;
    }

    // Mètodes accessors
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getIdClub() { return idClub; }
    public void setIdClub(long idClub) { this.idClub = idClub; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public boolean esEliminat() { return eliminat; }
    public void setEliminat(boolean eliminat) { this.eliminat = eliminat; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seccio seccio = (Seccio) o;
        return id == seccio.id && idClub == seccio.idClub && eliminat == seccio.eliminat && Objects.equals(nom, seccio.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idClub, nom, eliminat);
    }
}
