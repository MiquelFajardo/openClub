package main.java.membreClub;

import main.java.persistencia.InterficieDAO;
import main.java.utilitats.Logs;
;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementació DAO de la classe persona contacte
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class PersonaContacteDAO implements InterficieDAO<PersonaContacte> {
    final static String ARXIU_LOG = "personaContacteDAO.log";
    private final Connection connexio;
    public PersonaContacteDAO(Connection connexio) { this.connexio = connexio; }

    @Override
    public void emmagatzemar(PersonaContacte personaContacte) {
        String sentenciaSQL = "INSERT INTO persona_contacte_membre_club (id_membre, nom, cognoms, parentesc, telefon, correu_electronic) VALUES(?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setLong(1, personaContacte.getIdMembre());
            preparedStatement.setString(2, personaContacte.getNom());
            preparedStatement.setString(3, personaContacte.getCognoms());
            preparedStatement.setString(4, personaContacte.getParentesc());
            preparedStatement.setString(5, personaContacte.getTelefon());
            preparedStatement.setString(6, personaContacte.getCorreuElectronic());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut emmagatzemar\n" + e);
        }
    }

    @Override
    public void modificar(PersonaContacte personaContacte) {
        String sentenciaSQL = "UPDATE persona_contacte_membre_club SET id_membre = ?, nom = ?, cognoms = ?, parentesc = ?, telefon = ?, correu_electronic = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setLong(1, personaContacte.getIdMembre());
            preparedStatement.setString(2, personaContacte.getNom());
            preparedStatement.setString(3, personaContacte.getCognoms());
            preparedStatement.setString(4, personaContacte.getParentesc());
            preparedStatement.setString(5, personaContacte.getTelefon());
            preparedStatement.setString(6, personaContacte.getCorreuElectronic());
            preparedStatement.setLong(7, personaContacte.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut modificar\n" + e);
        }
    }

    @Override
    public void eliminar(Long id) {
        String sentenciaSQL = "UPDATE persona_contacte_membre_club SET eliminat = ? WHERE id = ?";
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
        String sentenciaSQL = "UPDATE persona_contacte_membre_club SET eliminat = ? WHERE id = ?";
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
    public PersonaContacte obtenir(Long id) {
        PersonaContacte personaContacte = null;
        String sentenciaSQL = "SELECT id_membre, nom, cognoms, parentesc, telefon, correu_electronic, eliminat FROM persona_contacte_membre_club WHERE id = ? AND eliminat = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setLong(1, id);
            preparedStatement.setBoolean(2, false);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                personaContacte = new PersonaContacte();
                personaContacte.setId(id);
                personaContacte.setIdMembre(resultSet.getLong("id_membre"));
                personaContacte.setNom(resultSet.getString("nom"));
                personaContacte.setCognoms(resultSet.getString("cognoms"));
                personaContacte.setParentesc(resultSet.getString("parentesc"));
                personaContacte.setTelefon(resultSet.getString("telefon"));
                personaContacte.setCorreuElectronic(resultSet.getString("correu_electronic"));
                personaContacte.setEliminat(resultSet.getBoolean("eliminat"));
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut obtenir\n" + e);
        }
        return personaContacte;
    }

    @Override
    public List<PersonaContacte> obtenirTot() {
        List<PersonaContacte> personaContactes = new ArrayList<>();
        String sentenciaSQL = "SELECT id FROM persona_contacte_membre_club WHERE eliminat = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setBoolean(1, false);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                personaContactes.add(obtenir(resultSet.getLong("id")));
            }
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'han pogut obtenir\n" + e);
        }
        return personaContactes;
    }

    public List<PersonaContacte> obtenirTotPerMembreClub(long idMembre) {
        List<PersonaContacte> personaContactes = new ArrayList<>();
        String sentenciaSQL = "SELECT id FROM persona_contacte_membre_club WHERE id_membre = ? AND eliminat = ?";
        try {
            PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
            preparedStatement.setLong(1, idMembre);
            preparedStatement.setBoolean(2, false);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                personaContactes.add(obtenir(resultSet.getLong("id")));
            }
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'han pogut obtenir per membre club\n" + e);
        }
        return personaContactes;
    }
}