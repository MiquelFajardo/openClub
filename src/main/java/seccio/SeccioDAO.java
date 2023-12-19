package main.java.seccio;

import main.java.persistencia.InterficieDAO;
import main.java.utilitats.Logs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementació DAO de la classe secció
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class SeccioDAO implements InterficieDAO<Seccio> {
    final static String ARXIU_LOG = "seccioDAO.log";
    private final Connection connexio;
    public SeccioDAO(Connection connexio) { this.connexio = connexio; }

    @Override
    public void emmagatzemar(Seccio seccio) {
        String sentenciaSQL = "INSERT INTO seccio (id_club, nom) VALUES (?, ?)";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setLong(1, seccio.getIdClub());
            preparedStatement.setString(2, seccio.getNom());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut emmagatzemar\n" + e);
        }
    }

    @Override
    public void modificar(Seccio seccio) {
        String sentenciaSQL = "UPDATE seccio SET id_club = ?, nom = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setLong(1, seccio.getIdClub());
            preparedStatement.setString(2, seccio.getNom());
            preparedStatement.setLong(3, seccio.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut modificar\n" + e);
        }
    }

    @Override
    public void eliminar(Long id) {
        String sentenciaSQL = "UPDATE seccio SET eliminat = ? WHERE id = ?";
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
        String sentenciaSQL = "UPDATE seccio SET eliminat = ? WHERE id = ?";
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
    public Seccio obtenir(Long id) {
        Seccio seccio = null;
        String sentenciaSQL = "SELECT id_club, nom, eliminat FROM seccio WHERE id = ? AND eliminat = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setLong(1, id);
            preparedStatement.setBoolean(2, false);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                seccio = new Seccio();
                seccio.setId(id);
                seccio.setIdClub(resultSet.getLong("id_club"));
                seccio.setNom(resultSet.getString("nom"));
                seccio.setEliminat(resultSet.getBoolean("eliminat"));
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut obtenir\n" + e);
        }
        return seccio;
    }

    @Override
    public List<Seccio> obtenirTot() {
        List<Seccio> seccions = new ArrayList<>();
        String sentenciaSQL = "SELECT id FROM seccio WHERE eliminat = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setBoolean(1, false);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                seccions.add(obtenir(resultSet.getLong("id")));
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'han pogut obtenir\n" + e);
        }
        return seccions;
    }
}
