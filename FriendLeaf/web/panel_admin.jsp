<%-- 
    Document   : panel_admin
    Created on : Nov 25, 2015, 12:01:16 PM
    Author     : andrew
--%>

<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="lista_usuarios" class="java.util.ArrayList" scope="session"/>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="design/design.css">
        <link rel="icon" type="image/png" href="images/favicon.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FriendLeaf - Admin</title>
    </head>

    <body>
        <header>
            <img src="images/header.png" alt="header">
        </header>
        <nav>
            <a href="AdminServlet?page=index.jsp">Panel de Administrador</a>
            <a href="DestruirSesionServlet">Cerrar Sesi&oacute;n</a>
        </nav>
        <h1>Panel de Administrador</h1>
        <%
            //Si no hay error imprimimos todas las tablas.
            if (session.getAttribute("error") == null || session.getAttribute("error").equals("")) {
        %> 
        <fieldset>
            <legend align="center">Usuarios</legend>
            <%
                if (lista_usuarios.isEmpty()) {
            %>
            <p>No hay usuarios.</p>
            <%
            } else {

                //Recorremos la lista de usuarios.
                for (int i = 0; i < lista_usuarios.size(); i++) {


            %>
            <a class="enlace" href="AdminServlet?page=panel_admin.jsp&user=<%=lista_usuarios.get(i)%>"><%=lista_usuarios.get(i)%></a>
            <hr>
            <%
                }
            %>
        </fieldset>
        <%
            }
        } else {
        %>
        <p class="rojo"><%=session.getAttribute("error")%></p>
        <a href="AdminServlet?page=index.jsp">Intentarlo de Nuevo</a>
        <%
            }

            if (session.getAttribute("correcto") != null) {
        %>
        <p class="correcto"><%= session.getAttribute("correcto")%></p>
        <%
                session.setAttribute("correcto", null);
            }
        %>
    </body>
</html>
