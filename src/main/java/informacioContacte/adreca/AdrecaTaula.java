package main.java.informacioContacte.adreca;

import main.java.utilitats.JDBC;

import java.sql.Connection;

/**
 * Taula adreça
 * S'ha de pasar per paràmetre el nom de la taula i qui és el propietari
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class AdrecaTaula {
    public static void iniciar(Connection connexio, String nomTaula, String referenciat) {
        String campsAdreca = "id SERIAL PRIMARY KEY, " +
                             "tipus VARCHAR(10) NOT NULL, " +
                             "nom_carrer VARCHAR(50) NOT NULL, " +
                             "numero VARCHAR(10) NOT NULL, " +
                             "pis VARCHAR(10), " +
                             "id_propietari BIGINT REFERENCES " + referenciat + "(id), " +
                             "id_poblacio BIGINT REFERENCES poblacio(id), " +
                             "eliminat BOOLEAN NOT NULL DEFAULT FALSE";

        JDBC.iniciarTaula(connexio, nomTaula, campsAdreca);
    }
}
