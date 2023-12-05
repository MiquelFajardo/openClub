package main.java.club;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Classe de les dades de Club
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class Club {
    private long id;
    private String nom;
    private String paginaWeb;
    private String escut;
    private String nif;
    private LocalDate dataCreacio;
    private boolean eliminat;

    // Constructor
    public Club() { }

    public Club(long id, String nom, String paginaWeb, String escut, String nif, LocalDate dataCreacio, boolean eliminat) {
        this.id = id;
        this.nom = nom;
        this.paginaWeb = paginaWeb;
        this.escut = escut;
        this.nif = nif;
        this.dataCreacio = dataCreacio;
        this.eliminat = eliminat;
    }

    // Mètodes accessors
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPaginaWeb() { return paginaWeb; }
    public void setPaginaWeb(String paginaWeb) { this.paginaWeb = paginaWeb; }

    public String getEscut() { return escut; }
    public void setEscut(String escut) { this.escut = escut; }

    public String getNif() { return nif; }
    public void setNif(String nif) { this.nif = nif; }

    public LocalDate getDataCreacio() { return dataCreacio; }
    public void setDataCreacio(LocalDate dataCreacio) { this.dataCreacio = dataCreacio; }

    public boolean esEliminat() { return eliminat; }
    public void setEliminat(boolean eliminat) { this.eliminat = eliminat; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Club altreClub = (Club) obj;
        return id == altreClub.id &&
                eliminat == altreClub.eliminat &&
                Objects.equals(nom, altreClub.nom) &&
                Objects.equals(paginaWeb, altreClub.paginaWeb) &&
                Objects.equals(escut, altreClub.escut) &&
                Objects.equals(nif, altreClub.nif) &&
                Objects.equals(dataCreacio, altreClub.dataCreacio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, paginaWeb, escut, nif, dataCreacio, eliminat);
    }
}