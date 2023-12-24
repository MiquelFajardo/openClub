package main.java.membreClub;

import main.java.utilitats.JDBC;

import java.sql.Connection;

/**
 * Taula persona contacte
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class PersonaContacteTaula {
    public static final String NOM_TAULA = "persona_contacte_membre_club";

    public static void iniciar(Connection connexio) {
        String campsAdreca = "id SERIAL PRIMARY KEY, " +
                "id_membre BIGINT REFERENCES membre_club(id), " +
                "nom VARCHAR(30) NOT NULL, " +
                "cognoms VARCHAR(60) NOT NULL, " +
                "parentesc VARCHAR(20) NOT NULL, " +
                "telefon VARCHAR(20) NOT NULL, " +
                "correu_electronic VARCHAR(50), " +
                "eliminat BOOLEAN NOT NULL DEFAULT FALSE";

        JDBC.iniciarTaula(connexio, NOM_TAULA, campsAdreca);
    }

}