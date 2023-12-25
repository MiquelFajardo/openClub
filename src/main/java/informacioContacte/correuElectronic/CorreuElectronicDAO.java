package main.java.informacioContacte.correuElectronic;

import main.java.persistencia.InterficieExtensaDAO;
import main.java.utilitats.Logs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementació DAO de la classe CorreuElectronic
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class CorreuElectronicDAO implements InterficieExtensaDAO<CorreuElectronic> {
    final static String ARXIU_LOG = "correuElectronicDAO.log";
    private final Connection connexio;
    public CorreuElectronicDAO(Connection connexio) { this.connexio = connexio; }

    @Override
    public void emmagatzemar(CorreuElectronic correuElectronic, String nomTaula) {
        String sentenciaSQL = "INSERT INTO " + nomTaula+ " (id_propietari, adreca, tipus) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setLong(1, correuElectronic.getIdPropietari());
            preparedStatement.setString(2, correuElectronic.getAdreca());
            preparedStatement.setString(3, correuElectronic.getTipus());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut emmagatzemar\n" + e);
        }
    }

    @Override
    public void modificar(CorreuElectronic correuElectronic, String nomTaula) {
        String sentenciaSQL = "UPDATE " + nomTaula + " SET id_propietari = ?, adreca = ?, tipus = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setLong(1, correuElectronic.getIdPropietari());
            preparedStatement.setString(2, correuElectronic.getAdreca());
            preparedStatement.setString(3, correuElectronic.getTipus());
            preparedStatement.setLong(4, correuElectronic.getId());
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
    public CorreuElectronic obtenir(Long id, String nomTaula) {
        CorreuElectronic correuElectronic = null;
        String sentenciaSQL = "SELECT id_propietari, adreca, tipus, eliminat FROM " + nomTaula + " WHERE id = ? AND eliminat = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setLong(1, id);
            preparedStatement.setBoolean(2, false);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                correuElectronic = new CorreuElectronic();
                correuElectronic.setId(id);
                correuElectronic.setIdPropietari(resultSet.getLong("id_propietari"));
                correuElectronic.setAdreca(resultSet.getString("adreca"));
                correuElectronic.setTipus(resultSet.getString("tipus"));
                correuElectronic.setEliminat(resultSet.getBoolean("eliminat"));
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut obtenir\n" + e);
        }
        return correuElectronic;
    }

    @Override
    public List<CorreuElectronic> obtenirTot(String nomTaula) {
        List<CorreuElectronic> correuElectronics = new ArrayList<>();
        String sentenciaSQL = "SELECT id FROM " + nomTaula + " WHERE eliminat = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setBoolean(1, false);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                correuElectronics.add(obtenir(resultSet.getLong("id"), nomTaula));
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'han pogut obtenir\n" + e);
        }
        return correuElectronics;
    }

    @Override
    public List<CorreuElectronic> obtenirTotPerPropietari(String nomTaula, Long idPropietari) {

        List<CorreuElectronic> correuElectronics = new ArrayList<>();
        String sentenciaSQL = "SELECT id FROM " + nomTaula + " WHERE id_propietari= ? AND eliminat = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setLong(1, idPropietari);
            preparedStatement.setBoolean(2, false);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                correuElectronics.add(obtenir(resultSet.getLong("id"), nomTaula));
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'han pogut obtenir\n" + e);
        }
        return correuElectronics;
    }
}
