package main.java.informacioContacte.poblacio;

import java.util.Objects;

/**
 * Dades de provincia
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class Provincia {
    private long id;
    private String nom;
    private long idPais;

    // Constructor
    public Provincia() { }

    public Provincia(long id, String nom, long idPais) {
        this.id = id;
        this.nom = nom;
        this.idPais = idPais;
    }

    // Mètodes accessors
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public long getIdPais() { return idPais; }
    public void setIdPais(long idPais) { this.idPais = idPais; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Provincia altreProvincia = (Provincia) obj;
        return id == altreProvincia.id && Objects.equals(nom, altreProvincia.nom) && Objects.equals(idPais, altreProvincia.idPais);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, idPais);
    }
}