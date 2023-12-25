package main.java.areaEsportiva.equipTecnic;

import java.util.Objects;

/**
 * Classe per les dades de l'Equip Tècnic de cada equip
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class EquipTecnic {
    private long id;
    private long idMembre;
    private long idEquip;
    private String carrec;
    private boolean actiu;
    private boolean eliminat;

    // Constructor
    public EquipTecnic() { }

    public EquipTecnic(long id, long idMembre, long idEquip, String carrec, boolean actiu, boolean eliminat) {
        this.id = id;
        this.idMembre = idMembre;
        this.idEquip = idEquip;
        this.carrec = carrec;
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

    public String getCarrec() { return carrec; }
    public void setCarrec(String carrec) { this.carrec = carrec; }

    public boolean esActiu() { return actiu; }
    public void setActiu(boolean actiu) { this.actiu = actiu; }

    public boolean esEliminat() { return eliminat; }
    public void setEliminat(boolean eliminat) { this.eliminat = eliminat; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EquipTecnic that = (EquipTecnic) o;
        return id == that.id && idMembre == that.idMembre && idEquip == that.idEquip && actiu == that.actiu && eliminat == that.eliminat && Objects.equals(carrec, that.carrec);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idMembre, idEquip, carrec, actiu, eliminat);
    }
}
