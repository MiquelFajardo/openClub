package main.java.informacioContacte.poblacio;

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
}