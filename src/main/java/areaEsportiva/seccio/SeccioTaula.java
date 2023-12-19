package main.java.areaEsportiva.seccio;

import main.java.utilitats.JDBC;

import java.sql.Connection;

/**
 * Taula secció
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class SeccioTaula {
    public static final String NOM_TAULA = "seccio";

    public static void iniciar(Connection connexio) {
        String campsAdreca = "id SERIAL PRIMARY KEY, " +
                "nom VARCHAR(50) NOT NULL, " +
                "id_club BIGINT REFERENCES club(id), " +
                "eliminat BOOLEAN NOT NULL DEFAULT FALSE";

        JDBC.iniciarTaula(connexio, NOM_TAULA, campsAdreca);
    }
}
