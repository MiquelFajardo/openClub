package main.java.informacioContacte.dadesPersonals;

import java.time.LocalDate;

/**
 * Classe abstracta amb les dades personals que utilitzarem pels jugadors, entrenadors...
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public abstract class DadesPersonals {
    String nom;
    String cognom1;
    String cognom2;
    String nif;
    LocalDate dataNaixement;
    String foto;
    boolean eliminat;

    // Constructor
    public DadesPersonals() { }

    public DadesPersonals(String nom, String cognom1, String cognom2, String nif, LocalDate dataNaixement, String foto, boolean eliminat) {
        this.nom = nom;
        this.cognom1 = cognom1;
        this.cognom2 = cognom2;
        this.nif = nif;
        this.dataNaixement = dataNaixement;
        this.foto = foto;
        this.eliminat = eliminat;
    }

    // Mètodes accessors
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getCognom1() { return cognom1; }
    public void setCognom1(String cognom1) { this.cognom1 = cognom1; }

    public String getCognom2() { return cognom2; }
    public void setCognom2(String cognom2) { this.cognom2 = cognom2; }

    public String getNif() { return nif; }
    public void setNif(String nif) { this.nif = nif; }

    public LocalDate getDataNaixement() { return dataNaixement; }
    public void setDataNaixement(LocalDate dataNaixement) { this.dataNaixement = dataNaixement; }

    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }

    public boolean esEliminat() { return eliminat; }
    public void setEliminat(boolean eliminat) { this.eliminat = eliminat; }
}
