package test.membreClubTest;

import main.java.membreClub.MembreClub;
import main.java.membreClub.MembreClubDAO;
import main.java.membreClub.MembreClubTaula;
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

    @BeforeAll
    public static void setupTest() throws SQLException {
        connexio = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");

        MembreClubTaula.iniciar(connexio);
        membreClubDAO = new MembreClubDAO(connexio);
        membreClub1 = new MembreClub(1, "mfajardo", "contrasenya", true, "Miquel", "Fajardo", "Reyes",
                "40000000A", LocalDate.of(1976,9,29), "foto.jpg", false );
        membreClub2 = new MembreClub(2, "bfajardo", "contrasenya", true, "Blai", "Fajardo", "Viñolas",
                "30000000B", LocalDate.of(2011,6,2), "foto.jpg", false );
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


    @AfterAll
    public static void tancament() throws SQLException {
        connexio.close();
    }
}
