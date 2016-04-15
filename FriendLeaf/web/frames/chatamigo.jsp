<%-- 
    Document   : chatamigo
    Created on : Nov 22, 2015, 11:10:28 AM
    Author     : andrew
--%>

<%@page import="datos.Chat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="lista_chats" class="datos.ListaChats" scope="session"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="../design/design_iframe.css">
        <title>FriendLeaf</title>
    </head>
    <body>
        <fieldset>
            <legend>Chat con <%=lista_chats.getAmigo()%></legend>
            <%
                java.util.ArrayList chats = lista_chats.getLista_chats();
                if (chats.isEmpty()) {
            %>
            <p>No hay mensajes</p>
            <%
            } else {
                for (int i = 0; i < chats.size(); i++) {
                    Chat chat_actual = (Chat) chats.get(i);
            %>
            <p class="left"><%=chat_actual.getUsuario()%></p>
            <p><%=chat_actual.getMessage()%><br></p>
            <p class="right fecha"><%=chat_actual.getDate()%></p>
            <%
                    }
                }
            %>
        </fieldset>
        <% String url = request.getContextPath();%>
        <p id="link"><%=url+ "/ObtenerDatosChat?amigo=" + lista_chats.getAmigo()%></p>
        <script type="text/javascript" src="../javascript/countdown_chat.js"></script>
        <script type="text/javascript" src="../javascript/diferencia_fechas.js"></script>
        <script type="text/javascript" src="../javascript/emoticonos-dentro.js"></script>
    </body>
</html>
