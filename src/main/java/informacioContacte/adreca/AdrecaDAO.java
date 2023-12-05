package main.java.informacioContacte.adreca;

import main.java.persistencia.InterficieExtensaDAO;
import main.java.utilitats.Logs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        String sentenciaSQL = "UPDATE " + nomTaula + " SET eliminat = TRUE WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut eliminar\n" + e);
        }
    }

    @Override
    public void restaurar(Long id, String nomTaula) {
        String sentenciaSQL = "UPDATE " + nomTaula + " SET eliminat = FALSE WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut restaurar\n" + e);
        }
    }

    @Override
    public Adreca obtenir(Long id, String nomTaula) {
        return null;
    }

    @Override
    public List<Adreca> obtenirTot(String nomTaula) {
        return null;
    }

    @Override
    public List<Adreca> obtenirTotPerPropietari(String nomTaula, Long idPropietari) {
        return null;
    }
}