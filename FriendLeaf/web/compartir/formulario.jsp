<%-- 
    Document   : formulario
    Created on : Dec 13, 2015, 9:10:16 AM
    Author     : andrew
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="../design/design.css">
        <title>Friend Leaf - Compartir</title>
    </head>
    <body>
        <h1>&iexcl;Bienvenido/a <%=session.getAttribute("usuario_compartir")%>!</h1>
        <fieldset>
            <legend>Compartir</legend>
            <form action="../CompartirServlet" name="insertar_post" method="post">
                <input type="hidden" name="compartir" value="true">
                <input type="hidden" name="funcion" value="insertar_post">

                <label for="content">Contenido: <span class="rojo">*</span></label><br><textarea class="cajatexto" name="content" id="content" required title="Debes insertar un contenido" rows="5">A&ntilde;ade contenido a tu post...</textarea><br>
                <%
                    if (request.getParameter("photo") != null && !request.getParameter("photo").equals("null")) {
                %>
                Foto:<br><img src="<%=request.getParameter("photo")%>" alt="photo"><br>
                <input type="hidden" name="photo" value="<%=request.getParameter("photo")%>">
                <%
                    }
                    if (request.getParameter("video") != null && !request.getParameter("video").equals("null")) {
                %>
                V&iacute;deo:<br>
                <iframe width="420" height="315" src="<%=request.getParameter("video")%>" frameborder="0"></iframe><br>
                <input type="hidden" name="video" value="<%=request.getParameter("video")%>">
                <%
                    }
                %>
                <input id="boton" type="submit" value="Insertar Post">

            </form>
        </fieldset>
        <p class="rojo"><% if (session.getAttribute("error") != null) {%><%= session.getAttribute("error")%><% }%></p>
    </body>
</html>
