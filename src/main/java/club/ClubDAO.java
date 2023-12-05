package main.java.club;

import main.java.persistencia.InterficieDAO;
import main.java.utilitats.Logs;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementació DAO de la classe Club
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class ClubDAO  implements InterficieDAO<Club> {
    final static String ARXIU_LOG = "clubDAO.log";
    private final Connection connexio;
    public ClubDAO(Connection connexio) { this.connexio = connexio; }


    @Override
    public void emmagatzemar(Club club) {
        String sentenciaSQL = "INSERT INTO club (nom , pagina_web, nif, escut, data_creacio) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, club.getNom());
            preparedStatement.setString(2, club.getPaginaWeb());
            preparedStatement.setString(3, club.getNif());
            preparedStatement.setString(4, club.getEscut());
            if(club.getDataCreacio() != null){
                preparedStatement.setDate(5, Date.valueOf(club.getDataCreacio()));
            } else {
                preparedStatement.setDate(5, null);
            }
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut emmagatzemar\n" + e);
        }
    }

    @Override
    public void modificar(Club club) {
        String sentenciaSQL = "UPDATE club SET nom = ?, pagina_web = ?, nif = ?, escut = ?, data_creacio = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, club.getNom());
            preparedStatement.setString(2, club.getPaginaWeb());
            preparedStatement.setString(3, club.getNif());
            preparedStatement.setString(4, club.getEscut());
            if(club.getDataCreacio() != null) {
                preparedStatement.setDate(5, Date.valueOf(club.getDataCreacio()));
            } else {
                preparedStatement.setDate(5, null);
            }
            preparedStatement.setLong(6, club.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut actualitzar\n" + e);
        }
    }

    @Override
    public void eliminar(Long id) {
        String sentenciaSQL = "UPDATE club SET eliminat = ? WHERE id = ?";
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
        String sentenciaSQL = "UPDATE club SET eliminat = ? WHERE id = ?";
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
    public Club obtenir(Long id) {
        Club club = null;
        String sentenciaSQL = "SELECT id, nom , pagina_web, nif, escut, data_creacio, eliminat FROM club WHERE id = ? AND eliminat = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setLong(1, id);
            preparedStatement.setBoolean(2, false);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                club = new Club();
                club.setId(resultSet.getLong("id"));
                club.setNom(resultSet.getString("nom"));
                club.setPaginaWeb(resultSet.getString("pagina_web"));
                club.setNif(resultSet.getString("nif"));
                club.setEscut(resultSet.getString("escut"));
                club.setDataCreacio(resultSet.getObject("data_creacio", LocalDate.class));
                club.setEliminat(resultSet.getBoolean("eliminat"));
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut obtenir\n" + e);
        }
        return club;
    }

    @Override
    public List<Club> obtenirTot() {
        List<Club> clubs = new ArrayList<>();
        String sentenciaSQL = "SELECT id FROM club WHERE eliminat = ? ";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setBoolean(1, false);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Club club = obtenir(resultSet.getLong("id"));
                clubs.add(club);
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'han pogut obtenir tots\n" + e);
        }
        return clubs;
    }
}
