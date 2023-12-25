package test.informacioContacteTest;


import main.java.club.Club;
import main.java.club.ClubDAO;
import main.java.club.ClubTaula;
import main.java.informacioContacte.adreca.Adreca;
import main.java.informacioContacte.adreca.AdrecaDAO;
import main.java.informacioContacte.adreca.AdrecaTaula;
import main.java.informacioContacte.correuElectronic.CorreuElectronic;
import main.java.informacioContacte.correuElectronic.CorreuElectronicDAO;
import main.java.informacioContacte.correuElectronic.CorreuElectronicTaula;
import main.java.informacioContacte.poblacio.*;
import main.java.informacioContacte.telefon.Telefon;
import main.java.informacioContacte.telefon.TelefonDAO;
import main.java.informacioContacte.telefon.TelefonTaula;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test informacio Contacte
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InformacioContacteTest {
    private static Connection connexio;

    final static String NOM_CLUB = "UD openClub";
    private static Club club;
    private static Club club2;
    private static ClubDAO clubDAO;

    private static Pais pais1;
    private static Pais pais2;
    private static PaisDAO paisDAO;

    private static Provincia provincia1;
    private static Provincia provincia2;
    private static ProvinciaDAO provinciaDAO;

    private static Poblacio poblacio1;
    private static Poblacio poblacio2;
    private static PoblacioDAO poblacioDAO;

    private static Adreca adreca1;
    private static Adreca adreca2;
    private static AdrecaDAO adrecaDAO;

    private static Telefon telefon1;
    private static Telefon telefon2;
    private static TelefonDAO telefonDAO;

    private static CorreuElectronic correuElectronic1;
    private static CorreuElectronic correuElectronic2;
    private static CorreuElectronicDAO correuElectronicDAO;

    @BeforeAll
    public static void setupTest() throws SQLException {
        connexio = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");

        ClubTaula.iniciar(connexio);
        clubDAO = new ClubDAO(connexio);
        club = new Club(1L, NOM_CLUB, "www.openclub.cat", "/escut.jpg", "GI17000000",  LocalDate.of(2020, 6,2), false);
        club2 = new Club(1L, NOM_CLUB, "www.openclub.cat", "/escut.jpg", "GI17000000",  LocalDate.of(2020, 6,2), false);
        clubDAO.emmagatzemar(club);
        clubDAO.emmagatzemar(club2);

        PaisTaula.iniciar(connexio);
        paisDAO = new PaisDAO(connexio);
        pais1 = new Pais(1, "Espanya");
        pais2 = new Pais(2, "Portugal");

        ProvinciaTaula.iniciar(connexio);
        provinciaDAO = new ProvinciaDAO(connexio);
        provincia1 = new Provincia(1, "Catalunya", 1);
        provincia2 = new Provincia(2, "Andalusia", 1);

        PoblacioTaula.iniciar(connexio);
        poblacioDAO = new PoblacioDAO(connexio);
        poblacio1 = new Poblacio(1, "17244", "Cassa de la Selva", 1);
        poblacio2 = new Poblacio(2, "17003", "Girona", 1);

        AdrecaTaula.iniciar(connexio, "adreca_club", "club");
        adrecaDAO = new AdrecaDAO(connexio);
        adreca1 = new Adreca(1, 1, "principal", "openStreet", "13", null, 1, false);
        adreca2 = new Adreca(2, 2, "secundaria", "openStreet", "1", null, 2, false);

        TelefonTaula.iniciar(connexio, "telefon_club", "club");
        telefonDAO = new TelefonDAO(connexio);
        telefon1 = new Telefon(1, 1, "+034", "972460000", "Oficina", false);
        telefon2 = new Telefon(2, 2, "+034", "930800000", "Delegació", false);

        CorreuElectronicTaula.iniciar(connexio, "correu_electronic_club", "club");
        correuElectronicDAO = new CorreuElectronicDAO(connexio);
        correuElectronic1 = new CorreuElectronic(1, 1, "info@openClub.cat", "principal", false);
        correuElectronic2 = new CorreuElectronic(1, 1, "alta@openClub.cat", "altes", false);
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
    public void testObtenirPaissos() {
        paisDAO.emmagatzemar(pais2);
        List<Pais> paissos = paisDAO.obtenirTot();
        assertEquals(2, paissos.size());
    }

    @Test
    @Order(5)
    public void testEmmagatzemarProvincia() throws SQLException {
        boolean resultat = false;
        provinciaDAO.emmagatzemar(provincia1);
        String sentenciaSQL = "SELECT nom FROM provincia";
        Statement statement = connexio.createStatement();
        ResultSet resultSet = statement.executeQuery(sentenciaSQL);
        while (resultSet.next()) {
            resultat = true;
            assertEquals(resultSet.getString("nom"), provincia1.getNom());
        }
        assertTrue(resultat);
    }

    @Test
    @Order(6)
    public void testModificarProvincia() throws SQLException {
        boolean resultat = false;
        String nomNou = "Algarve";
        int nouIdPais = 2;
        provincia1.setNom(nomNou);
        provincia1.setIdPais(nouIdPais);
        provinciaDAO.modificar(provincia1);
        String sentenciaSQL = "SELECT nom, id_pais FROM provincia";
        Statement statement = connexio.createStatement();
        ResultSet resultSet = statement.executeQuery(sentenciaSQL);
        while (resultSet.next()) {
            resultat = true;
            assertEquals(resultSet.getString("nom"), nomNou);
            assertEquals(resultSet.getLong("id_pais"), nouIdPais);
        }
        assertTrue(resultat);
    }

    @Test
    @Order(7)
    public void testObtenirProvincia() {
        Provincia provinciaObtinguda = provinciaDAO.obtenir(1L);
        assertEquals(provinciaObtinguda.toString(), provincia1.toString());
    }

    @Test
    @Order(8)
    public void testObtenirProvincies() {
        provinciaDAO.emmagatzemar(provincia2);
        List<Provincia> provinces = provinciaDAO.obtenirTot();
        assertEquals(2, provinces.size());
    }

    @Test
    @Order(9)
    public void testEmmagatzemarPoblacio() throws SQLException {
        boolean resultat = false;
        poblacioDAO.emmagatzemar(poblacio1);
        String sentenciaSQL = "SELECT codi_postal, nom FROM poblacio";
        Statement statement = connexio.createStatement();
        ResultSet resultSet = statement.executeQuery(sentenciaSQL);
        while (resultSet.next()) {
            resultat = true;
            assertEquals(resultSet.getString("codi_postal"), poblacio1.getCodiPostal());
            assertEquals(resultSet.getString("nom"), poblacio1.getNom());
        }
        assertTrue(resultat);
    }

    @Test
    @Order(10)
    public void testModificarPoblacio() throws SQLException {
        boolean resultat = false;
        String nomNou = "Cassà de la Selva";
        poblacio1.setNom(nomNou);
        poblacioDAO.modificar(poblacio1);
        String sentenciaSQL = "SELECT nom FROM poblacio";
        Statement statement = connexio.createStatement();
        ResultSet resultSet = statement.executeQuery(sentenciaSQL);
        while (resultSet.next()) {
            resultat = true;
            assertEquals(resultSet.getString("nom"), nomNou);
        }
        assertTrue(resultat);
    }

    @Test
    @Order(11)
    public void testObtenirPoblacio() {
        Poblacio poblacioObtinguda = poblacioDAO.obtenir(1L);
        assertEquals(poblacioObtinguda.toString(), poblacio1.toString());
    }

    @Test
    @Order(12)
    public void testObtenirPoblacions() {
        poblacioDAO.emmagatzemar(poblacio2);
        List<Poblacio> poblacions = poblacioDAO.obtenirTot();
        assertEquals(2, poblacions.size());
    }

    @Test
    @Order(13)
    public void testEmmagatzemarAdreca() throws SQLException {
        boolean resultat = false;
        adrecaDAO.emmagatzemar(adreca1, "adreca_club");
        String sentenciaSQL = "SELECT nom_carrer FROM adreca_club";
        Statement statement = connexio.createStatement();
        ResultSet resultSet = statement.executeQuery(sentenciaSQL);
        while (resultSet.next()) {
            resultat = true;
            assertEquals(adreca1.getNomCarrer(), resultSet.getString("nom_carrer"));
        }
        assertTrue(resultat);
    }

    @Test
    @Order(14)
    public void testModificarAdreca() throws SQLException {
        boolean resultat = false;
        String nomNou = "mediterrani";
        adreca1.setNomCarrer(nomNou);
        adrecaDAO.modificar(adreca1, "adreca_club");
        String sentenciaSQL = "SELECT nom_carrer FROM adreca_club";
        Statement statement = connexio.createStatement();
        ResultSet resultSet = statement.executeQuery(sentenciaSQL);
        while (resultSet.next()) {
            resultat = true;
            assertEquals(nomNou, resultSet.getString("nom_carrer"));
        }
        assertTrue(resultat);
    }

    @Test
    @Order(15)
    public void testEliminarAdreca() throws SQLException {
        boolean resultat = false;
        adrecaDAO.eliminar(1L, "adreca_club");
        String sentenciaSQL = "SELECT eliminat FROM adreca_club WHERE id = 1";
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
    public void testObtenirAdrecaEliminada() {
        Adreca adrecaObtinguda = adrecaDAO.obtenir(1L, "adreca_club");
        assertNull(adrecaObtinguda);
    }

    @Test
    @Order(17)
    public void testRestaurarAdreca() throws SQLException {
        boolean resultat = false;
        adrecaDAO.restaurar(1L, "adreca_club");
        String sentenciaSQL = "SELECT eliminat FROM adreca_club WHERE id = 1";
        Statement statement = connexio.createStatement();
        ResultSet resultSet = statement.executeQuery(sentenciaSQL);
        while (resultSet.next()) {
            resultat = true;
            assertFalse(resultSet.getBoolean("eliminat"));
        }
        assertTrue(resultat);
    }

    @Test
    @Order(18)
    public void testObtenirAdreca() {
        Adreca adrecaObtinguda = adrecaDAO.obtenir(1L, "adreca_club");
        assertEquals(adreca1, adrecaObtinguda);
    }

    @Test
    @Order(19)
    public void testObtenirAdreces() {
        adrecaDAO.emmagatzemar(adreca2, "adreca_club");
        List<Adreca> adreces = adrecaDAO.obtenirTot("adreca_club");
        assertEquals(2, adreces.size());
    }

    @Test
    @Order(20)
    public void testObtenirAdrecesPerPropietari() {
        adrecaDAO.emmagatzemar(adreca2, "adreca_club");
        List<Adreca> adreces = adrecaDAO.obtenirTotPerPropietari("adreca_club",1L);
        assertEquals(1, adreces.size());
    }

    @Test
    @Order(21)
    public void testEmmagatzemarTelefon() throws SQLException {
        boolean resultat = false;
        telefonDAO.emmagatzemar(telefon1, "telefon_club");
        String sentenciaSQL = "SELECT numero FROM telefon_club";
        Statement statement = connexio.createStatement();
        ResultSet resultSet = statement.executeQuery(sentenciaSQL);
        while(resultSet.next()) {
            resultat = true;
            assertEquals(resultSet.getString("numero"), telefon1.getNumero());
        }
        assertTrue(resultat);
    }

    @Test
    @Order(22)
    public void testModificarTelefon() throws SQLException {
        boolean resultat = false;
        String telefonNou = "972464646";
        telefon1.setNumero(telefonNou);
        telefonDAO.modificar(telefon1, "telefon_club");
        String sentenciaSQL = "SELECT numero FROM telefon_club";
        Statement statement = connexio.createStatement();
        ResultSet resultSet = statement.executeQuery(sentenciaSQL);
        while(resultSet.next()) {
            resultat = true;
            assertEquals(resultSet.getString("numero"), telefonNou);
        }
        assertTrue(resultat);
    }

    @Test
    @Order(23)
    public void testEliminarTelefon() throws SQLException {
        boolean resultat = false;
        telefonDAO.eliminar(1L, "telefon_club");
        String sentenciaSQL = "SELECT eliminat FROM telefon_club WHERE id = 1";
        Statement statement = connexio.createStatement();
        ResultSet resultSet = statement.executeQuery(sentenciaSQL);
        while (resultSet.next()) {
            resultat = true;
            assertTrue(resultSet.getBoolean("eliminat"));
        }
        assertTrue(resultat);
    }

    @Test
    @Order(24)
    public void testRestaurarTelefon() throws SQLException {
        boolean resultat = false;
        telefonDAO.restaurar(1L, "telefon_club");
        String sentenciaSQL = "SELECT eliminat FROM telefon_club WHERE id = 1";
        Statement statement = connexio.createStatement();
        ResultSet resultSet = statement.executeQuery(sentenciaSQL);
        while (resultSet.next()) {
            resultat = true;
            assertFalse(resultSet.getBoolean("eliminat"));
        }
        assertTrue(resultat);
    }

    @Test
    @Order(25)
    public void testObtenirTelefon() {
        Telefon telefonObtingut = telefonDAO.obtenir(1L, "telefon_club");
        assertEquals(telefonObtingut, telefon1);
    }

    @Test
    @Order(26)
    public void testObtenirTelefons() {
        telefonDAO.emmagatzemar(telefon2, "telefon_club");
        List<Telefon> telefons = telefonDAO.obtenirTot("telefon_club");
        assertEquals(2, telefons.size());
    }

    @Test
    @Order(27)
    public void testObtenirTelefonsPerPropietari() {
        List<Telefon> telefons = telefonDAO.obtenirTotPerPropietari("telefon_club", 2L);
        assertEquals(1, telefons.size());
    }

    @Test
    @Order(28)
    public void testEmmagatzemarCorreuElectronic() throws SQLException {
        boolean resultat = false;
        correuElectronicDAO.emmagatzemar(correuElectronic1, "correu_electronic_club");
        String sentenciaSQL = "SELECT adreca FROM correu_electronic_club";
        Statement statement = connexio.createStatement();
        ResultSet resultSet = statement.executeQuery(sentenciaSQL);
        while (resultSet.next()) {
            resultat = true;
            assertEquals(resultSet.getString("adreca"), correuElectronic1.getAdreca());
        }
        assertTrue(resultat);
    }

    @Test
    @Order(29)
    public void testModificarCorreuElectronic() throws SQLException {
        boolean resultat = false;
        String novaAdreca = "hola@openclub.cat";
        correuElectronic1.setAdreca(novaAdreca);
        correuElectronicDAO.modificar(correuElectronic1, "correu_electronic_club");
        String sentenciaSQL = "SELECT adreca FROM correu_electronic_club";
        Statement statement = connexio.createStatement();
        ResultSet resultSet = statement.executeQuery(sentenciaSQL);
        while (resultSet.next()) {
            resultat = true;
            assertEquals(resultSet.getString("adreca"), novaAdreca);
        }
        assertTrue(resultat);
    }

    @Test
    @Order(30)
    public void testEliminarCorreuElectronic() throws SQLException {
        boolean resultat = false;
        correuElectronicDAO.eliminar(1L,"correu_electronic_club");
        String sentenciaSQL = "SELECT eliminat FROM correu_electronic_club";
        Statement statement = connexio.createStatement();
        ResultSet resultSet = statement.executeQuery(sentenciaSQL);
        while (resultSet.next()) {
            resultat = true;
            assertTrue(resultSet.getBoolean("eliminat"));
        }
        assertTrue(resultat);
    }

    @Test
    @Order(31)
    public void testRestaurarCorreuElectronic() throws SQLException {
        boolean resultat = false;
        correuElectronicDAO.restaurar(1L,"correu_electronic_club");
        String sentenciaSQL = "SELECT eliminat FROM correu_electronic_club";
        Statement statement = connexio.createStatement();
        ResultSet resultSet = statement.executeQuery(sentenciaSQL);
        while (resultSet.next()) {
            resultat = true;
            assertFalse(resultSet.getBoolean("eliminat"));
        }
        assertTrue(resultat);
    }

     @Test
     @Order(32)
     public void testObtenirCorreuElectronic() {
        CorreuElectronic correuElectronicObtingut = correuElectronicDAO.obtenir(1L, "correu_electronic_club");
        assertEquals(correuElectronic1, correuElectronicObtingut);
     }

    @Test
    @Order(33)
    public void testObtenirTotsElsCorreuElectronic() {
        correuElectronicDAO.emmagatzemar(correuElectronic2, "correu_electronic_club");
        List<CorreuElectronic> correuElectronics = correuElectronicDAO.obtenirTot("correu_electronic_club");
        assertEquals(2, correuElectronics.size());
    }

    @Test
    @Order(34)
    public void testObtenirTotsElsCorreuElectronicPerPropietari() {
        List<CorreuElectronic> correuElectronics = correuElectronicDAO.obtenirTotPerPropietari("correu_electronic_club",1L);
        assertEquals(2, correuElectronics.size());
        List<CorreuElectronic> noCorreuElectronics = correuElectronicDAO.obtenirTotPerPropietari("correu_electronic_club",10L);
        assertEquals(0, noCorreuElectronics.size());
    }

    @AfterAll
    public static void tancament() throws SQLException {
        connexio.close();
    }
}