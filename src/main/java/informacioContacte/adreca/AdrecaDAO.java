package main.java.informacioContacte.adreca;

import main.java.persistencia.InterficieExtensaDAO;
import main.java.utilitats.Logs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementació del patró DAO a la classe Adreca
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class AdrecaDAO implements InterficieExtensaDAO<Adreca> {
    final static String ARXIU_LOG = "adrecaDAO.log";
    private final Connection connexio;
    public AdrecaDAO(Connection connexio) { this.connexio = connexio; }

    @Override
    public void emmagatzemar(Adreca adreca, String nomTaula) {
        String sentenciaSQL = "INSERT INTO " + nomTaula + " (tipus, nom_carrer, numero, pis, id_propietari, id_poblacio) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, adreca.getTipus());
            preparedStatement.setString(2, adreca.getNomCarrer());
            preparedStatement.setString(3, adreca.getNumero());
            preparedStatement.setString(4, adreca.getPis());
            preparedStatement.setLong(5, adreca.getIdPropietari());
            preparedStatement.setLong(6, adreca.getIdPoblacio());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut emmagatzemar\n" + e);
        }
    }

    @Override
    public void modificar(Adreca adreca, String nomTaula) {
        String sentenciaSQL = "UPDATE " + nomTaula + " SET tipus = ?, nom_carrer = ?, numero = ?, pis = ?, id_propietari = ?, id_poblacio = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, adreca.getTipus());
            preparedStatement.setString(2, adreca.getNomCarrer());
            preparedStatement.setString(3, adreca.getNumero());
            preparedStatement.setString(4, adreca.getPis());
            preparedStatement.setLong(5, adreca.getIdPropietari());
            preparedStatement.setLong(6, adreca.getIdPoblacio());
            preparedStatement.setLong(7, adreca.getId());
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
    public Adreca obtenir(Long id, String nomTaula) {
        Adreca adreca = null;
        String sentenciaSQL = "SELECT tipus, nom_carrer, numero, pis, id_propietari, id_poblacio, eliminat FROM " + nomTaula + " WHERE id = ? AND eliminat = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setLong(1, id);
            preparedStatement.setBoolean(2, false);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                adreca = new Adreca();
                adreca.setId(id);
                adreca.setTipus(resultSet.getString("tipus"));
                adreca.setNomCarrer(resultSet.getString("nom_carrer"));
                adreca.setNumero(resultSet.getString("numero"));
                adreca.setPis(resultSet.getString("pis"));
                adreca.setIdPropietari(resultSet.getLong("id_propietari"));
                adreca.setIdPoblacio(resultSet.getLong("id_poblacio"));
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut obtenir\n" + e);
        }
        return adreca;
    }

    @Override
    public List<Adreca> obtenirTot(String nomTaula) {
        List<Adreca> adreces = new ArrayList<>();
        String sentenciaSQL = "SELECT id FROM " + nomTaula + " WHERE eliminat = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setBoolean(1, false);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                adreces.add(obtenir(resultSet.getLong("id"), nomTaula));
            }
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'han pogut obtenir totes\n" + e);
        }
        return adreces;
    }

    @Override
    public List<Adreca> obtenirTotPerPropietari(String nomTaula, Long idPropietari) {
        List<Adreca> adreces = new ArrayList<>();
        String sentenciaSQL = "SELECT id FROM " + nomTaula + " WHERE id_propietari = ? AND eliminat = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setLong(1, idPropietari);
            preparedStatement.setBoolean(2, false);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                adreces.add(obtenir(resultSet.getLong("id"), nomTaula));
            }
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'han pogut obtenir totes\n" + e);
        }
        return adreces;
    }
}