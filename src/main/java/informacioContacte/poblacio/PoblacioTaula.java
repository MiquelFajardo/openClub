package main.java.informacioContacte.poblacio;

import main.java.utilitats.JDBC;

import java.sql.Connection;

/**
 * Taula població
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class PoblacioTaula {
    public static final String NOM_TAULA = "poblacio";

    public static void iniciar(Connection connexio) {
        String campsAdreca = "id SERIAL PRIMARY KEY, " +
                "codi_postal VARCHAR(10) NOT NULL, " +
                "nom VARCHAR(50) NOT NULL, " +
                "id_provincia BIGINT REFERENCES provincia(id)";

        JDBC.iniciarTaula(connexio, NOM_TAULA, campsAdreca);
    }
}
