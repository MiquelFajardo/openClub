package main.java.areaEsportiva.jugador;

import main.java.utilitats.JDBC;

import java.sql.Connection;

/**
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class JugadorTaula {
    public static final String NOM_TAULA = "jugador";

    public static void iniciar(Connection connexio) {
        String campsAdreca = "id SERIAL PRIMARY KEY, " +
                "id_membre_club BIGINT REFERENCES membre_club(id), " +
                "id_equip BIGINT REFERENCES equip(id), " +
                "dorsal INT NOT NULL, " +
                "actiu BOOLEAN NOT NULL DEFAULT TRUE, " +
                "eliminat BOOLEAN NOT NULL DEFAULT FALSE";

        JDBC.iniciarTaula(connexio, NOM_TAULA, campsAdreca);
    }
}
