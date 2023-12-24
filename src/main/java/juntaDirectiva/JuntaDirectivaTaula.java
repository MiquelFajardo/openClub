package main.java.juntaDirectiva;

import main.java.utilitats.JDBC;

import java.sql.Connection;

/**
 * Taula per la junta directiva
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class JuntaDirectivaTaula {
    public static final String NOM_TAULA = "junta_directiva";

    public static void iniciar(Connection connexio) {
        String campsAdreca = "id SERIAL PRIMARY KEY, " +
                "id_club BIGINT REFERENCES club(id), " +
                "id_membre_club BIGINT REFERENCES membre_club(id), " +
                "carrec VARCHAR(25) NOT NULL, " +
                "data_inici_carrec DATE NOT NULL, " +
                "data_final_carrec DATE, " +
                "actiu BOOLEAN NOT NULL DEFAULT TRUE, " +
                "eliminat BOOLEAN NOT NULL DEFAULT FALSE";

        JDBC.iniciarTaula(connexio, NOM_TAULA, campsAdreca);
    }
}