package main.java.membreClub;

import java.util.Objects;

/**
 * Classe persona contacte d'un membre del club
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class PersonaContacte {
    private long id;
    private long idMembre;
    private String nom;
    private String cognoms;
    private String parentesc;
    private String telefon;
    private String correuElectronic;
    private boolean eliminat;

    // Constructor
    public PersonaContacte() { }

    public PersonaContacte(long id, long idMembre, String nom, String cognoms, String parentesc, String telefon, String correuElectronic, boolean eliminat) {
        this.id = id;
        this.idMembre = idMembre;
        this.nom = nom;
        this.cognoms = cognoms;
        this.parentesc = parentesc;
        this.telefon = telefon;
        this.correuElectronic = correuElectronic;
        this.eliminat = eliminat;
    }

    // Mètodes accessors
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getIdMembre() { return idMembre; }
    public void setIdMembre(long idMembre) { this.idMembre = idMembre; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getCognoms() { return cognoms; }
    public void setCognoms(String cognoms) { this.cognoms = cognoms; }

    public String getParentesc() { return parentesc; }
    public void setParentesc(String parentesc) { this.parentesc = parentesc; }

    public String getTelefon() { return telefon; }
    public void setTelefon(String telefon) { this.telefon = telefon; }

    public String getCorreuElectronic() { return correuElectronic; }
    public void setCorreuElectronic(String correuElectronic) { this.correuElectronic = correuElectronic; }

    public boolean esEliminat() { return eliminat; }
    public void setEliminat(boolean eliminat) { this.eliminat = eliminat; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonaContacte that = (PersonaContacte) o;
        return id == that.id && idMembre == that.idMembre && eliminat == that.eliminat && Objects.equals(nom, that.nom)
                && Objects.equals(cognoms, that.cognoms) && Objects.equals(parentesc, that.parentesc)
                && Objects.equals(telefon, that.telefon) && Objects.equals(correuElectronic, that.correuElectronic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idMembre, nom, cognoms, parentesc, telefon, correuElectronic, eliminat);
    }
}