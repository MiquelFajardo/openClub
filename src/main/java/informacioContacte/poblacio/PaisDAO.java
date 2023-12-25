package main.java.informacioContacte.poblacio;

import main.java.persistencia.InterficieDAO;
import main.java.utilitats.Logs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementació DAO de la classe Pais
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com> *
*/
public class PaisDAO implements InterficieDAO<Pais> {
    final static String ARXIU_LOG = "paisDAO.log";
    private final Connection connexio;

    public PaisDAO(Connection connexio) { this.connexio = connexio;}

    @Override
    public void emmagatzemar(Pais pais) {
        String sentenciaSQL = "INSERT INTO pais (nom) VALUES (?)";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, pais.getNom());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut emmagatzemar\n" + e);
        }
    }

    @Override
    public void modificar(Pais pais) {
        String sentenciaSQL = "UPDATE pais SET nom = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, pais.getNom());
            preparedStatement.setLong(2, pais.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut modificar\n" + e);
        }
    }

    @Override
    public void eliminar(Long id) {
        // No implementat. No es pot eliminar cap pais
    }

    @Override
    public void restaurar(Long id) {
        // No implementat. Com que no es pot eliminar cap pais, tampoc es pot restaurar
    }

    @Override
    public Pais obtenir(Long id) {
        Pais pais = null;
        String sentenciaSQL = "SELECT nom FROM pais WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                pais = new Pais();
                pais.setId(id);
                pais.setNom(resultSet.getString("nom"));
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut obtenir\n" + e);
        }
        return pais;
    }

    @Override
    public List<Pais> obtenirTot() {
        List<Pais> paissos = new ArrayList<>();
        String sentenciaSQL = "SELECT id FROM pais";
        try {
            Statement statement = connexio.createStatement();
            ResultSet resultSet = statement.executeQuery(sentenciaSQL);
            while (resultSet.next()) {
                paissos.add(obtenir(resultSet.getLong("id")));
            }
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'han pogut obtenir tots\n" + e);
        }
        return paissos;
    }
}
