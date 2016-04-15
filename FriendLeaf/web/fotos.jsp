<%-- 
    Document   : fotos
    Created on : 04-nov-2015, 9:08:01
    Author     : andrew

    Página donde se visualizarán fotos, y se podrán añadir,
    modificar o eliminar.
--%>
<% int l = 0; %>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="object" class="datos.Fotos" scope="session"></jsp:useBean>
    <!DOCTYPE html>
    <html>
        <head>
            <link rel="stylesheet" type="text/css" href="design/design_small.css" media="all and (min-width: 240px) and (max-width: 640px)">
            <link rel="stylesheet" type="text/css" href="design/design.css" media="all and (min-width: 640px)">
            <link rel="icon" type="image/png" href="images/favicon.png">
            <script tye="text/javascript" src="javascript/image-window.js"></script>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>FriendLeaf - Fotos</title>
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
                <a href="AmigosServlet">Amigos</a>
                <a href="FotosServlet" id="selected">Fotos</a>
                <a href="GustosServlet?page=gustos">Modificar Gustos</a>
                <a href="PerfilServlet">Perfil</a>
                <a href="ayuda.jsp">Ayuda</a>
                <a href="DestruirSesionServlet">Cerrar Sesi&oacute;n</a>
            </nav>
            <div id="cuerpo">
                <article>
                    <h1>Fotos</h1>
                <%
                    ArrayList<String> fotos = object.getFotos();
                    for (int i = 0; i < fotos.size(); i++) {
                        //Tenemos que obtener el nombre absoluto de la foto,
                        //sin la ruta.
                        /*String ruta_foto = fotos.get(i);
                        String foto_invertida = "";
                        boolean terminado = false;
                        for (int j = ruta_foto.length() - 1; j >= 0 && !terminado; j--) {
                            //Si hemos llegado a una / hemos terminado.
                            if (ruta_foto.charAt(j) == '/') {
                                terminado = true;
                                //En otro caso vamos concatenando caracteres.
                            } else {
                                foto_invertida += ruta_foto.charAt(j);
                            }

                        }
                        //Le damos la vuelta...
                        String foto = "";
                        for (int k = foto_invertida.length() - 1; k >= 0; k--) {
                            foto += foto_invertida.charAt(k);
                        }*/
                %>
                <div class="contenedor-imagen">
                    <img onclick="swipe(<%=i%>)" class="imagen" src="<%=fotos.get(i)%>" alt="<%=fotos.get(i)%>" width="320" height="320"><br>
                    <a href="ModificarFotoPerfilServlet?photo=<%=fotos.get(i)%>">Poner de Perfil</a>
                    <a href="EliminarFotoServlet?photo=<%=fotos.get(i)%>">Eliminar</a>
                </div>
                <%
                        l++;
                    }
                %>
                <hr>
                <a href="insertar_foto.jsp">Insertar Foto</a>
            </article>
            <aside>
                <iframe src="ChatServlet"></iframe>
            </aside>
        </div>
        <p class="rojo"><% if (session.getAttribute("error") != null) {%><%= session.getAttribute("error")%><% }%></p>
    </body>
</html>
