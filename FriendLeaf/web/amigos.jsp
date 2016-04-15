<%-- 
    Document   : amigos
    Created on : 04-nov-2015, 9:08:18
    Author     : andrew

    Página que visualiza los Amigos que tiene un usuario y opciones
    para añadir/elimintar amigos.
--%>

<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="object" class="datos.Amigos" scope="session"></jsp:useBean>
    <!DOCTYPE html>
    <html>
        <head>
            <link rel="stylesheet" type="text/css" href="design/design_small.css" media="all and (min-width: 240px) and (max-width: 640px)">
            <link rel="stylesheet" type="text/css" href="design/design.css" media="all and (min-width: 640px)">
            <link rel="icon" type="image/png" href="images/favicon.png">
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>FriendLeaf - Amigos</title>
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
            <div id="cuerpo">
                <article>
                    <fieldset>
                        <legend align="center">Amigos</legend>
                    <%
                        ArrayList<String> listaamigos = object.getAmigos();
                        ArrayList<String> usuariosamigos = object.getUsuariosamigos();
                        ArrayList<String> peticionesamigos = object.getPeticionesamigos();
                        for (int i = 0; i < listaamigos.size(); i++) {
                    %>
                    <%= listaamigos.get(i)%>: <a href="ObtenerDatosAmigo?amigo=<%= usuariosamigos.get(i)%>">Ver Amigo</a>
                    <a href="EliminarAmigo?amigo=<%= usuariosamigos.get(i)%>" class="confirmar">Eliminar Amigo</a><hr>
                    <%
                        }
                        if (!peticionesamigos.isEmpty()) {
                    %>
                    <fieldset>
                        <legend>Peticiones de Amistad</legend>
                        <%
                            for (int i = 0; i < peticionesamigos.size(); i++) {
                        %>
                        <%= peticionesamigos.get(i)%>: <a href="AceptarEliminarPeticionAmistad?funcion=aceptar&amigo=<%= peticionesamigos.get(i)%>">Aceptar Amistad</a>
                        <a href="AceptarEliminarPeticionAmistad?funcion=eliminar&amigo=<%= peticionesamigos.get(i)%>" class="confirmar">Rechazar Amistad</a><hr>
                        <%
                            }
                        %>
                    </fieldset>
                    <%
                        }
                    %>
                </fieldset>
                <form action="AgregarAmigo" method="post">
                    <fieldset>
                        <legend align="center">A&ntilde;adir Amigo</legend>
                        Usuario Amigo: <input class="cajatexto" type="text" name="amigo" placeholder="Nick Amigo" required title="Introduce un amigo"><br>
                        <input id="boton" type="submit" value="Añadir Amigo">
                    </fieldset>
                </form>
                <p class="rojo"><% if (session.getAttribute("error") != null) {%><%= session.getAttribute("error")%><% }%></p>
            </article>
            <aside>
                <iframe src="ChatServlet"></iframe>
            </aside>
        </div>
        
        <script type="text/javascript" src="javascript/confirmar_eliminar_amigo.js"></script>
        <script type="text/javascript" src="javascript/submitevent.js"></script>
    </body>
</html>
