package test.membreClubTest;

import main.java.membreClub.*;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test membre club
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MembreClubTest {
    private static Connection connexio;

    private static MembreClub membreClub1;
    private static MembreClub membreClub2;
    private static MembreClubDAO membreClubDAO;

    private static PersonaContacte personaContacte1;
    private static PersonaContacte personaContacte2;
    private static PersonaContacteDAO personaContacteDAO;

    @BeforeAll
    public static void setupTest() throws SQLException {
        connexio = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");

        MembreClubTaula.iniciar(connexio);
        membreClubDAO = new MembreClubDAO(connexio);
        membreClub1 = new MembreClub(1, "mfajardo", "contrasenya", true, "Miquel", "Fajardo", "Reyes",
                "40000000A", LocalDate.of(1976,9,29), "foto.jpg", false );
        membreClub2 = new MembreClub(2, "bfajardo", "contrasenya", true, "Blai", "Fajardo", "Viñolas",
                "30000000B", LocalDate.of(2011,6,2), "foto.jpg", false );

        PersonaContacteTaula.iniciar(connexio);
        personaContacteDAO = new PersonaContacteDAO(connexio);
        personaContacte1 = new PersonaContacte(1, 2,  "Miquel", "Fajardo", "pare", "666777888",
                "mfajardo@openclub.cat", false);
        personaContacte2 = new PersonaContacte(2, 1,  "Blas", "Fajardo", "avi", "666777888",
                "mfajardo@openclub.cat", false);
    }

    @Test
    @Order(1)
    public void testEmmagatzemarMembreClub() throws SQLException {
        boolean resultat = false;
        membreClubDAO.emmagatzemar(membreClub1);
        String sentenciaSQL = "SELECT nom_usuari FROM membre_club";
        Statement statement = connexio.createStatement();
        ResultSet resultSet = statement.executeQuery(sentenciaSQL);
        while (resultSet.next()) {
            resultat = true;
            assertEquals(resultSet.getString("nom_usuari"), membreClub1.getNomUsuari());
        }
        assertTrue(resultat);
    }

    @Test
    @Order(2)
    public void testModificarMembreClub() throws SQLException {
        boolean resultat = false;
        String nomNou = "mfajardo1";
        membreClub1.setNomUsuari(nomNou);
        membreClubDAO.modificar(membreClub1);
        String sentenciaSQL = "SELECT nom_usuari FROM membre_club";
        Statement statement = connexio.createStatement();
        ResultSet resultSet = statement.executeQuery(sentenciaSQL);
        while (resultSet.next()) {
            resultat = true;
            assertEquals(resultSet.getString("nom_usuari"), nomNou);
        }
        assertTrue(resultat);
    }

    @Test
    @Order(3)
    public void testEliminarMembreClub() throws SQLException {
        boolean resultat = false;
        membreClubDAO.eliminar(1L);
        String sentenciaSQL = "SELECT eliminat FROM membre_club WHERE id = ?";
        PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
        preparedStatement.setLong(1, 1L);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            resultat = true;
            assertTrue(resultSet.getBoolean("eliminat"));
        }
        assertTrue(resultat);
    }


    @Test
    @Order(4)
    public void testRestaurarMembreClub() throws SQLException {
        boolean resultat = false;
        membreClubDAO.restaurar(1L);
        String sentenciaSQL = "SELECT eliminat FROM membre_club WHERE id = ?";
        PreparedStatement preparedStatement = connexio.prepareStatement(sentenciaSQL);
        preparedStatement.setLong(1, 1L);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            resultat = true;
            assertFalse(resultSet.getBoolean("eliminat"));
        }
        assertTrue(resultat);
    }

    @Test
    @Order(5)
    public void testObtenirMembreClub() {
        MembreClub membreClubObtingut = membreClubDAO.obtenir(1L);
        assertEquals(membreClub1, membreClubObtingut);
    }

    @Test
    @Order(6)
    public void testObtenirTotsElsMembresClub() {
        membreClubDAO.emmagatzemar(membreClub2);
        List<MembreClub> membreClubs = membreClubDAO.obtenirTot();
        assertEquals(2, membreClubs.size());
    }

     @Test
     @Order(7)
     public void testEmmagatzemarPersonaContacte() throws SQLException {
        boolean resultat = false;
        personaContacteDAO.emmagatzemar(personaContacte1);
        String sentencia = "SELECT nom FROM persona_contacte_membre_club";
        Statement statement = connexio.createStatement();
        ResultSet resultSet = statement.executeQuery(sentencia);
        while (resultSet.next()) {
            resultat = true;
            assertEquals(resultSet.getString("nom"), personaContacte1.getNom());
        }
        assertTrue(resultat);
     }
     @Test
     @Order(8)
     public void testModificarPersonaContacte() throws SQLException {
         boolean resultat = false;
         String nomNou = "Miquel Angel";
         personaContacte1.setNom(nomNou);
         personaContacteDAO.modificar(personaContacte1);
         String sentencia = "SELECT nom FROM persona_contacte_membre_club";
         Statement statement = connexio.createStatement();
         ResultSet resultSet = statement.executeQuery(sentencia);
         while (resultSet.next()) {
             resultat = true;
             assertEquals(resultSet.getString("nom"), nomNou);
         }
         assertTrue(resultat);
     }

     @Test
     @Order(9)
     public void testEliminarPersonaContacte() throws SQLException {
         boolean resultat = false;
         personaContacteDAO.eliminar(1L);
         String sentencia = "SELECT eliminat FROM persona_contacte_membre_club";
         Statement statement = connexio.createStatement();
         ResultSet resultSet = statement.executeQuery(sentencia);
         while (resultSet.next()) {
             resultat = true;
             assertTrue(resultSet.getBoolean("eliminat"));
         }
         assertTrue(resultat);
     }

    @Test
    @Order(10)
    public void testRestaurarPersonaContacte() throws SQLException {
        boolean resultat = false;
        personaContacteDAO.restaurar(1L);
        String sentencia = "SELECT eliminat FROM persona_contacte_membre_club";
        Statement statement = connexio.createStatement();
        ResultSet resultSet = statement.executeQuery(sentencia);
        while (resultSet.next()) {
            resultat = true;
            assertFalse(resultSet.getBoolean("eliminat"));
        }
        assertTrue(resultat);
    }

    @Test
    @Order(11)
    public void testObtenirPersonaContacte() {
        PersonaContacte personaContacteObtinguda = personaContacteDAO.obtenir(1L);
        assertEquals(personaContacte1, personaContacteObtinguda);
    }

    @Test
    @Order(12)
    public void testObtenirTotesPersonaContacte() {
        personaContacteDAO.emmagatzemar(personaContacte2);
        List<PersonaContacte> personaContactes = personaContacteDAO.obtenirTot();
        assertEquals(2, personaContactes.size());
    }

    @Test
    @Order(13)
    public void testObtenirTotesPersonaContactePerMembreClub() {
        List<PersonaContacte> personaContactes = personaContacteDAO.obtenirTotPerMembreClub(1L);
        assertEquals(1, personaContactes.size());
    }

    @AfterAll
    public static void tancament() throws SQLException {
        connexio.close();
    }
}
