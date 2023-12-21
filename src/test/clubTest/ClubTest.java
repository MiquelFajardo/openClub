package test.clubTest;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import main.java.areaEsportiva.equip.*;
import main.java.areaEsportiva.temporada.*;
import main.java.club.*;
import main.java.areaEsportiva.seccio.*;

/**
 * Test de la classe Club, secció
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

    private static Seccio seccio1;
    private static Seccio seccio2;
    private static SeccioDAO seccioDAO;

    private static Temporada temporada1;
    private static Temporada temporada2;
    private static TemporadaDAO temporadaDAO;

    private static Equip equip1;
    private static Equip equip2;
    private static EquipDAO equipDAO;

    @BeforeAll
    public static void setupTest() throws SQLException {
        connexio = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");

        ClubTaula.iniciar(connexio);
        clubDAO = new ClubDAO(connexio);
        club = new Club(1L, NOM_CLUB, "www.openclub.cat", "/escut.jpg", "GI17000000",  LocalDate.of(2020, 6,2), false);
        club2 = new Club(2L, NOM_CLUB +" city", "www.openclubcity.cat", "/escut2.jpg", "B06000000",  LocalDate.of(2021, 6,2), false);

        SeccioTaula.iniciar(connexio);
        seccioDAO = new SeccioDAO(connexio);
        seccio1 = new Seccio(1L,1L, "Futbol", false);
        seccio2 = new Seccio(2L,1L, "Basquet", false);

        TemporadaTaula.iniciar(connexio);
        temporadaDAO = new TemporadaDAO(connexio);
        temporada1 = new Temporada(1L, 1L, "2022-2023", false);
        temporada2 = new Temporada(1L, 1L, "2023-2024", false);

        EquipTaula.iniciar(connexio);
        equipDAO = new EquipDAO(connexio);
        equip1 = new Equip(1L,1L, "Infantil A", "Primera", "17", false);
        equip2 = new Equip(2L,1L, "Infantil B", "Segona", "34", false);
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
        assertEquals(2, clubs.size());
    }

    @Test
    @Order(7)
    public void testEmmagatzemarSeccio() throws SQLException {
        boolean resultat = false;
        seccioDAO.emmagatzemar(seccio1);
        String sentenciaSQL = "SELECT nom FROM seccio";
        Statement statement = connexio.createStatement();
        ResultSet resultSet = statement.executeQuery(sentenciaSQL);
        while (resultSet.next()) {
            resultat = true;
            assertEquals(resultSet.getString("nom"), seccio1.getNom());
        }
        assertTrue(resultat);
    }

    @Test
    @Order(8)
    public void testModificarSeccio() throws SQLException {
        boolean resultat = false;
        String novaSeccio = "Futbol sala";
        seccio1.setNom(novaSeccio);
        seccioDAO.modificar(seccio1);
        String sentenciaSQL = "SELECT nom FROM seccio";
        Statement statement = connexio.createStatement();
        ResultSet resultSet = statement.executeQuery(sentenciaSQL);
        while (resultSet.next()) {
            resultat = true;
            assertEquals(resultSet.getString("nom"), novaSeccio);
        }
        assertTrue(resultat);
    }

    @Test
    @Order(9)
    public void testEliminarSeccio() throws SQLException {
        boolean resultat = false;
        seccioDAO.eliminar(1L);
        String sentenciaSQL = "SELECT eliminat FROM seccio";
        Statement statement = connexio.createStatement();
        ResultSet resultSet = statement.executeQuery(sentenciaSQL);
        while (resultSet.next()) {
            resultat = true;
            assertTrue(resultSet.getBoolean("eliminat"));
        }
        assertTrue(resultat);
    }

    @Test
    @Order(10)
    public void testRestaurarSeccio() throws SQLException {
        boolean resultat = false;
        seccioDAO.restaurar(1L);
        String sentenciaSQL = "SELECT eliminat FROM seccio";
        Statement statement = connexio.createStatement();
        ResultSet resultSet = statement.executeQuery(sentenciaSQL);
        while (resultSet.next()) {
            resultat = true;
            assertFalse(resultSet.getBoolean("eliminat"));
        }
        assertTrue(resultat);
    }

    @Test
    @Order(11)
    public void testObtenirSeccio() {
        Seccio seccioObtinguda = seccioDAO.obtenir(1L);
        assertEquals(seccioObtinguda, seccio1);
    }

    @Test
    @Order(12)
    public void testObtenirTotesLesSeccions() {
        seccioDAO.emmagatzemar(seccio2);
        List<Seccio> seccions = seccioDAO.obtenirTot();
        assertEquals(2, seccions.size());
    }

    @Test
    @Order(13)
    public void testEmmagatzemarTemporada() throws SQLException {
        boolean resultat = false;
        temporadaDAO.emmagatzemar(temporada1);
        String sentenciaSQL = "SELECT any_temporada FROM temporada";
        Statement statement = connexio.createStatement();
        ResultSet resultSet = statement.executeQuery(sentenciaSQL);
        while (resultSet.next()) {
            resultat = true;
            assertEquals(resultSet.getString("any_temporada"), temporada1.getAny());
        }
        assertTrue(resultat);
    }

    @Test
    @Order(14)
    public void testModificarTemporada() throws SQLException {
        boolean resultat = false;
        String nouAny = "2022";
        temporada1.setAny(nouAny);
        temporadaDAO.modificar(temporada1);
        String sentenciaSQL = "SELECT any_temporada FROM temporada";
        Statement statement = connexio.createStatement();
        ResultSet resultSet = statement.executeQuery(sentenciaSQL);
        while (resultSet.next()) {
            resultat = true;
            assertEquals(resultSet.getString("any_temporada"),nouAny);
        }
        assertTrue(resultat);
    }

    @Test
    @Order(15)
    public void testEliminarTemporada() throws SQLException {
        boolean resultat = false;
        temporadaDAO.eliminar(1L);
        String sentenciaSQL = "SELECT eliminat FROM temporada";
        Statement statement = connexio.createStatement();
        ResultSet resultSet = statement.executeQuery(sentenciaSQL);
        while (resultSet.next()) {
            resultat = true;
            assertTrue(resultSet.getBoolean("eliminat"));
        }
        assertTrue(resultat);
    }

    @Test
    @Order(16)
    public void testRestaurarTemporada() throws SQLException {
        boolean resultat = false;
        temporadaDAO.restaurar(1L);
        String sentenciaSQL = "SELECT eliminat FROM temporada";
        Statement statement = connexio.createStatement();
        ResultSet resultSet = statement.executeQuery(sentenciaSQL);
        while (resultSet.next()) {
            resultat = true;
            assertFalse(resultSet.getBoolean("eliminat"));
        }
        assertTrue(resultat);
    }

    @Test
    @Order(17)
    public void testObtenirTemporada() {
        Temporada temporadaObtinguda = temporadaDAO.obtenir(1L);
        assertEquals(temporadaObtinguda, temporada1);
    }

    @Test
    @Order(18)
    public void testObtenirTotesLesTemporades() {
        temporadaDAO.emmagatzemar(temporada2);
        List<Temporada> temporades = temporadaDAO.obtenirTot();
        assertEquals(2, temporades.size());
    }

    @Test
    @Order(19)
    public void testEmmagatzemarEquip () throws SQLException {
        boolean resultat = false;
        equipDAO.emmagatzemar(equip1);
        String sentenciaSQL = "SELECT nom FROM equip";
        Statement statement = connexio.createStatement();
        ResultSet resultSet = statement.executeQuery(sentenciaSQL);
        while (resultSet.next()) {
            resultat = true;
            assertEquals(resultSet.getString("nom"), "Infantil A");
        }
        assertTrue(resultat);
    }

    @Test
    @Order(20)
    public void testModificarEquip () throws SQLException {
        boolean resultat = false;
        String nouNom = "Cadet A";
        equip1.setNom(nouNom);
        equipDAO.modificar(equip1);
        String sentenciaSQL = "SELECT nom FROM equip";
        Statement statement = connexio.createStatement();
        ResultSet resultSet = statement.executeQuery(sentenciaSQL);
        while (resultSet.next()) {
            resultat = true;
            assertEquals(resultSet.getString("nom"), nouNom);
        }
        assertTrue(resultat);
    }

    @Test
    @Order(21)
    public void testEliminarEquip () throws SQLException {
        boolean resultat = false;
        equipDAO.eliminar(1L);
        String sentenciaSQL = "SELECT eliminat FROM equip";
        Statement statement = connexio.createStatement();
        ResultSet resultSet = statement.executeQuery(sentenciaSQL);
        while (resultSet.next()) {
            resultat = true;
            assertTrue(resultSet.getBoolean("eliminat"));
        }
        assertTrue(resultat);
    }

    @Test
    @Order(22)
    public void testRestaurarEquip () throws SQLException {
        boolean resultat = false;
        equipDAO.restaurar(1L);
        String sentenciaSQL = "SELECT eliminat FROM equip";
        Statement statement = connexio.createStatement();
        ResultSet resultSet = statement.executeQuery(sentenciaSQL);
        while (resultSet.next()) {
            resultat = true;
            assertFalse(resultSet.getBoolean("eliminat"));
        }
        assertTrue(resultat);
    }

    @Test
    @Order(23)
    public void testObtenirEquip() {
        Equip equipObtingut = equipDAO.obtenir(1L);
        assertEquals(equipObtingut.getCategoria(), "Primera");
    }

    @Test
    @Order(24)
    public void testObtenirTotsElsEquips() {
        equipDAO.emmagatzemar(equip2);
        List<Equip> equips = equipDAO.obtenirTot();
        assertEquals(2, equips.size());
    }

    @AfterAll
    public static void tancament() throws SQLException {
        connexio.close();
    }
}