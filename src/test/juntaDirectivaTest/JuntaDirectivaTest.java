package test.juntaDirectivaTest;

import main.java.club.Club;
import main.java.club.ClubDAO;
import main.java.club.ClubTaula;
import main.java.juntaDIrectiva.JuntaDirectiva;
import main.java.juntaDIrectiva.JuntaDirectivaDAO;
import main.java.juntaDIrectiva.JuntaDirectivaTaula;
import main.java.membreClub.MembreClub;
import main.java.membreClub.MembreClubDAO;
import main.java.membreClub.MembreClubTaula;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test junta directiva
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JuntaDirectivaTest {
    final static String NOM_CLUB = "UD openClub";
    private static Connection connexio;

    private static Club club1;
    private static Club club2;
    private static ClubDAO clubDAO;

    private static MembreClub membreClub1;
    private static MembreClub membreClub2;
    private static MembreClubDAO membreClubDAO;

    private static JuntaDirectiva juntaDirectiva1;
    private static JuntaDirectiva juntaDirectiva2;
    private static JuntaDirectivaDAO juntaDirectivaDAO;

    @BeforeAll
    public static void setupTest() throws SQLException {
        connexio = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");

        ClubTaula.iniciar(connexio);
        clubDAO = new ClubDAO(connexio);
        club1 = new Club(1L, NOM_CLUB, "www.openclub.cat", "/escut.jpg", "GI17000000",  LocalDate.of(2020,
                6,2), false);
        club2 = new Club(2L, NOM_CLUB +" city", "www.openclubcity.cat", "/escut2.jpg", "B06000000",  LocalDate.of(2021,
                6,2), false);
        clubDAO.emmagatzemar(club1);
        clubDAO.emmagatzemar(club2);

        MembreClubTaula.iniciar(connexio);
        membreClubDAO = new MembreClubDAO(connexio);
        membreClub1 = new MembreClub(1, "mfajardo", "contrasenya", true, "Miquel", "Fajardo", "Reyes",
                "40000000A", LocalDate.of(1976,9,29), "foto.jpg", false );
        membreClub2 = new MembreClub(2, "bfajardo", "contrasenya", true, "Blai", "Fajardo", "Viñolas",
                "30000000B", LocalDate.of(2011,6,2), "foto.jpg", false );
        membreClubDAO.emmagatzemar(membreClub1);
        membreClubDAO.emmagatzemar(membreClub2);

        JuntaDirectivaTaula.iniciar(connexio);
        juntaDirectivaDAO = new JuntaDirectivaDAO(connexio);
        juntaDirectiva1 = new JuntaDirectiva(1, 1, 1,  "President", LocalDate.of(2023,8,1), true, false);
        juntaDirectiva2 = new JuntaDirectiva(2, 2, 1,  "President", LocalDate.of(2023,8,1), true, false);
    }

    @Test
    @Order(1)
    public void testEmmagatzemarJuntaDirectiva() throws SQLException {
        boolean resultat = false;
        juntaDirectivaDAO.emmagatzemar(juntaDirectiva1);
        String sentenciaSQL = "SELECT carrec FROM junta_directiva";
        Statement statement = connexio.createStatement();
        ResultSet resultSet = statement.executeQuery(sentenciaSQL);
        while (resultSet.next()) {
            resultat = true;
            assertEquals(resultSet.getString("carrec"), juntaDirectiva1.getCarrec());
        }
        assertTrue(resultat);
    }

    @Test
    @Order(2)
    public void testModificarJuntaDirectiva() throws SQLException {
        boolean resultat = false;
        String nouCarrec = "Secretari";
        juntaDirectiva1.setCarrec(nouCarrec);
        juntaDirectivaDAO.modificar(juntaDirectiva1);
        String sentenciaSQL = "SELECT carrec FROM junta_directiva";
        Statement statement = connexio.createStatement();
        ResultSet resultSet = statement.executeQuery(sentenciaSQL);
        while (resultSet.next()) {
            resultat = true;
            assertEquals(resultSet.getString("carrec"), nouCarrec);
        }
        assertTrue(resultat);
    }

    @Test
    @Order(3)
    public void testEliminarJuntaDirectiva() throws SQLException {
        boolean resultat = false;
        String sentenciaSQL = "SELECT eliminat FROM junta_directiva";
        juntaDirectivaDAO.eliminar(1L);
        Statement statement = connexio.createStatement();
        ResultSet resultSet = statement.executeQuery(sentenciaSQL);
        while (resultSet.next()) {
            resultat = true;
            assertTrue(resultSet.getBoolean("eliminat"));
        }
        assertTrue(resultat);
    }

    @Test
    @Order(4)
    public void testRestaurarJuntaDirectiva() throws SQLException {
        boolean resultat = false;
        String sentenciaSQL = "SELECT eliminat FROM junta_directiva";
        juntaDirectivaDAO.restaurar(1L);
        Statement statement = connexio.createStatement();
        ResultSet resultSet = statement.executeQuery(sentenciaSQL);
        while (resultSet.next()) {
            resultat = true;
            assertFalse(resultSet.getBoolean("eliminat"));
        }
        assertTrue(resultat);
    }

    @Test
    @Order(5)
    public void testObtenirJuntaDirectiva() {
        JuntaDirectiva juntaDirectivaObtinguda = juntaDirectivaDAO.obtenir(1L);
        assertEquals(juntaDirectivaObtinguda, juntaDirectiva1);
    }

    @Test
    @Order(6)
    public void testObtenirTotesJuntaDirectiva() {
        juntaDirectivaDAO.emmagatzemar(juntaDirectiva2);
        List<JuntaDirectiva> juntaDirectives = juntaDirectivaDAO.obtenirTot();
        assertEquals(2, juntaDirectives.size());
    }

    @Test
    @Order(7)
    public void testObtenirTotesJuntaDirectivaPerClub() {
        List<JuntaDirectiva> juntaDirectives1 = juntaDirectivaDAO.obtenirTotPerClub(1L);
        List<JuntaDirectiva> juntaDirectives2 = juntaDirectivaDAO.obtenirTotPerClub(2L);
        assertEquals(1, juntaDirectives1.size());
        assertEquals(1, juntaDirectives2.size());
    }

    @Test
    @Order(8)
    public void testEsActiuJuntaDirectiva() throws SQLException {
        juntaDirectiva2.setActiu(false);
        juntaDirectivaDAO.modificar(juntaDirectiva2);
        assertTrue(juntaDirectivaDAO.esActiu(1L));
        assertFalse(juntaDirectivaDAO.esActiu(2L));
    }

    @AfterAll
    public static void tancament() throws SQLException {
        connexio.close();
    }
}
