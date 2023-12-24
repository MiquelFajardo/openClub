package main.java.juntaDIrectiva;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Classe per la junta directiva
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class JuntaDirectiva {
    private long id;
    private long idClub;
    private long idMembre;
    private String carrec;
    private LocalDate dataInici;
    private LocalDate dataFinal;
    private boolean actiu;
    private boolean eliminat;

    // Constructor
    public JuntaDirectiva() { }

    public JuntaDirectiva(long id, long idClub, long idMembre, String carrec, LocalDate dataInici, boolean actiu, boolean eliminat) {
        this.id = id;
        this.idClub = idClub;
        this.idMembre = idMembre;
        this.carrec = carrec;
        this.dataInici = dataInici;
        this.dataFinal = dataFinal;
        this.actiu = actiu;
        this.eliminat = eliminat;
    }

    // Mètodes accessors
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getIdClub() { return idClub; }
    public void setIdClub(long idClub) { this.idClub = idClub; }

    public long getIdMembre() { return idMembre; }
    public void setIdMembre(long idMembre) { this.idMembre = idMembre; }

    public String getCarrec() { return carrec; }
    public void setCarrec(String carrec) { this.carrec = carrec; }

    public LocalDate getDataInici() { return dataInici; }
    public void setDataInici(LocalDate dataInici) { this.dataInici = dataInici; }

    public LocalDate getDataFinal() { return dataFinal; }
    public void setDataFinal(LocalDate dataFinal) { this.dataFinal = dataFinal; }

    public boolean esActiu() { return actiu; }
    public void setActiu(boolean actiu) { this.actiu = actiu; }

    public boolean esEliminat() { return eliminat; }
    public void setEliminat(boolean eliminat) { this.eliminat = eliminat; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JuntaDirectiva that = (JuntaDirectiva) o;
        return id == that.id && idMembre == that.idMembre && actiu == that.actiu && eliminat == that.eliminat
                && Objects.equals(carrec, that.carrec) && Objects.equals(dataInici, that.dataInici) && Objects.equals(dataFinal, that.dataFinal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idMembre, carrec, dataInici, dataFinal, actiu, eliminat);
    }
}
