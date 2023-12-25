package main.java.informacioContacte.poblacio;

import main.java.persistencia.InterficieDAO;
import main.java.utilitats.Logs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementació DAO de la Provincia
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class ProvinciaDAO implements InterficieDAO<Provincia> {
    final static String ARXIU_LOG = "provinciaDAO.log";
    private final Connection connexio;
    public ProvinciaDAO(Connection connexio) { this.connexio = connexio; }

    @Override
    public void emmagatzemar(Provincia provincia) {
        String sentenciaSQL = "INSERT INTO provincia (nom, id_pais) VALUES (?, ?)";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, provincia.getNom());
            preparedStatement.setLong(2, provincia.getIdPais());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut emmagatzemar\n" + e);
        }
    }

    @Override
    public void modificar(Provincia provincia) {
        String sentenciaSQL = "UPDATE provincia SET nom = ?, id_pais = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, provincia.getNom());
            preparedStatement.setLong(2, provincia.getIdPais());
            preparedStatement.setLong(3, provincia.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut modificar\n" +e);
        }
    }

    @Override
    public void eliminar(Long id) {
        // No implementat. No es pot eliminar cap provincia
    }

    @Override
    public void restaurar(Long id) {
        // No implementat. Com que no es pot eliminar cap provincia, tampoc es pot restaurar
    }

    @Override
    public Provincia obtenir(Long id) {
        Provincia provincia = null;
        String sentenciaSQL = "SELECT nom, id_pais FROM provincia WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                provincia = new Provincia();
                provincia.setId(id);
                provincia.setNom(resultSet.getString("nom"));
                provincia.setIdPais(resultSet.getLong("id_pais"));
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut obtenir\n" + e);
        }
        return provincia;
    }

    @Override
    public List<Provincia> obtenirTot() {
        List<Provincia> provincies = new ArrayList<>();
        String sentenciaSQL = "SELECT id FROM provincia";
        try {
            Statement statement = connexio.createStatement();
            ResultSet resultSet = statement.executeQuery(sentenciaSQL);
            while (resultSet.next()) {
                provincies.add(obtenir(resultSet.getLong("id")));
            }
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut obtenir tot\n" + e);
        }
        return provincies;
    }
}