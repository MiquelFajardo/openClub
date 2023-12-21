package main.java.membreClub;

import main.java.persistencia.InterficieDAO;
import main.java.utilitats.Logs;

import java.time.LocalDate;
import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementació DAO de la classe membresClub
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class MembreClubDAO implements InterficieDAO<MembreClub> {
    final static String ARXIU_LOG = "membreClubDAO.log";
    private final Connection connexio;
    public MembreClubDAO(Connection connexio) { this.connexio = connexio; }

    @Override
    public void emmagatzemar(MembreClub membreClub) {
        String sentenciaSQL ="INSERT INTO membre_club (nom_usuari, contrasenya, actiu, nom, cognom_1, cognom_2, nif, data_naixement, foto) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, membreClub.getNomUsuari());
            preparedStatement.setString(2, membreClub.getContrasenya());
            preparedStatement.setBoolean(3, membreClub.esActiu());
            preparedStatement.setString(4, membreClub.getNom());
            preparedStatement.setString(5, membreClub.getCognom1());
            preparedStatement.setString(6, membreClub.getCognom2());
            preparedStatement.setString(7, membreClub.getNif());
            if(membreClub.getDataNaixement() != null){
                preparedStatement.setDate(8, Date.valueOf(membreClub.getDataNaixement()));
            } else {
                preparedStatement.setDate(8, null);
            }
            preparedStatement.setString(9, membreClub.getFoto());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut emmagatzemar\n" + e);
        }
    }

    @Override
    public void modificar(MembreClub membreClub) {
        String sentenciaSQL = "UPDATE membre_club SET nom_usuari = ?, contrasenya = ?, actiu = ?, nom = ?, cognom_1 = ?, cognom_2 = ?, nif = ?," +
                " data_naixement = ?, foto = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, membreClub.getNomUsuari());
            preparedStatement.setString(2, membreClub.getContrasenya());
            preparedStatement.setBoolean(3, membreClub.esActiu());
            preparedStatement.setString(4, membreClub.getNom());
            preparedStatement.setString(5, membreClub.getCognom1());
            preparedStatement.setString(6, membreClub.getCognom2());
            preparedStatement.setString(7, membreClub.getNif());
            if(membreClub.getDataNaixement() != null){
                preparedStatement.setDate(8, Date.valueOf(membreClub.getDataNaixement()));
            } else {
                preparedStatement.setDate(8, null);
            }
            preparedStatement.setString(9, membreClub.getFoto());
            preparedStatement.setLong(10, membreClub.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No 'sha pogut modificar\n" + e);
        }
    }

    @Override
    public void eliminar(Long id) {
        String sentenciaSQL = "UPDATE membre_club SET eliminat = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setBoolean(1, true);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut eliminar\n" + e);
        }
    }

    @Override
    public void restaurar(Long id) {
        String sentenciaSQL = "UPDATE membre_club SET eliminat = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setBoolean(1, false);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut restaurar\n" + e);
        }
    }

    @Override
    public MembreClub obtenir(Long id) {
        MembreClub membreClub = null;
        String sentenciaSQL = "SELECT nom_usuari, contrasenya, actiu, nom, cognom_1, cognom_2, nif, data_naixement, foto, eliminat FROM membre_club " +
                "WHERE id = ? AND eliminat = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setLong(1, id);
            preparedStatement.setBoolean(2, false);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                membreClub = new MembreClub();
                membreClub.setId(id);
                membreClub.setNomUsuari(resultSet.getString("nom_usuari"));
                membreClub.setContrasenya(resultSet.getString("contrasenya"));
                membreClub.setActiu(resultSet.getBoolean("actiu"));
                membreClub.setNom(resultSet.getString("nom"));
                membreClub.setCognom1(resultSet.getString("cognom_1"));
                membreClub.setCognom2(resultSet.getString("cognom_2"));
                membreClub.setNif(resultSet.getString("nif"));
                membreClub.setDataNaixement(resultSet.getObject("data_naixement", LocalDate.class));
                membreClub.setFoto(resultSet.getString("foto"));
                membreClub.setEliminat(resultSet.getBoolean("eliminat"));
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut obtenir\n" + e);
        }
        return membreClub;
    }

    @Override
    public List<MembreClub> obtenirTot() {
        List<MembreClub> membreClubs = new ArrayList<>();
        String sentenciaSQL = "SELECT id FROM membre_club WHERE eliminat = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setBoolean(1, false);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                membreClubs.add(obtenir(resultSet.getLong("id")));
            }
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'han pogut obtenir\n" + e);
        }
        return membreClubs;
    }
}
