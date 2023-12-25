package main.java.membreClub;

import main.java.utilitats.JDBC;
import java.sql.Connection;

/**
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */

public class MembreClubTaula {
    public static final String NOM_TAULA = "membre_club";

    public static void iniciar(Connection connexio) {
        String campsAdreca = "id SERIAL PRIMARY KEY, " +
                "nom_usuari VARCHAR(20) NOT NULL, " +
                "contrasenya VARCHAR(20) NOT NULL, " +
                "actiu BOOLEAN NOT NULL DEFAULT TRUE, " +
                "nom VARCHAR(30) NOT NULL, " +
                "cognom_1 VARCHAR(30) NOT NULL," +
                "cognom_2 VARCHAR(30), " +
                "nif VARCHAR(15), " +
                "data_naixement DATE NOT NULL, " +
                "foto VARCHAR(100) NOT NULL, " +
                "eliminat BOOLEAN NOT NULL DEFAULT FALSE";

        JDBC.iniciarTaula(connexio, NOM_TAULA, campsAdreca);
    }
}
