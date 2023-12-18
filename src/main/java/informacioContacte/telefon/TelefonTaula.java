package main.java.informacioContacte.telefon;

import main.java.utilitats.JDBC;

import java.sql.Connection;

/**
 * Taula tele`fon
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class TelefonTaula {
    public static void iniciar(Connection connexio, String nomTaula, String referenciat) {
        String campsAdreca = "id SERIAL PRIMARY KEY, " +
                "tipus VARCHAR(10) NOT NULL, " +
                "prefix_pais VARCHAR(10) NOT NULL, " +
                "numero VARCHAR(15) NOT NULL, " +
                "id_propietari BIGINT REFERENCES " + referenciat + "(id), " +
                "eliminat BOOLEAN NOT NULL DEFAULT FALSE";

        JDBC.iniciarTaula(connexio, nomTaula, campsAdreca);
    }
}
