package main.java.areaEsportiva.equipTecnic;

import main.java.utilitats.JDBC;

import java.sql.Connection;

/**
 * Taula EquipTecnic
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class EquipTecnicTaula {
    public static final String NOM_TAULA = "equip_tecnic";

    public static void iniciar(Connection connexio) {
        String campsAdreca = "id SERIAL PRIMARY KEY, " +
                "id_membre_club BIGINT REFERENCES membre_club(id), " +
                "id_equip BIGINT REFERENCES equip(id), " +
                "carrec VARCHAR(30) NOT NULL, " +
                "actiu BOOLEAN NOT NULL DEFAULT TRUE, " +
                "eliminat BOOLEAN NOT NULL DEFAULT FALSE";

        JDBC.iniciarTaula(connexio, NOM_TAULA, campsAdreca);
    }
}
