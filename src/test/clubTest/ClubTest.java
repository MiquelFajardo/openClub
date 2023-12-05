package test.clubTest;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import main.java.club.Club;
import main.java.club.ClubDAO;
import main.java.club.ClubTaula;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test de la classe Club
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClubTest {
    final static String NOM_CLUB = "UD openClub";
    private static Connection connexio;

    private static Club club;
    private static Club club2;
    private static ClubDAO clubDAO;

    @BeforeAll
    public static void setupTest() throws SQLException {
        connexio = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");

        ClubTaula.iniciar(connexio);
        clubDAO = new ClubDAO(connexio);

        club = new Club(1L, NOM_CLUB, "www.openclub.cat", "/escut.jpg", "GI17000000",  LocalDate.of(2020, 6,2), false);
        club2 = new Club(2L, NOM_CLUB +" city", "www.openclubcity.cat", "/escut2.jpg", "B06000000",  LocalDate.of(2021, 6,2), false);
    }

    @Test
    @Order(1)
    public void testEmmagatzemarClub() throws SQLException {
        boolean resultat = false;
        clubDAO.emmagatzemar(club);
        String sentenciaSQL = "SELECT id, nom FROM club";
        Statement statement = connexio.createStatement();
        ResultSet resultSet = statement.executeQuery(sentenciaSQL);
        while (resultSet.next()) {
            assertEquals(resultSet.getString("nom"), NOM_CLUB);
            resultat = true;
        }
        assertTrue(resultat);
    }

    @Test
    @Order(2)
    public void testModificarClub() throws SQLException {
        boolean resultat = false;
        String nouNif = "GI000000000";
        club.setNif(nouNif);
        clubDAO.modificar(club);
        String sentenciaSQL = "SELECT id, nif FROM club";
        Statement statement = connexio.createStatement();
        ResultSet resultSet = statement.executeQuery(sentenciaSQL);
        while (resultSet.next()) {
            assertEquals(resultSet.getString("nif"), nouNif);
            resultat = true;
        }
        assertTrue(resultat);
    }

    @Test
    @Order(3)
    public void testEliminarClub() throws SQLException {
        boolean resultat = false;
        clubDAO.eliminar(1L);
        String sentenciaSQL = "SELECT eliminat FROM club WHERE id = 1";
        Statement statement = connexio.createStatement();
        ResultSet resultSet = statement.executeQuery(sentenciaSQL);
        while (resultSet.next()) {
            assertTrue(resultSet.getBoolean("eliminat"));
            resultat = true;
        }
        assertTrue(resultat);
    }

    @Test
    @Order(4)
    public void testRestaurarClub() throws SQLException {
        boolean resultat = false;
        clubDAO.restaurar(1L);
        String sentenciaSQL = "SELECT eliminat FROM club WHERE id = 1";
        Statement statement = connexio.createStatement();
        ResultSet resultSet = statement.executeQuery(sentenciaSQL);
        while (resultSet.next()) {
            assertFalse(resultSet.getBoolean("eliminat"));
            resultat = true;
        }
        assertTrue(resultat);
    }

    @Test
    @Order(5)
    public void testObtenirClub() {
        Club clubObtingut = clubDAO.obtenir(1L);
        assertEquals(clubObtingut.toString(), club.toString());
    }

    @Test
    @Order(6)
    public void testObtenirTotsClubs() {
        clubDAO.emmagatzemar(club2);
        List<Club> clubs = clubDAO.obtenirTot();
        assertTrue(clubs.size() == 2);
    }

    @AfterAll
    public static void tancament() throws SQLException {
        connexio.close();
    }
}