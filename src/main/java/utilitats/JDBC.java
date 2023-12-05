package main.java.utilitats;

import java.sql.*;

/**
 * Utilitats per treballar amb la base de dades de PostgreSQL
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class JDBC {
    private final static String ARXIU_LOG = "jdbc.log";

    /**
     * Connectar amb la base de dades
     * @return la connexió
     */
    public static Connection connectar() {
        Connection connexio = null;
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/openClub";
            String usuari = "openClubAdmin";
            String contrasenya = "openClubAdmin";
            connexio = DriverManager.getConnection(url, usuari, contrasenya);
        } catch (SQLException | ClassNotFoundException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut realitzar la connexió amb la base de dades" + "\n" + e);
        }
        return connexio;
    }


    /**
     * Crea una taula a la base de dades
     */
    public static void crearTaula(Connection connexio, String nom, String camps) {
        String sentenciaSQL = "CREATE TABLE IF NOT EXISTS " + nom  + " (" + camps + ")";
        try {
            Statement statement = connexio.createStatement();
            statement.executeUpdate(sentenciaSQL);
            statement.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut crear la taula " + nom + "\n" + e);
        }
    }

    /**
     * Elimina una taula passada per paràmetre
     */
    public static void eliminarTaula(Connection connexio, String nom) {
        String sentenciaSQL = "DROP TABLE IF EXISTS " + nom;
        Statement statement;
        try {
            statement = connexio.createStatement();
            statement.executeUpdate(sentenciaSQL);
            statement.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut eliminar la taula " + nom);
        }
    }

    /**
     * Comprova si existeix una taula a la base de dades
     * @return si la taula existeix
     */
    public static boolean existeixTaula(Connection connexio, String nom) {
        try {
            DatabaseMetaData dbm = connexio.getMetaData();
            ResultSet taula = dbm.getTables(null, null, nom, null);
            return taula.next();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut comprovar si existeix la taula " + nom);
        }
        return false;
    }

    /**
     * Inicia la taula passada per paràmetre
     */
    public static void iniciarTaula(Connection connexio, String taula, String camps) {
        System.out.print("TaulaBBDD " + taula + " .... ");
        if(!JDBC.existeixTaula(connexio, taula)) {
            JDBC.crearTaula(connexio, taula, camps);
            System.out.println("creada");
        } else {
            System.out.println("ok");
            Logs.escriure(ARXIU_LOG, "No s'ha pogut crear la taula " + taula);
        }
    }
}