package main.java.membreClub;

import main.java.informacioContacte.dadesPersonals.DadesPersonals;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Classe per les dades de membres del club
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class MembreClub extends DadesPersonals {
    private long id;
    private String nomUsuari;
    private String contrasenya;
    private boolean actiu;

    // Constructor
    public MembreClub() { }

    public MembreClub(long id, String nomUsuari, String contrasenya, boolean actiu, String nom, String cognom1, String cognom2, String nif,
                      LocalDate dataNaixement, String foto, boolean eliminat) {
        super(nom, cognom1, cognom2, nif, dataNaixement, foto, eliminat);
        this.id = id;
        this.nomUsuari = nomUsuari;
        this.contrasenya = contrasenya;
        this.actiu = actiu;
    }

    // Mètodes accessors
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getNomUsuari() { return nomUsuari; }
    public void setNomUsuari(String nomUsuari) { this.nomUsuari = nomUsuari; }

    public String getContrasenya() { return contrasenya; }
    public void setContrasenya(String contrasenya) { this.contrasenya = contrasenya; }

    public boolean esActiu() { return actiu; }
    public void setActiu(boolean actiu) { this.actiu = actiu; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MembreClub that = (MembreClub) o;
        return id == that.id && actiu == that.actiu && Objects.equals(nomUsuari, that.nomUsuari) && Objects.equals(contrasenya, that.contrasenya);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nomUsuari, contrasenya, actiu);
    }
}
