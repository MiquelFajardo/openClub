package test.utilitats;

import main.java.utilitats.Logs;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test classe Logs
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LogsTest {
    private final static String ARXIU_PROVA = "prova.log";

    @Test
    @Order(1)
    public void testExisteixArxiu() {
        Logs.escriure(ARXIU_PROVA, "PROVA");
        assertTrue(Logs.existeix("logs" + System.getProperty("file.separator") + ARXIU_PROVA));
    }

    @Test
    @Order(2)
    public void testEliminarArxiu() {
        Logs.eliminarArxiu("logs" + System.getProperty("file.separator") + ARXIU_PROVA);
        assertFalse(Logs.existeix("logs" + System.getProperty("file.separator") + ARXIU_PROVA));
    }
}