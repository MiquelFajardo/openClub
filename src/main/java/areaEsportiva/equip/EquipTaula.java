package main.java.areaEsportiva.equip;

import main.java.utilitats.JDBC;

import java.sql.Connection;

/**
 * Taula Equip
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class EquipTaula {
    public static final String NOM_TAULA = "equip";

    public static void iniciar(Connection connexio) {
        String campsAdreca = "id SERIAL PRIMARY KEY, " +
                "id_temporada BIGINT REFERENCES temporada(id), " +
                "nom VARCHAR(30) NOT NULL, " +
                "categoria VARCHAR(30) NOT NULL, " +
                "grup VARCHAR(30) NOT NULL, " +
                "eliminat BOOLEAN NOT NULL DEFAULT FALSE";

        JDBC.iniciarTaula(connexio, NOM_TAULA, campsAdreca);
    }
}
