package main.java.areaEsportiva.temporada;

import java.util.Objects;

/**
 * Classe amb les dades de les temporades
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class Temporada {
    private long id;
    private long idSeccio;
    private String any;
    private boolean eliminat;

    // Constructor
    public Temporada() { }

    public Temporada(long id, long idSeccio, String any, boolean eliminat) {
        this.id = id;
        this.idSeccio = idSeccio;
        this.any = any;
        this.eliminat = eliminat;
    }

    // Mètodes accessors
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getIdSeccio() { return idSeccio; }
    public void setIdSeccio(long idSeccio) { this.idSeccio = idSeccio; }

    public String getAny() { return any; }
    public void setAny(String any) { this.any = any; }

    public boolean esEliminat() { return eliminat; }
    public void setEliminat(boolean eliminat) { this.eliminat = eliminat; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Temporada temporada = (Temporada) o;
        return id == temporada.id && idSeccio == temporada.idSeccio && eliminat == temporada.eliminat && Objects.equals(any, temporada.any);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idSeccio, any, eliminat);
    }
}
