package test.informacioContacteTest;


import main.java.informacioContacte.poblacio.Pais;
import main.java.informacioContacte.poblacio.PaisDAO;
import main.java.informacioContacte.poblacio.PaisTaula;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test informacio Contacte
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InformacioContacteTest {
    private static Connection connexio;

    private static Pais pais1;
    private static Pais pais2;
    private static PaisDAO paisDAO;

    @BeforeAll
    public static void setupTest() throws SQLException {
        connexio = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");

        PaisTaula.iniciar(connexio);
        paisDAO = new PaisDAO(connexio);

        pais1 = new Pais(1, "Espanya");
        pais2 = new Pais(2, "Portugal");
    }

    @Test
    @Order(1)
    public void testEmmagatzemarPais() throws SQLException {
        boolean resultat = false;
        paisDAO.emmagatzemar(pais1);
        String sentenciaSQL = "SELECT nom FROM pais";
        Statement statement = connexio.createStatement();
        ResultSet resultSet = statement.executeQuery(sentenciaSQL);
        while(resultSet.next()) {
            resultat = true;
            assertEquals(resultSet.getString("nom"), pais1.getNom());
        }
        assertTrue(resultat);
    }

    @Test
    @Order(2)
    public void testModificarPais() throws SQLException {
        boolean resultat = false;
        String nouNom = "França";
        pais1.setNom(nouNom);
        paisDAO.modificar(pais1);
        String sentenciaSQL = "SELECT nom FROM pais";
        Statement statement = connexio.createStatement();
        ResultSet resultSet = statement.executeQuery(sentenciaSQL);
        while(resultSet.next()) {
            resultat = true;
            assertEquals(resultSet.getString("nom"), nouNom);
        }
        assertTrue(resultat);
    }

    @Test
    @Order(3)
    public void testObtenirPais() {
        Pais paisObtingut = paisDAO.obtenir(1L);
        assertEquals(paisObtingut.toString(), pais1.toString());
    }

    @Test
    @Order(4)
    public void testObtenirTotsElsPaissos() {
        paisDAO.emmagatzemar(pais2);
        List<Pais> paissos = paisDAO.obtenirTot();
        assertEquals(2, paissos.size());
    }

    @AfterAll
    public static void tancament() throws SQLException {
        connexio.close();
    }
}