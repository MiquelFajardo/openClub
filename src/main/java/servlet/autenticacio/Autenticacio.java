package main.java.servlet.autenticacio;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.java.utilitats.JDBC;
import main.java.utilitats.Logs;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


/**
 * Servlet per autenticació usuaris
 *
 * @author Miquel A Fajardo <miquel.fajardo@protonmail.com>
 */

@WebServlet(name="autenticacio", urlPatterns = {"/autenticacio"})
public class Autenticacio extends HttpServlet {
    final static String ARXIU_LOG = "autenticacio.log";
    private Connection connexio;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
    }
    @Override
    public void init() throws ServletException {
        connexio = JDBC.connectar();
    }

    @Override
    public void destroy() {
        try {
            connexio.close();
        } catch (SQLException e) {
            Logs.escriure(ARXIU_LOG, "No s'ha pogut tancar connexió\n" + e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nomUsuari = req.getParameter("nom_usuari");
        String contrasenya = req.getParameter("contrasenya");
        // Autenticacio
    }
}
