<%-- 
    Document   : ayuda
    Created on : 04-nov-2015, 9:07:54
    Author     : andrew

    Una página que muestra ayuda de la aplicación.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="design/design_help_small.css" media="all and (min-width: 240px) and (max-width: 640px)">
        <link rel="stylesheet" type="text/css" href="design/design_help.css" media="all and (min-width: 640px)">
        <link rel="icon" type="image/png" href="images/favicon.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FriendLeaf - Ayuda</title>
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
            <a href="FotosServlet">Fotos</a>
            <a href="GustosServlet?page=gustos">Modificar Gustos</a>
            <a href="PerfilServlet">Perfil</a>
            <a href="ayuda.jsp" id="selected">Ayuda</a>
            <a href="DestruirSesionServlet">Cerrar Sesi&oacute;n</a>
        </nav>
        <div id="cuerpo">
            <article>
                <h1>P&aacute;gina de Ayuda</h1>
                <p>Bienvenido a la p&aacute;gina de ayuda. Para resolver su duda, puede o contactar
                con el administrador o puede mirar nuestro FAQ para ver si existe la duda en cuesti&oacute;n.</p>
                <fieldset>
                    <legend>Datos de Contacto</legend>
                    <ul>
                        <li>Correo del administrador: cats.andy@hotmail.com</li>
                        <li>Teléfono de Contacto: (+34) 692102202 / 968551651</li>
                        <li>Skype: cats.andy@hotmail.com</li>
                    </ul>
                </fieldset>
                <p>También puede mirar nuestro <a href="faq.html" class="enlace">FAQ</a> o preguntas frecuentemente hechas, para ver
                si tu pregunta está ya contestada.</p>
            </article>
            <aside>
                <iframe src="ChatServlet"></iframe>
            </aside>
        </div>

        <p class="rojo"><% if (session.getAttribute("error") != null) {%><%= session.getAttribute("error")%><% }%></p>
    </body>
</html>
