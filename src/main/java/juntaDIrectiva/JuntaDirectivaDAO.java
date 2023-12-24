package main.java.juntaDIrectiva;

import main.java.persistencia.InterficieDAO;
import main.java.utilitats.Logs;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementació DAO de la classe juntaDirectiva
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class JuntaDirectivaDAO implements InterficieDAO<JuntaDirectiva> {
    final static String ARXIU_LOG = "juntaDirectivaDAO.log";
    private final Connection connexio;
    public JuntaDirectivaDAO(Connection connexio) { this.connexio = connexio; }

    @Override
    public void emmagatzemar(JuntaDirectiva juntaDirectiva) {
        String sentenciaSQL = "INSERT INTO junta_directiva (id_club, id_membre_club, carrec, data_inici_carrec, data_final_carrec, actiu) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setLong(1, juntaDirectiva.getIdClub());
            preparedStatement.setLong(2, juntaDirectiva.getIdMembre());
            preparedStatement.setString(3, juntaDirectiva.getCarrec());
            if(juntaDirectiva.getDataInici() != null){
                preparedStatement.setDate(4, Date.valueOf(juntaDirectiva.getDataInici()));
            } else {
                preparedStatement.setDate(4, null);
            }
            if(juntaDirectiva.getDataFinal() != null){
                preparedStatement.setDate(5, Date.valueOf(juntaDirectiva.getDataFinal()));
            } else {
                preparedStatement.setDate(5, null);
            }
            preparedStatement.setBoolean(6, juntaDirectiva.esActiu());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut emmagatzemar\n" + e);
        }
    }

    @Override
    public void modificar(JuntaDirectiva juntaDirectiva) {
        String sentenciaSQL = "UPDATE junta_directiva SET id_club = ?, id_membre_club = ?, carrec = ?, data_inici_carrec = ?, data_final_carrec = ?, actiu = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setLong(1, juntaDirectiva.getIdClub());
            preparedStatement.setLong(2, juntaDirectiva.getIdMembre());
            preparedStatement.setString(3, juntaDirectiva.getCarrec());
            if(juntaDirectiva.getDataInici() != null){
                preparedStatement.setDate(4, Date.valueOf(juntaDirectiva.getDataInici()));
            } else {
                preparedStatement.setDate(4, null);
            }
            if(juntaDirectiva.getDataFinal() != null){
                preparedStatement.setDate(5, Date.valueOf(juntaDirectiva.getDataFinal()));
            } else {
                preparedStatement.setDate(5, null);
            }
            preparedStatement.setBoolean(6, juntaDirectiva.esActiu());
            preparedStatement.setLong(7, juntaDirectiva.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut modificar\n" + e);
        }
    }

    @Override
    public void eliminar(Long id) {
        String sentenciaSQL = "UPDATE junta_directiva SET eliminat = ? WHERE id = ?";
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
        String sentenciaSQL = "UPDATE junta_directiva SET eliminat = ? WHERE id = ?";
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
    public JuntaDirectiva obtenir(Long id) {
        JuntaDirectiva juntaDirectiva = null;
        String sentenciaSQL = "SELECT id_club, id_membre_club, carrec, data_inici_carrec, data_final_carrec, actiu, eliminat FROM junta_directiva WHERE id= ? AND eliminat  =?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setLong(1, id);
            preparedStatement.setBoolean(2, false);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                juntaDirectiva = new JuntaDirectiva();
                juntaDirectiva.setId(id);
                juntaDirectiva.setIdClub(resultSet.getLong("id_club"));
                juntaDirectiva.setIdMembre(resultSet.getLong("id_membre_club"));
                juntaDirectiva.setCarrec(resultSet.getString("carrec"));
                juntaDirectiva.setDataInici(resultSet.getObject("data_inici_carrec", LocalDate.class));
                juntaDirectiva.setDataFinal(resultSet.getObject("data_final_carrec", LocalDate.class));
                juntaDirectiva.setActiu(resultSet.getBoolean("actiu"));
                juntaDirectiva.setEliminat(resultSet.getBoolean("eliminat"));
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut obtenir\n" + e);
        }
        return juntaDirectiva;
    }

    @Override
    public List<JuntaDirectiva> obtenirTot() {
        List<JuntaDirectiva> juntaDirectives = new ArrayList<>();
        String sentenciaSQL = "SELECT id FROM junta_directiva WHERE eliminat = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setBoolean(1, false);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                juntaDirectives.add(obtenir(resultSet.getLong("id")));
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'han pogut obtenir\n" + e);
        }
        return juntaDirectives;
    }

    public List<JuntaDirectiva> obtenirTotPerClub(long idClub) {
        List<JuntaDirectiva> juntaDirectives = new ArrayList<>();
        String sentenciaSQL = "SELECT id FROM junta_directiva WHERE id_club = ? AND eliminat = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setLong(1, idClub);
            preparedStatement.setBoolean(2, false);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                juntaDirectives.add(obtenir(resultSet.getLong("id")));
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'han pogut obtenir per club\n" + e);
        }
        return juntaDirectives;
    }

    public boolean esActiu(long id) {
        boolean actiu = false;
        String sentenciaSQL = "SELECT actiu FROM junta_directiva WHERE id = ? AND eliminat = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setLong(1, id);
            preparedStatement.setBoolean(2, false);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                actiu = resultSet.getBoolean("actiu");
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut obtenir si es actiu\n" + e);
        }
        return actiu;
    }
}
