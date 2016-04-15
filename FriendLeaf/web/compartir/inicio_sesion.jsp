<%-- 
    Document   : inicio_sesion
    Created on : Dec 13, 2015, 9:16:38 AM
    Author     : andrew
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="../design/design.css">
        <title>Friend Leaf - Autentificarse</title>
    </head>
    <body>
        <h1>Necesita autentificarse para compartir su contenido.</h1>
        <fieldset>
            <legend>Datos de Autentificaci&oacute;n</legend>
            <form action="../CompartirServlet" name="autentificar_compartir" method="post">
                <input type="hidden" name="compartir" value="true">
                <input type="hidden" name="funcion" value="autentificarse">
                <input type="hidden" name="photo" value="<%=request.getParameter("photo")%>">
                <input type="hidden" name="video" value="<%=request.getParameter("video")%>">
                <label for="user"> Usuario: </label><input class="cajatexto" type="text" id="user" name="user" placeholder="..." required title="Introduce un Usuario"><br>
                <label for="password"> Contrase&ntilde;a: </label><input class="cajatexto" id="password" type="password" name="password" placeholder="..." required title="Introduce la Contraseña"><br>
                <input id="boton" type="submit" value="Iniciar Sesión"><br>
            </form>
        </fieldset>
        <p class="rojo"><% if (request.getParameter("error") != null) {%><%= request.getParameter("error")%><% }%></p>
    </body>
</html>
