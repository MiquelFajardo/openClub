package main.java.areaEsportiva.equipTecnic;

import main.java.persistencia.InterficieDAO;
import main.java.utilitats.Logs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementació DAO de la classe EquipTecnic
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class EquipTecnicDAO implements InterficieDAO<EquipTecnic> {
    final static String ARXIU_LOG = "equipTecnicDAO.log";
    private final Connection connexio;
    public EquipTecnicDAO(Connection connexio) { this.connexio = connexio; }

    @Override
    public void emmagatzemar(EquipTecnic equipTecnic) {
        String sentenciaSQL = "INSERT INTO equip_tecnic (id_membre_club, id_equip, carrec, actiu) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setLong(1, equipTecnic.getIdMembre());
            preparedStatement.setLong(2, equipTecnic.getIdEquip());
            preparedStatement.setString(3, equipTecnic.getCarrec());
            preparedStatement.setBoolean(4, equipTecnic.esActiu());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut emmagatzemar\n" + e);
        }
    }

    @Override
    public void modificar(EquipTecnic equipTecnic) {
        String sentenciaSQL = "UPDATE equip_tecnic SET id_membre_club = ?, id_equip = ?, carrec = ?, actiu = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setLong(1, equipTecnic.getIdMembre());
            preparedStatement.setLong(2, equipTecnic.getIdEquip());
            preparedStatement.setString(3, equipTecnic.getCarrec());
            preparedStatement.setBoolean(4, equipTecnic.esActiu());
            preparedStatement.setLong(5, equipTecnic.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut modificar\n" + e);
        }
    }

    @Override
    public void eliminar(Long id) {
        String sentenciaSQL = "UPDATE equip_tecnic SET eliminat = ? WHERE id = ?";
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
        String sentenciaSQL = "UPDATE equip_tecnic SET eliminat = ? WHERE id = ?";
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
    public EquipTecnic obtenir(Long id) {
        EquipTecnic equipTecnic = null;
        String sentenciaSQL = "SELECT id_membre_club, id_equip, carrec, actiu, eliminat FROM equip_tecnic WHERE id = ? AND eliminat = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setLong(1, id);
            preparedStatement.setBoolean(2, false);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                equipTecnic = new EquipTecnic();
                equipTecnic.setId(id);
                equipTecnic.setIdMembre(resultSet.getLong("id_membre_club"));
                equipTecnic.setIdEquip(resultSet.getLong("id_equip"));
                equipTecnic.setCarrec(resultSet.getString("carrec"));
                equipTecnic.setActiu(resultSet.getBoolean("actiu"));
                equipTecnic.setEliminat(resultSet.getBoolean("eliminat"));
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut obtenir\n" + e);
        }
        return equipTecnic;
    }

    @Override
    public List<EquipTecnic> obtenirTot() {
        List<EquipTecnic> jugadors = new ArrayList<>();
        String sentenciaSQL = "SELECT id FROM equip_tecnic WHERE eliminat = ?";
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

    public List<EquipTecnic> obtenirTotPerEquip(long idEquip) {
        List<EquipTecnic> jugadors = new ArrayList<>();
        String sentenciaSQL = "SELECT id FROM equip_tecnic WHERE id_equip = ? AND eliminat = ?";
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
