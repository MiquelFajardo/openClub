package main.java.areaEsportiva.temporada;

import main.java.utilitats.JDBC;

import java.sql.Connection;

/**
 * Taula temporada
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class TemporadaTaula {
    public static final String NOM_TAULA = "temporada";

    public static void iniciar(Connection connexio) {
        String campsAdreca = "id SERIAL PRIMARY KEY, " +
                "any_temporada VARCHAR(20) NOT NULL, " +
                "id_seccio BIGINT REFERENCES club(id), " +
                "eliminat BOOLEAN NOT NULL DEFAULT FALSE";

        JDBC.iniciarTaula(connexio, NOM_TAULA, campsAdreca);
    }
}
