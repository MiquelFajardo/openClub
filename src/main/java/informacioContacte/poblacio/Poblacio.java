package main.java.informacioContacte.poblacio;

import java.util.Objects;

/**
 * Dades població
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class Poblacio {
    private long id;
    private String codiPostal;
    private String nom;
    private long idProvincia;

    // Constructor
    public Poblacio() { }

    public Poblacio(long id, String codiPostal, String nom, long idProvincia) {
        this.id = id;
        this.codiPostal = codiPostal;
        this.nom = nom;
        this.idProvincia = idProvincia;
    }

    // Mètodes accessors
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getCodiPostal() { return codiPostal; }
    public void setCodiPostal(String codiPostal) { this.codiPostal = codiPostal; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public long getIdProvincia() { return idProvincia; }
    public void setIdProvincia(long idProvincia) { this.idProvincia = idProvincia; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Poblacio altrePoblacio = (Poblacio) obj;
        return id == altrePoblacio.id
                && Objects.equals(codiPostal, altrePoblacio.codiPostal)
                && Objects.equals(nom, altrePoblacio.nom)
                && Objects.equals(idProvincia, altrePoblacio.idProvincia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codiPostal, nom, idProvincia);
    }
}