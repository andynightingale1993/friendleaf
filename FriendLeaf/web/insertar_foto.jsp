<%-- 
    Document   : insertar_foto
    Created on : Nov 15, 2015, 10:57:05 AM
    Author     : andrew
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="design/design.css">
        <link rel="icon" type="image/png" href="images/favicon.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FriendLeaf - <%=session.getAttribute("name")%> </title>
    </head>
    <body>
        <header>
            <img src="images/header.png" alt="header">
        </header>
        <nav>
            <a href="FeedServlet">Inicio</a>
            <a href="AmigosServlet">Amigos</a>
            <a href="FotosServlet" id="selected">Fotos</a>
            <a href="GustosServlet?page=gustos">Modificar Gustos</a>
            <a href="PerfilServlet">Perfil</a>
            <a href="ayuda.jsp">Ayuda</a>
            <a href="DestruirSesionServlet">Cerrar Sesi&oacute;n</a>
        </nav>
        <h1>Seleccione la foto que quiere insertar:</h1>
        <fieldset>
            <form name="insertar_foto" action="InsertarFotoServlet" method="post" enctype="multipart/form-data">
                Archivo: <input type="file" id="file" name="photo" required title="Debes subir un archivo" accept="image/*"><br>
                <input id="boton" type="submit" value="Agregar Foto">
            </form>
        </fieldset>
        <p class="rojo"><% if (session.getAttribute("error") != null) {%><%= session.getAttribute("error")%><% }%></p>
        <script type="text/javascript" src="javascript/submitevent.js"></script>
    </body>
</html>
