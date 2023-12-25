package main.java.informacioContacte.poblacio;

import main.java.utilitats.JDBC;

import java.sql.Connection;

/**
 * Taula Pais
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
*/
public class PaisTaula {
    public static final String NOM_TAULA = "pais";

    public static void iniciar(Connection connexio) {
        String campsAdreca = "id SERIAL PRIMARY KEY, " +
                "nom VARCHAR(50) NOT NULL";

        JDBC.iniciarTaula(connexio, NOM_TAULA, campsAdreca);
    }
}