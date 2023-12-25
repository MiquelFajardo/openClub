package main.java.informacioContacte.poblacio;

import main.java.club.Club;

import java.util.Objects;

/**
 * Dades dels països
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class Pais {
    private long id;
    private String nom;

    // Constructor
    public Pais() { }

    public Pais(long id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    // Mètodes accessors
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Pais altrePais = (Pais) obj;
        return id == altrePais.id && Objects.equals(nom, altrePais.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom);
    }
}