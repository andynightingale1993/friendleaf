<%-- 
    Document   : perfil
    Created on : 04-nov-2015, 9:08:06
    Author     : andrew

    Página que nos muestra el perfil y en la que se podrán
    modificar los datos.
--%>
<% int k = 1; %>
<%@page import="datos.Feed"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="friend" class="datos.Perfil" scope="session"/>
<jsp:useBean id="object" class="datos.Fotos" scope="session"></jsp:useBean>
<jsp:useBean id="lista_feeds" class="java.util.ArrayList" scope="session"></jsp:useBean>
    <!DOCTYPE html>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <link rel="stylesheet" type="text/css" href="design/design.css">
            <link rel="icon" type="image/png" href="images/favicon.png">
            <script type="text/javascript" src="javascript/provinces.js"></script>
            <title>FriendLeaf - <%= friend.getUser()%></title>
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
            <a href="FeedServlet">Inicio</a>
            <a href="AmigosServlet" id="selected">Amigos</a>
            <a href="FotosServlet">Fotos</a>
            <a href="GustosServlet?page=gustos">Modificar Gustos</a>
            <a href="PerfilServlet">Perfil</a>
            <a href="ayuda.jsp">Ayuda</a>
            <a href="DestruirSesionServlet">Cerrar Sesi&oacute;n</a>
        </nav>
        <h1>Perfil de <%= friend.getName() + " " + friend.getSurname()%></h1>
        <fieldset>
            <legend align="center">Foto de Perfil</legend>
            <img onclick="swipe(0)" class="imagen" src="<%=friend.getPhoto()%>" alt="Foto de <%=friend.getUser()%>" width="320px" height="320px"><br>
        </fieldset>
        <fieldset>
            <legend align="center">Datos Personales</legend>
            <p>Usuario: <%= friend.getUser()%></p>
            <p>Nombre: <%= friend.getName()%></p>
            <p>Apellidos: <%= friend.getSurname()%></p>
            <p>DNI/NIE/Pasaporte: <%= friend.getDni()%></p>
            <p>Direcci&oacute;n: <%= friend.getAddress()%></p>
            <p>Correo Electr&oacute;nico: <%= friend.getEmail()%></p>
            <p>Tel&eacute;fono: <%= friend.getTelefono()%></p>
            <p>P&aacute;gina Web: <%= friend.getWebpage()%></p>
            <p>Pa&iacute;s: <%= friend.getCountry()%></p>
            <a href="EliminarAmigo?amigo=<%= friend.getUser()%>" class="confirmar">Eliminar Amigo</a><br>
        </fieldset>
        <fieldset>
            <legend align="center">Fotos</legend>
            <%
                ArrayList<String> fotos = object.getFotos();
                for (int i = 0; i < fotos.size(); i++) {
            %>
            <img onclick="swipe(<%=k%>)" class="imagen" src="<%=fotos.get(i)%>" alt="<%=fotos.get(i)%>" width="320px" height="320px">
            <%
                k++;
                }
            %>
        </fieldset>
        <fieldset id="posts">
            <legend align="center">Posts</legend>

            <%
                if (lista_feeds.isEmpty()) {
            %>
            <p>No hay Posts.</p>
            <%
            } else {
                for (int i = 0; i < lista_feeds.size(); i++) {
                    Feed feed = (Feed) lista_feeds.get(i);
            %>
            <fieldset>
                <legend align="center" class="fecha"><%=feed.getDate()%></legend>
                <p><%=feed.getContent()%></p>
                <%
                    if (!feed.getPhoto().equals("")) {
                %>
                <img onclick="swipe(<%=k%>)" class="imagen" src="<%=feed.getPhoto()%>" alt="<%=feed.getPhoto()%>"><br>
                <%
                    k++;
                    }
                %>
                <%
                    if (!feed.getVideo().equals("")) {
                %>
                <video width="320" height="240" controls>
                    <source src="<%=feed.getVideo()%>">
                    Tu navegador no soporta el formato del vídeo.
                </video>
                <br>
                <%
                    }
                %>
            </fieldset>
            <%
                    }
                }
            %>
        </fieldset>
        <a href="AmigosServlet">Volver</a>
        <p class="rojo"><% if (session.getAttribute("error") != null) {%><%= session.getAttribute("error")%><% }%></p>
        <script type="text/javascript" src="javascript/confirmar_eliminar_amigo.js"></script>
        <script type="text/javascript" src="javascript/submitevent.js"></script>
        <script type="text/javascript" src="javascript/image-window.js"></script>
        <script type="text/javascript" src="javascript/diferencia_fechas.js"></script>
        <script type="text/javascript" src="javascript/emoticonos-perfil.js"></script>
    </body>
</html>
