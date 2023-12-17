package main.java.club;

import main.java.utilitats.JDBC;

import java.sql.Connection;

/**
 * Taula club
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class ClubTaula {
    public static final String NOM_TAULA = "club";

    public static void iniciar(Connection connexio) {
        String campsAdreca = "id SERIAL PRIMARY KEY, " +
                "nom VARCHAR(50) NOT NULL, " +
                "pagina_web VARCHAR(100), " +
                "nif VARCHAR(15) NOT NULL, " +
                "escut VARCHAR(60) NOT NULL, " +
                "data_creacio DATE NOT NULL, " +
                "eliminat BOOLEAN NOT NULL DEFAULT FALSE";

        JDBC.iniciarTaula(connexio, NOM_TAULA, campsAdreca);
    }
}