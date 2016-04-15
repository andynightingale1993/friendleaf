<%-- 
    Document   : modificar_feed
    Created on : 16-nov-2015, 12:32:24
    Author     : andrew
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="feed" class="datos.Feed" scope="session"></jsp:useBean>
    <!DOCTYPE html>
    <html>
        <head>
            <link rel="stylesheet" type="text/css" href="design/design.css">
            <link rel="icon" type="image/png" href="images/favicon.png">
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>FriendLeaf - Modificar Feed</title>
            <style>
                body {
                    cursor: url('images/quill.png'), auto;
                }
            </style>
        </head>
        <body>
            <header>
                <img src="images/header.png" alt="header">
            </header>
            <nav>
                <a href="FeedServlet" id="selected">Inicio</a>
                <a href="AmigosServlet">Amigos</a>
                <a href="FotosServlet">Fotos</a>
                <a href="GustosServlet?page=gustos">Modificar Gustos</a>
                <a href="PerfilServlet">Perfil</a>
                <a href="ayuda.jsp">Ayuda</a>
                <a href="DestruirSesionServlet">Cerrar Sesi&oacute;n</a>
            </nav>
            <h1>Modificar Feed:</h1>
            <fieldset>
                <legend align="center"><%=feed.getUser() + " - " + feed.getDate()%></legend>
            <form action="InsertarModificarEliminarPost" method="post" name="modificarpost" enctype="multipart/form-data">
                <input type="hidden" name="funcion" value="modificacion">
                <input type="hidden" name="id" value="<%=feed.getIdpost()%>">

                <label for="content">Contenido: <span class="rojo">*</span></label><br><textarea class="cajatexto" name="content" id="content" required title="Debes insertar un contenido" rows="5" cols="60"><%=feed.getContent()%></textarea><br>
                <label for="photo">Image: </label><input type="file" id="photo" name="photo" accept="image/*"><br>
                <label for="video">V&iacute;deo: </label><input type="file" id="video" name="video" accept="video/*"><br>
                <input id="boton" type="submit" value="Modificar Post">
            </form>
        </fieldset>
        <script type="text/javascript" src="javascript/submitevent.js"></script>
    </body>
</html>
