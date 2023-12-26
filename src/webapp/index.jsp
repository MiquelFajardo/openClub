<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charsetUTF-8">
        <link rel="stylesheet" href="/src/webapp/estils/estil.css">
        <title>openClub - autenticació</title>
    </head>
    <body id="body-autenticacio">
        <div id="contenidor-autenticacio">
            <h1>openClub</h1>
            <form action ="autenticacio" method="POST">
                <div class="form-usuari">
                    <p>Usuari</p>
                    <input type="text" name="nom_usuari" required autocomplete="off" autofocus placeholder="Introdueix el nif ..."/>
                </div>
                <div class="form-contrasenya">
                    <p>Contrasenya</p>
                    <input type="password" name="contrasenya" required autocomplete="off"/>
                </div>
                <div id="form-boto">
                    <input type="submit" value="Autenticar"/>
                </div>
            </form>
            <div id="form-subtitol">
                <p>Creat per @Miquel A Fajardo</p>
            </div>
        </div>
    </body>
</html>