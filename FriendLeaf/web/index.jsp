<%-- 
    Document   : index
    Created on : 04-nov-2015, 9:06:47
    Author     : andrew

    Página principal, que sólo será accesible si no tenemos
    sesión iniciada.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="design/design.css">
        <link rel="icon" type="image/png" href="images/favicon.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FriendLeaf - Inicio</title>
        <style>
            body {
                cursor: url('images/pencil.png'), auto;
            }
        </style>
    </head>
    <body>
        <header>
            <img src="images/header.png" alt="header">
        </header>
        <nav>
            <a href="index.jsp" id="selected">Inicio</a>
            <a href="crearcuenta.jsp">Crear Cuenta</a>
        </nav>
        <h1>Index.jsp</h1>
        <form name="iniciarsesion" action="ControladorServlet" method="post">
            <input type="hidden" name="page" value="index.jsp">
            <label for="user"> Usuario: </label><input class="cajatexto" type="text" id="user" name="name" placeholder="..." required title="Introduce un Usuario"><br>
            <label for="password"> Contrase&ntilde;a: </label><input class="cajatexto" id="password" type="password" name="password" placeholder="..." required title="Introduce la Contraseña"><br>
            <input id="boton" type="submit" value="Iniciar Sesión"><br>
        </form> 
        <p class="rojo"><% if (session.getAttribute("error") != null) {%><%= session.getAttribute("error")%><% }%></p>
        <p class="rojo"><% if (request.getAttribute("error") != null) {%><%= request.getAttribute("error")%><% }%></p>
        <p class="correcto"><% if (request.getAttribute("mensaje") != null) {%><%= request.getAttribute("mensaje")%><% }%></p>
    <script type="text/javascript" src="javascript/submitevent.js"></script>
    </body>
</html>
