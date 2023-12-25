package main.java.areaEsportiva.jugador;

import main.java.persistencia.InterficieDAO;
import main.java.utilitats.Logs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementació DAO de la classe Jugador
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class JugadorDAO implements InterficieDAO<Jugador> {
    final static String ARXIU_LOG = "jugadorDAO.log";
    private final Connection connexio;
    public JugadorDAO(Connection connexio) { this.connexio = connexio; }

    @Override
    public void emmagatzemar(Jugador jugador) {
        String sentenciaSQL = "INSERT INTO jugador (id_membre_club, id_equip, dorsal, actiu) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setLong(1, jugador.getIdMembre());
            preparedStatement.setLong(2, jugador.getIdEquip());
            preparedStatement.setInt(3, jugador.getDorsal());
            preparedStatement.setBoolean(4, jugador.esActiu());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut emmagatzemar\n" + e);
        }
    }

    @Override
    public void modificar(Jugador jugador) {
        String sentenciaSQL = "UPDATE jugador SET id_membre_club = ?, id_equip = ?, dorsal = ?, actiu = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setLong(1, jugador.getIdMembre());
            preparedStatement.setLong(2, jugador.getIdEquip());
            preparedStatement.setInt(3, jugador.getDorsal());
            preparedStatement.setBoolean(4, jugador.esActiu());
            preparedStatement.setLong(5, jugador.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut modificar\n" + e);
        }
    }

    @Override
    public void eliminar(Long id) {
        String sentenciaSQL = "UPDATE jugador SET eliminat = ? WHERE id = ?";
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
        String sentenciaSQL = "UPDATE jugador SET eliminat = ? WHERE id = ?";
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
    public Jugador obtenir(Long id) {
        Jugador jugador = null;
        String sentenciaSQL = "SELECT id_membre_club, id_equip, dorsal, actiu, eliminat FROM jugador WHERE id = ? AND eliminat = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setLong(1, id);
            preparedStatement.setBoolean(2, false);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                jugador = new Jugador();
                jugador.setId(id);
                jugador.setIdMembre(resultSet.getLong("id_membre_club"));
                jugador.setIdEquip(resultSet.getLong("id_equip"));
                jugador.setDorsal(resultSet.getInt("dorsal"));
                jugador.setActiu(resultSet.getBoolean("actiu"));
                jugador.setEliminat(resultSet.getBoolean("eliminat"));
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut obtenir\n" + e);
        }
        return jugador;
    }

    @Override
    public List<Jugador> obtenirTot() {
        List<Jugador> jugadors = new ArrayList<>();
        String sentenciaSQL = "SELECT id FROM jugador WHERE eliminat = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setBoolean(1, false);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                jugadors.add(obtenir(resultSet.getLong("id")));
            }
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'han pogut obtenir\n" + e);
        }
        return jugadors;
    }

    public List<Jugador> obtenirTotPerEquip(long idEquip) {
        List<Jugador> jugadors = new ArrayList<>();
        String sentenciaSQL = "SELECT id FROM jugador WHERE id_equip = ? AND eliminat = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setLong(1, idEquip);
            preparedStatement.setBoolean(2, false);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                jugadors.add(obtenir(resultSet.getLong("id")));
            }
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'han pogut obtenir per equip\n" + e);
        }
        return jugadors;
    }
}
