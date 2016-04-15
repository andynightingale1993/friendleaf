<%-- 
    Document   : chat_contenedor
    Created on : Nov 22, 2015, 2:12:14 PM
    Author     : andrew
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="../design/design_iframe.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FriendLeaf</title>
    </head>
    <body>
        <iframe height="600" src="../ObtenerDatosChat?amigo=<%=request.getParameter("amigo")%>"></iframe>
        <a id="botonreset" href="../ChatServlet">Volver</a><hr>
        <form action="../InsertarMensaje" method="post" name="insertar_chat">
            <input type="hidden" name="friend" value="<%=request.getParameter("amigo")%>">
            <label for="message">Mensaje: </label><textarea id="message" name="message" required title="Debes introducir un mensaje"></textarea><br>
            <input id="boton" type="submit" value="Enviar Mensaje">
            <script type="text/javascript" src="../javascript/submitevent.js"></script>
            <script type="text/javascript" src="../javascript/emoticonos-dentro.js"></script>
        </form>
    </body>
</html>
