package main.java.areaEsportiva.equip;

import main.java.persistencia.InterficieDAO;
import main.java.utilitats.Logs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementació DAO de la classe Equip
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class EquipDAO implements InterficieDAO<Equip> {
    final static String ARXIU_LOG = "equipDAO.log";
    private final Connection connexio;
    public EquipDAO(Connection connexio) { this.connexio = connexio; }

    @Override
    public void emmagatzemar(Equip equip) {
        String sentenciaSQL = "INSERT INTO equip (id_temporada, nom, categoria, grup) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setLong(1, equip.getIdTemporada());
            preparedStatement.setString(2, equip.getNom());
            preparedStatement.setString(3, equip.getCategoria());
            preparedStatement.setString(4, equip.getGrup());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut emmagatzemar\n" + e);
        }
    }

    @Override
    public void modificar(Equip equip) {
        String sentenciaSQL = "UPDATE equip SET id_temporada= ?, nom = ?, categoria = ?, grup = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setLong(1, equip.getIdTemporada());
            preparedStatement.setString(2, equip.getNom());
            preparedStatement.setString(3, equip.getCategoria());
            preparedStatement.setString(4, equip.getGrup());
            preparedStatement.setLong(5, equip.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut modificar\n" + e);
        }
    }

    @Override
    public void eliminar(Long id) {
        String sentenciaSQL = "UPDATE equip SET eliminat = ? WHERE id = ?";
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
        String sentenciaSQL = "UPDATE equip SET eliminat = ? WHERE id = ?";
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
    public Equip obtenir(Long id) {
        Equip equip = null;
        String sentenciaSQL = "SELECT id_temporada, nom, categoria, grup, eliminat FROM equip WHERE id = ? AND eliminat = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setLong(1, id);
            preparedStatement.setBoolean(2, false);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                equip = new Equip();
                equip.setId(id);
                equip.setIdTemporada(resultSet.getLong("id_temporada"));
                equip.setNom(resultSet.getString("nom"));
                equip.setCategoria(resultSet.getString("categoria"));
                equip.setGrup(resultSet.getString("grup"));
                equip.setEliminat(resultSet.getBoolean("eliminat"));
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut obtenir\n" + e);
        }
        return equip;
    }

    @Override
    public List<Equip> obtenirTot() {
        List<Equip> equips = new ArrayList<>();
        String sentenciaSQL = "SELECT id FROM equip WHERE eliminat = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setBoolean(1, false);
            ResultSet resultSet  = preparedStatement.executeQuery();
            while (resultSet.next()) {
                equips.add(obtenir(resultSet.getLong("id")));
            }
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'han pogut obtenir\n" + e);
        }
        return equips;
    }
}
