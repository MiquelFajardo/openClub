package main.java.informacioContacte.telefon;

import main.java.persistencia.InterficieExtensaDAO;
import main.java.utilitats.Logs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementació DAO de la classe Telèfon
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class TelefonDAO implements InterficieExtensaDAO<Telefon> {
    final static String ARXIU_LOG = "telefonDAO.log";
    private final Connection connexio;
    public TelefonDAO(Connection connexio) { this.connexio = connexio; }

    @Override
    public void emmagatzemar(Telefon telefon, String nomTaula) {
        String sentenciaSQL = "INSERT INTO " + nomTaula + " (id_propietari, prefix_pais, numero, tipus) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setLong(1, telefon.getIdPropietari());
            preparedStatement.setString(2, telefon.getPrefixPais());
            preparedStatement.setString(3, telefon.getNumero());
            preparedStatement.setString(4, telefon.getTipus());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut emmagatzemar\n" + e);
        }
    }

    @Override
    public void modificar(Telefon telefon, String nomTaula) {
        String sentenciaSQL = "UPDATE " + nomTaula + " SET id_propietari = ?, prefix_pais = ?, numero = ?, tipus = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setLong(1, telefon.getIdPropietari());
            preparedStatement.setString(2, telefon.getPrefixPais());
            preparedStatement.setString(3, telefon.getNumero());
            preparedStatement.setString(4, telefon.getTipus());
            preparedStatement.setLong(5, telefon.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut modificar\n" + e);
        }
    }

    @Override
    public void eliminar(Long id, String nomTaula) {
        String sentenciaSQL = "UPDATE " + nomTaula + " SET eliminat = ? WHERE id = ?";
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
    public void restaurar(Long id, String nomTaula) {
        String sentenciaSQL = "UPDATE " + nomTaula + " SET eliminat = ? WHERE id = ?";
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
    public Telefon obtenir(Long id, String nomTaula) {
        Telefon telefon = null;
        String sentenciaSQL = "SELECT id_propietari, prefix_pais, numero, tipus, eliminat FROM " + nomTaula + " WHERE id = ? AND eliminat = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setLong(1, id);
            preparedStatement.setBoolean(2, false);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                telefon = new Telefon();
                telefon.setId(id);
                telefon.setIdPropietari(resultSet.getLong("id_propietari"));
                telefon.setPrefixPais(resultSet.getString("prefix_pais"));
                telefon.setNumero(resultSet.getString("numero"));
                telefon.setTipus(resultSet.getString("tipus"));
                telefon.setEliminat(resultSet.getBoolean("eliminat"));
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut obtenir\n" + e);
        }
        return telefon;
    }

    @Override
    public List<Telefon> obtenirTot(String nomTaula) {
        List<Telefon> telefons = new ArrayList<>();
        String sentenciaSQL = "SELECT id FROM " + nomTaula + " WHERE eliminat = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setBoolean(1, false);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                telefons.add(obtenir(resultSet.getLong("id"), nomTaula));
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'han pogut obtenir\n" + e);
        }
        return telefons;
    }

    @Override
    public List<Telefon> obtenirTotPerPropietari(String nomTaula, Long idPropietari) {
        List<Telefon> telefons = new ArrayList<>();
        String sentenciaSQL = "SELECT id FROM " + nomTaula + " WHERE id_propietari = ? AND eliminat = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setLong(1, idPropietari);
            preparedStatement.setBoolean(2, false);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                telefons.add(obtenir(resultSet.getLong("id"), nomTaula));
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'han pogut obtenir per propietari\n" + e);
        }
        return telefons;
    }
}
