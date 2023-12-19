package main.java.areaEsportiva.temporada;

import main.java.persistencia.InterficieDAO;
import main.java.utilitats.Logs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementació DAO de la classe Temporada
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class TemporadaDAO implements InterficieDAO<Temporada> {
    final static String ARXIU_LOG = "temporadaDAO.log";
    private final Connection connexio;
    public TemporadaDAO(Connection connexio) { this.connexio = connexio; }

    @Override
    public void emmagatzemar(Temporada temporada) {
        String sentenciaSQL = "INSERT INTO temporada (id_seccio, any_temporada) VALUES (?, ?)";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setLong(1, temporada.getIdSeccio());
            preparedStatement.setString(2, temporada.getAny());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut emmagatzemar\n" + e);
        }
    }

    @Override
    public void modificar(Temporada temporada) {
        String sentenciaSQL = "UPDATE temporada SET id_seccio = ?, any_temporada = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setLong(1, temporada.getIdSeccio());
            preparedStatement.setString(2, temporada.getAny());
            preparedStatement.setLong(3, temporada.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut modificar\n" + e);
        }
    }

    @Override
    public void eliminar(Long id) {
        String sentenciaSQL = "UPDATE temporada SET eliminat = ? WHERE id = ?";
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
        String sentenciaSQL = "UPDATE temporada SET eliminat = ? WHERE id = ?";
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
    public Temporada obtenir(Long id) {
        Temporada temporada = null;
        String sentenciaSQL = "SELECT id_seccio, any_temporada, eliminat FROM temporada WHERE id = ? AND eliminat = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setLong(1, id);
            preparedStatement.setBoolean(2, false);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                temporada = new Temporada();
                temporada.setId(id);
                temporada.setIdSeccio(resultSet.getLong("id_seccio"));
                temporada.setAny(resultSet.getString("any_temporada"));
                temporada.setEliminat(resultSet.getBoolean("eliminat"));
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut obtenir\n" + e);
        }
        return temporada;
    }

    @Override
    public List<Temporada> obtenirTot() {
        List<Temporada> temporades = new ArrayList<>();
        String sentenciaSQL = "SELECT id FROM temporada WHERE eliminat = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setBoolean(1, false);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                temporades.add(obtenir(resultSet.getLong("id")));
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'han pogut obtenir\n" + e);
        }
        return temporades;
    }
}
