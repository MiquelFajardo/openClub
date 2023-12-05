package test.utilitats;

import main.java.utilitats.JDBC;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test classe JDBC
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JDBCTest {
    private final String NOM = "prova";
    private final String CAMPS = "id INTEGER NOT NULL, nom VARCHAR(20)";

    Connection connexio = null;


    @Test
    @Order(1)
    public void testConnectar() {
        connexio = JDBC.connectar();
        assertNotNull(connexio);
    }

    @Test
    @Order(2)
    public void testCrearTaula() {
        connexio = JDBC.connectar();
        JDBC.crearTaula(connexio, NOM, CAMPS);
        assertTrue(JDBC.existeixTaula(connexio, NOM));
    }

    @Test
    @Order(3)
    public void testEliminarTaula() {
        connexio = JDBC.connectar();
        JDBC.eliminarTaula(connexio, NOM);
        assertFalse(JDBC.existeixTaula(connexio, NOM));
    }
}