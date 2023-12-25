package main.java.informacioContacte.poblacio;

import main.java.persistencia.InterficieDAO;
import main.java.utilitats.Logs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementació DAO de la classe Poblacio
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class PoblacioDAO implements InterficieDAO<Poblacio> {
    final static String ARXIU_LOG = "poblacioDAO.log";
    private final Connection connexio;
    public PoblacioDAO(Connection connexio) { this.connexio = connexio; }

    @Override
    public void emmagatzemar(Poblacio poblacio) {
        String sentenciaSQL = "INSERT INTO poblacio (codi_postal, nom, id_provincia) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, poblacio.getCodiPostal());
            preparedStatement.setString(2, poblacio.getNom());
            preparedStatement.setLong(3, poblacio.getIdProvincia());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut emmagatzemar\n" + e);
        }
    }

    @Override
    public void modificar(Poblacio poblacio) {
        String sentenciaSQL = "UPDATE poblacio SET codi_postal = ?, nom = ?, id_provincia = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, poblacio.getCodiPostal());
            preparedStatement.setString(2, poblacio.getNom());
            preparedStatement.setLong(3, poblacio.getIdProvincia());
            preparedStatement.setLong(4, poblacio.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut modificar\n" + e);
        }
    }

    @Override
    public void eliminar(Long id) {
        // No implementat. No es pot eliminar cap població
    }

    @Override
    public void restaurar(Long id) {
        // No implementat. Com que no es pot eliminar cap població, tampoc es pot restaurar
    }

    @Override
    public Poblacio obtenir(Long id) {
        Poblacio poblacio = null;
        String sentenciaSQL = "SELECT codi_postal, nom, id_provincia FROM poblacio WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                poblacio = new Poblacio();
                poblacio.setId(id);
                poblacio.setCodiPostal(resultSet.getString("codi_postal"));
                poblacio.setNom(resultSet.getString("nom"));
                poblacio.setIdProvincia(resultSet.getLong("id_provincia"));
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut obtenir\n" + e);
        }
        return poblacio;
    }

    @Override
    public List<Poblacio> obtenirTot() {
        List<Poblacio> poblacions = new ArrayList<>();
        String sentenciaSQL = "SELECT id FROM poblacio";
        try {
            Statement statement = connexio.createStatement();
            ResultSet resultSet = statement.executeQuery(sentenciaSQL);
            while (resultSet.next()) {
                poblacions.add(obtenir(resultSet.getLong("id")));
            }
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'han pogut obtenir totes\n" + e);
        }
        return poblacions;
    }
}
