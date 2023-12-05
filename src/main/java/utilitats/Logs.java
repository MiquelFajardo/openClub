package main.java.utilitats;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utilitats per guardar els logs de l'activitat de l'aplicació
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */
public class Logs {
    private final static String DIRECTORI_LOG = "logs";
    private final static String ARXIU_LOG = "logs.log";

    /**
     * Escriu el log en l'arxiu passat per paràmetre
     */
    public static void escriure(String nomArxiu, String textLog) {
        String arxiu = DIRECTORI_LOG + System.getProperty("file.separator") + nomArxiu;
        Date ara = new Date();
        SimpleDateFormat formatData = new SimpleDateFormat("dd/MM/yyy HH:mm");
        String dataActual = formatData.format(ara);

        try {
            if(!existeix(DIRECTORI_LOG)) crearDirectori(DIRECTORI_LOG);
            if(!existeix(arxiu)) crearArxiu(arxiu);
            FileWriter fileWriter = new FileWriter(arxiu, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(dataActual + " - " + textLog);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            escriure(ARXIU_LOG, "No s'ha pogut guardar el log en l'arxiu " + arxiu + "\n" + e);
        }
    }

    /**
     * Comprova si existeix un arxiu o directori
     * @return si existeix l'arxiu o directori
     */
    public static boolean existeix(String arxiu) {
        File file = new File(arxiu);
        return file.exists();
    }

    /**
     * Crea un arxiu passat per paràmetre
     */
    public static void crearArxiu(String arxiu) {
        File file = new File(arxiu);
        try {
            if(!file.createNewFile()) escriure(ARXIU_LOG, "No s'ha pogut crear l'arxiu " + arxiu);
        } catch (IOException e) {
            escriure(ARXIU_LOG, "No s'ha pogut crear l'arxiu " + arxiu + "\n" + e);
        }
    }

    /**
     * Crea un directori passat per paràmetre
     */
    public static void crearDirectori(String directori) {
        File file = new File(directori);
        if(!file.mkdir())  escriure(ARXIU_LOG, "No s'ha pogut crear el directori " + directori);
    }

    /**
     * Elimina un arxiu passat per paràmetre
     */
    public static void eliminarArxiu(String arxiu) {
        File file = new File(arxiu);
        if(!file.delete())  escriure(ARXIU_LOG, "No s'ha pogut eliminar l'arxiu " + arxiu);
    }
}
