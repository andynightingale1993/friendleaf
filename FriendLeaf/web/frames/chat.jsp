<%-- 
    Document   : chat
    Created on : Nov 22, 2015, 10:46:57 AM
    Author     : andrew
--%>

<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="friends" class="datos.Amigos" scope="session"></jsp:useBean>
    <!DOCTYPE html>
    <html>
        <head>
            <link rel="stylesheet" type="text/css" href="../design/design_iframe.css">
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>FriendLeaf</title>
        </head>
        <body>
            <fieldset>
                <legend align="center">Chat</legend>
            <%
                ArrayList<String> listaamigos = friends.getAmigos();
                ArrayList<String> usuariosamigos = friends.getUsuariosamigos();
                for (int i = 0; i < listaamigos.size(); i++) {
            %>
            <a class="enlace" href="chat_contenedor.jsp?amigo=<%= usuariosamigos.get(i)%>"><%=listaamigos.get(i)%></a><hr>
            <%
                }
            %>
        </fieldset>
        <p class="rojo"><% if (session.getAttribute("error") != null) {%><%= session.getAttribute("error")%><% }%></p>
        <script type="text/javascript" src="../javascript/emoticonos-dentro.js"></script>
    </body>
</html>

