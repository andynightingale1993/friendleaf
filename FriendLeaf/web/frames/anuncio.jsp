<%-- 
    Document   : anuncio
    Created on : Nov 19, 2015, 6:51:17 PM
    Author     : andrew
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="../design/design_redirigir.css">
        <link rel="icon" type="image/png" href="../images/favicon.png">
        <title>FriendLeaf</title>
    </head>
    <body>
        <header>
            <img src="../images/header.png" alt="header">
        </header>
        <h1>¡Has pinchado en un anuncio!</h1>
        <p>Espera mientras le redirigimos...</p>
        <p>Quedan <span id="mensaje">5</span> segundos...</p>
        <script type="text/javascript" src="../javascript/countdown.js"></script>
        <a href="<%=request.getParameter("link")%>">O pinche aquí si no desea esperar!</a>
        <p id="link"><%=request.getParameter("link")%></p>
    </body>
</html>
