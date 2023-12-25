package main.java.areaEsportiva.jugador;

import java.util.Objects;

/**
 * classe per les dades dels jugadors de cada equip
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class Jugador {
    private long id;
    private long idMembre;
    private long idEquip;
    private int dorsal;
    private boolean actiu;
    private boolean eliminat;

    // Constructor
    public Jugador() { }

    public Jugador(long id, long idMembre, long idEquip, int dorsal, boolean actiu, boolean eliminat) {
        this.id = id;
        this.idMembre = idMembre;
        this.idEquip = idEquip;
        this.dorsal = dorsal;
        this.actiu = actiu;
        this.eliminat = eliminat;
    }

    // Mètodes accessors
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getIdMembre() { return idMembre; }
    public void setIdMembre(long idMembre) { this.idMembre = idMembre; }

    public long getIdEquip() { return idEquip; }
    public void setIdEquip(long idEquip) { this.idEquip = idEquip; }

    public int getDorsal() { return dorsal; }
    public void setDorsal(int dorsal) { this.dorsal = dorsal; }

    public boolean esActiu() { return actiu; }
    public void setActiu(boolean actiu) { this.actiu = actiu; }

    public boolean esEliminat() { return eliminat; }
    public void setEliminat(boolean eliminat) { this.eliminat = eliminat; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jugador jugador = (Jugador) o;
        return id == jugador.id && idMembre == jugador.idMembre && idEquip == jugador.idEquip && dorsal == jugador.dorsal
                && actiu == jugador.actiu && eliminat == jugador.eliminat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idMembre, idEquip, dorsal, actiu, eliminat);
    }
}
