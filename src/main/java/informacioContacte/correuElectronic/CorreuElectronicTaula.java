package main.java.informacioContacte.correuElectronic;

import main.java.utilitats.JDBC;

import java.sql.Connection;

/**
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class CorreuElectronicTaula {
    public static void iniciar(Connection connexio, String nomTaula, String referenciat) {
        String campsAdreca = "id SERIAL PRIMARY KEY, " +
                "tipus VARCHAR(10) NOT NULL, " +
                "adreca VARCHAR(50) NOT NULL, " +
                "id_propietari BIGINT REFERENCES " + referenciat + "(id), " +
                "eliminat BOOLEAN NOT NULL DEFAULT FALSE";

        JDBC.iniciarTaula(connexio, nomTaula, campsAdreca);
    }
}
