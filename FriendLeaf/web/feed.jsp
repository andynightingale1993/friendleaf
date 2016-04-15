<%-- 
    Document   : feed
    Created on : 04-nov-2015, 9:07:43
    Author     : andrew

    Página de "feed", donde se visualizarán los mensajes recientes
    tanto tuyos como los de tus amigos.
--%>
<% int k = 0;%>
<%@page import="java.util.ArrayList"%>
<%@page import="datos.Feed"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="lista_feeds" class="java.util.ArrayList" scope="session"></jsp:useBean>
<jsp:useBean id="photos" class="datos.Fotos" scope="session"></jsp:useBean>
    <!DOCTYPE html>
    <html>
        <head>
            <link rel="stylesheet" type="text/css" href="design/design_feed_small.css" media="all and (min-width: 240px) and (max-width: 640px)">
            <link rel="stylesheet" type="text/css" href="design/design_feed.css" media="all and (min-width: 640px)">
            <link rel="icon" type="image/png" href="images/favicon.png">
            <script tye="text/javascript" src="javascript/image-window.js"></script>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>FriendLeaf - P&aacute;gina Principal</title>
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
            <div id="cuerpo">
                <aside>Quiz&aacute;s te interesen...<br><iframe src="GustosServlet"></iframe></aside>
                <article>
                    <h1>Feed de <%= session.getAttribute("name")%></h1>
                <%
                    if (lista_feeds.isEmpty()) {
                %>
                <p>No hay feeds</p>
                <%
                } else {
                    for (int i = 0; i < lista_feeds.size(); i++) {
                        Feed feed = (Feed) lista_feeds.get(i);
                %>
                <fieldset>
                    <legend align="center">
                        <%
                            if (!feed.getUser().equals(session.getAttribute("name"))) {
                        %><a class="enlace" href="ObtenerDatosAmigo?amigo=<%=feed.getUser()%>"><%=feed.getUser()%></a> - <span class="fecha"><%=feed.getDate()%></span>
                        <%
                        } else {
                        %>
                        <a class="enlace" href="PerfilServlet"><%=feed.getUser()%></a> - <span class="fecha"><%=feed.getDate()%></span>
                        <%
                            }
                        %>
                    </legend>
                    <p><%=feed.getContent()%></p>
                    <%
                        if (!feed.getPhoto().equals("")) {

                    %>
                    <img onclick="swipe(<%=k%>)" class="imagen" src="<%=feed.getPhoto()%>" alt="<%=feed.getPhoto()%>" width="320px" height="320px"><br>
                    <%
                            k++;
                        }
                    %>
                    <%
                        if (!feed.getVideo().equals("")) {
                            if (feed.getVideo().startsWith("videos")) {
                    %>
                    <video width="320" height="240" controls>
                        <source src="<%=feed.getVideo()%>">
                        Tu navegador no soporta el formato del vídeo.
                    </video>
                    <br>
                    <% } else {
                    %>
                    <iframe width="420" height="315" src="<%=feed.getVideo()%>" frameborder="0"></iframe><br>
                        <%
                                }

                            } //Obtenemos los usuarios a los que les gusta esto.
                            ArrayList<String> likes = feed.getLikes();
                            for (int j = 0; j < likes.size(); j++) {
                                if (!likes.get(j).equals((String) session.getAttribute("name"))) {
                        %>
                    <p>A <a class="enlace" href="ObtenerDatosAmigo?amigo=<%=likes.get(j)%>"><%=likes.get(j)%></a> le gusta esto</p>
                    <%
                    } else {
                    %>
                    <p>A <a class="enlace" href="PerfilServlet">tí</a> te gusta esto</p>
                    <%
                            }
                        }
                        //Buscamos si estoy yo en ese array.
                        String user = (String) session.getAttribute("name");
                        boolean encontrado = false;
                        for (int j = 0; j < likes.size() && !encontrado; j++) {
                            if (likes.get(j).equals(user)) {
                                encontrado = true;
                            }
                        }
                        if (encontrado) {
                    %>
                    <a href="AgregarEliminarLike?like=true&idpost=<%=feed.getIdpost()%>">Ya no me gusta</a><hr>
                    <%
                    } else {
                    %>
                    <a href="AgregarEliminarLike?like=false&idpost=<%=feed.getIdpost()%>">Me gusta</a><hr>
                    <%
                        }
                        if (feed.getUser().equals(session.getAttribute("name"))) {
                    %>
                    <a href="InsertarModificarEliminarPost?funcion=modificar&id=<%=feed.getIdpost()%>">Modificar</a><hr>
                    <a href="InsertarModificarEliminarPost?funcion=eliminar&id=<%=feed.getIdpost()%>" class="confirmar">Eliminar</a>
                    <%
                        }
                    %>
                </fieldset>
                <%
                        }
                    }

                    //Transformo el array list en un array primitivo para
                    //que el JavaScript lo pueda entender.
                    ArrayList<String> lista_fotos = photos.getFotos();
                    String array_js = "[";
                    for (int i = 0; i < lista_fotos.size(); i++) {
                        if (i == lista_fotos.size() - 1) {
                            array_js += "\"" + lista_fotos.get(i) + "\"";
                            
                        } else {
                            array_js += "\"" + lista_fotos.get(i) + "\",";
                        }

                    }
                    array_js += "]";
                %>
                
                <script type="text/javascript">
                    var array = <%=array_js%>;
                </script>
                <fieldset>
                    <legend align="center">Insertar Nuevo Post</legend>
                    <form action="InsertarModificarEliminarPost" name="insertarpost" method="post" enctype="multipart/form-data">
                        <input id="insertar" type="hidden" name="funcion" value="insertar">
                        <!-- El contenido es obligatorio, el resto no -->

                        <label for="content">Contenido: <span class="rojo">*</span></label><br><textarea class="cajatexto" name="content" id="content" required title="Debes insertar un contenido" rows="5"></textarea><br>
                        <input type="checkbox" id="tu_foto" name="tu_foto" onchange="cambiarInput(array)"> <label for="tu_foto">&iquest;La foto ya existe en tu perfil?</label><br>
                        <div id="contenedor_photo"><label for="photo">Imagen: </label><input type="file" id="photo" name="photo" accept="image/*"></div><br>
                        <label for="video">V&iacute;deo: </label><input type="file" id="video" name="video" accept="video/*"><br>
                        <input id="boton" type="submit" value="Insertar Post">

                    </form>
                </fieldset>
                    <p class="rojo"><% if (session.getAttribute(
                            "error") != null) {%><%= session.getAttribute("error")%><% }%></p>
            </article>
            <aside id="chat">
                <iframe src="ChatServlet"></iframe>
            </aside>
        </div>
        <script type="text/javascript" src="javascript/confirmar_eliminar_post.js"></script>
        <script type="text/javascript" src="javascript/submitevent.js"></script>
        <script type="text/javascript" src="javascript/changeinput.js"></script>
        <script tye="text/javascript" src="javascript/diferencia_fechas.js"></script>
        <script type="text/javascript" src="javascript/emoticonos-fuera.js"></script>
    </body>
</html>
