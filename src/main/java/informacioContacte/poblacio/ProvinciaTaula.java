package main.java.informacioContacte.poblacio;

import main.java.utilitats.JDBC;

import java.sql.Connection;

/**
 * Taula Provincia
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class ProvinciaTaula {
    public static final String NOM_TAULA = "provincia";

    public static void iniciar(Connection connexio) {
        String campsAdreca = "id SERIAL PRIMARY KEY, " +
                             "nom VARCHAR(50) NOT NULL, " +
                             "id_pais BIGINT NOT NULL, " +
                             "CONSTRAINT fk_id_pais FOREIGN KEY (id_pais) REFERENCES pais(id)";

        JDBC.iniciarTaula(connexio, NOM_TAULA, campsAdreca);
    }
}