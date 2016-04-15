<%-- 
    Document   : gustos.jsp
    Created on : 05-nov-2015, 11:19:41
    Author     : ubuntu

    Página que nos presenta un formulario para los gustos
    de una persona (para luego presentarle cosas que le gusten en el feed).
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="likes" class="datos.Like" scope="session"></jsp:useBean>
    <!DOCTYPE html>
<%
    String[] musica = likes.getMusica();
    String[] videojuegos = likes.getVideojuegos();
    String[] cine = likes.getCine();
    String[] literatura = likes.getLiteratura();
    String[] arte = likes.getArte();
    //Booleanos para los valores de checked.
    boolean pop = false, rock = false, metal = false, blackmetal = false, deathmetal = false, funk = false, electro = false, rockandroll = false,
            jazz = false, swing = false, country = false, blues = false, gospell = false, opera = false, clasico = false, v_accion = false, v_aventura = false,
            v_carreras = false, v_deporte = false, v_disparos = false, v_estrategia = false, v_fantasia = false, v_guerra = false, v_musica = false,
            v_rol = false, v_simulacion = false, c_drama = false, c_comedia = false, c_accion = false, c_aventura = false, c_terror = false, c_ciencia_ficcion = false,
            c_romantico = false, c_musical = false, c_melodrama = false, c_catastrofe = false, c_suspense = false, c_fantasia = false, c_historico = false,
            c_policiaco = false, c_belico = false, c_oeste = false, c_deportivo = false, c_marciales = false, c_zombis = false, c_gore = false, c_misterio = false,
            c_animacion = false, n_aprendizaje = false, n_aventura = false, n_belica = false, n_biografica = false, n_ciberpunk = false, n_ciencia_ficcion = false,
            n_dramatica = false, n_epistolar = false, n_erotica = false, n_espionaje = false, n_fantasia = false, n_fantastica = false, n_ficcion_especulativa = false,
            n_filosofica = false, n_historica = false, n_humor = false, n_juridico = false, n_misterio = false, n_negra = false, n_pastoril = false, n_picaresca = false,
            n_policiaca = false, n_posmoderna = false, n_psicologica = false, n_realismo = false, n_romantica = false, n_satirica = false, n_sentimental = false,
            n_suspense = false, n_terror = false, n_tragicomica = false, n_utopica = false, n_viaje_tiempo = false, n_victoriana = false, pintura = false,
            fotografia = false, television = false, comic = false, arquitectura = false, escultura = false;
    //Esto se hace para quitar los "[]" de la primera y última cadena.
    if (musica.length > 1) musica[0] = musica[0].substring(1, musica[0].length());
    if (musica.length > 1) musica[musica.length - 1] = musica[musica.length - 1].substring(0, musica[musica.length - 1].length() - 1);

    if (videojuegos.length > 1) videojuegos[0] = videojuegos[0].substring(1, videojuegos[0].length());
    if (videojuegos.length > 1) videojuegos[videojuegos.length - 1] = videojuegos[videojuegos.length - 1].substring(0, videojuegos[videojuegos.length - 1].length() - 1);

    if (cine.length > 1) cine[0] = cine[0].substring(1, cine[0].length());
    if (cine.length > 1) cine[cine.length - 1] = cine[cine.length - 1].substring(0, cine[cine.length - 1].length() - 1);

    if (literatura.length > 1) literatura[0] = literatura[0].substring(1, literatura[0].length());
    if (literatura.length > 1) literatura[literatura.length - 1] = literatura[literatura.length - 1].substring(0, literatura[literatura.length - 1].length() - 1);

    if (arte.length > 1) arte[0] = arte[0].substring(1, arte[0].length());
    if (arte.length > 1) arte[arte.length - 1] = arte[arte.length - 1].substring(0, arte[arte.length - 1].length() - 1);

    //Si son de longitud 1 tenemos que hacer un tratamiento distinto.
    if (musica.length == 1) {
        musica[0] = musica[0].substring(1, musica[0].length()-1);
    }
    
    if (videojuegos.length == 1) {
        videojuegos[0] = videojuegos[0].substring(1, videojuegos[0].length()-1);
    }
    
    if (cine.length == 1) {
        cine[0] = cine[0].substring(1, cine[0].length()-1);
    }
    
    if (literatura.length == 1) {
        literatura[0] = literatura[0].substring(1, literatura[0].length()-1);
    }
    
    if (arte.length == 1) {
        arte[0] = arte[0].substring(1, arte[0].length()-1);
    }
    //Recorremos cada array y le damos el valor booleano true correspondiente a cada uno de los elementos.
    for (int i = 0; i < musica.length; i++) {
        String musica_actual = musica[i].trim();
        switch (musica_actual) {
            case "pop": {
                pop = true;
            }
            break;
            case "rock": {
                rock = true;
            }
            break;
            case "metal": {
                metal = true;
            }
            break;
            case "blackmetal": {
                blackmetal = true;
            }
            break;
            case "deathmetal": {
                deathmetal = true;
            }
            break;
            case "funk": {
                funk = true;
            }
            break;
            case "electro": {
                electro = true;
            }
            break;
            case "rockandroll": {
                rockandroll = true;
            }
            break;
            case "jazz": {
                jazz = true;
            }
            break;
            case "swing": {
                swing = true;
            }
            break;
            case "country": {
                country = true;
            }
            break;
            case "blues": {
                blues = true;
            }
            break;
            case "gospell": {
                gospell = true;
            }
            break;
            case "opera": {
                opera = true;
            }
            break;
            case "clasico": {
                clasico = true;
            }
            break;
        }
    }
    for (int i = 0; i < videojuegos.length; i++) {
        String videojuego_actual = videojuegos[i].trim();
        switch (videojuego_actual) {
            case "accion": {
                v_accion = true;
            }
            break;
            case "aventura": {
                v_aventura = true;
            }
            break;
            case "carreras": {
                v_carreras = true;
            }
            break;
            case "deporte": {
                v_deporte = true;
            }
            break;
            case "disparos": {
                v_disparos = true;
            }
            break;
            case "estrategia": {
                v_estrategia = true;
            }
            break;
            case "fantasia": {
                v_fantasia = true;
            }
            break;
            case "guerra": {
                v_guerra = true;
            }
            break;
            case "musica": {
                v_musica = true;
            }
            break;
            case "rol": {
                v_rol = true;
            }
            break;
            case "simulacion": {
                v_simulacion = true;
            }
            break;
        }
    }
    for (int i = 0; i < cine.length; i++) {
        String cine_actual = cine[i].trim();
        switch (cine_actual) {
            case "drama": {
                c_drama = true;
            }
            break;
            case "comedia": {
                c_comedia = true;
            }
            break;
            case "accion": {
                c_accion = true;
            }
            break;
            case "aventura": {
                c_aventura = true;
            }
            break;
            case "terror": {
                c_terror = true;
            }
            break;
            case "ficcion": {
                c_ciencia_ficcion = true;
            }
            break;
            case "romantico": {
                c_romantico = true;
            }
            break;
            case "musical": {
                c_musical = true;
            }
            break;
            case "melodrama": {
                c_melodrama = true;
            }
            break;
            case "catastrofe": {
                c_catastrofe = true;
            }
            break;
            case "suspense": {
                c_suspense = true;
            }
            break;
            case "fantasia": {
                c_fantasia = true;
            }
            break;
            case "historico": {
                c_historico = true;
            }
            break;
            case "policiaco": {
                c_policiaco = true;
            }
            break;
            case "belico": {
                c_belico = true;
            }
            break;
            case "oeste": {
                c_oeste = true;
            }
            break;
            case "deportivo": {
                c_deportivo = true;
            }
            break;
            case "artesmarciales": {
                c_marciales = true;
            }
            break;
            case "zombis": {
                c_zombis = true;
            }
            break;
            case "gore": {
                c_gore = true;
            }
            break;
            case "misterio": {
                c_misterio = true;
            }
            break;
            case "animacion": {
                c_animacion = true;
            }
            break;
        }
    }
    for (int i = 0; i < literatura.length; i++) {
        String literatura_actual = literatura[i].trim();
        switch (literatura_actual) {
            case "aprendizaje": {
                n_aprendizaje = true;
            }
            break;
            case "aventuras": {
                n_aventura = true;
            }
            break;
            case "belicas": {
                n_belica = true;
            }
            break;
            case "biograficas": {
                n_biografica = true;
            }
            break;
            case "ciberpunk": {
                n_ciberpunk = true;
            }
            break;
            case "cienciaficcion": {
                n_ciencia_ficcion = true;
            }
            break;
            case "dramaticas": {
                n_dramatica = true;
            }
            break;
            case "epistolares": {
                n_epistolar = true;
            }
            break;
            case "eroticas": {
                n_erotica = true;
            }
            break;
            case "espionaje": {
                n_espionaje = true;
            }
            break;
            case "fantasia": {
                n_fantasia = true;
            }
            break;
            case "fantasticas": {
                n_fantastica = true;
            }
            break;
            case "ficcionespeculativa": {
                n_ficcion_especulativa = true;
            }
            break;
            case "filosoficas": {
                n_filosofica = true;
            }
            break;
            case "historicas": {
                n_historica = true;
            }
            break;
            case "humor": {
                n_humor = true;
            }
            break;
            case "temasjuridicos": {
                n_juridico = true;
            }
            break;
            case "misterio": {
                n_misterio = true;
            }
            break;
            case "negra": {
                n_negra = true;
            }
            break;
            case "pastoriles": {
                n_pastoril = true;
            }
            break;
            case "picarescas": {
                n_picaresca = true;
            }
            break;
            case "policiacas": {
                n_policiaca = true;
            }
            break;
            case "posmodernas": {
                n_posmoderna = true;
            }
            break;
            case "psicologicas": {
                n_psicologica = true;
            }
            break;
            case "realismomagico": {
                n_realismo = true;
            }
            break;
            case "romanticas": {
                n_romantica = true;
            }
            break;
            case "satiricas": {
                n_satirica = true;
            }
            break;
            case "sentimentales": {
                n_sentimental = true;
            }
            break;
            case "suspense": {
                n_suspense = true;
            }
            break;
            case "terror": {
                n_terror = true;
            }
            break;
            case "tragicomicas": {
                n_tragicomica = true;
            }
            break;
            case "utopicas": {
                n_utopica = true;
            }
            break;
            case "viajestiempo": {
                n_viaje_tiempo = true;
            }
            break;
            case "victorianas": {
                n_victoriana = true;
            }
            break;
        }
    }
    for (int i = 0; i < arte.length; i++) {
        String arte_actual = arte[i].trim();
        switch (arte_actual) {
            case "pintura": {
                pintura = true;
            }
            break;
            case "fotografia": {
                fotografia = true;
            }
            break;
            case "television": {
                television = true;
            }
            break;
            case "comic": {
                comic = true;
            }
            break;
            case "arquitectura": {
                arquitectura = true;
            }
            break;
            case "escultura": {
                escultura = true;
            }
            break;
        }
    }
%>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="design/design_small.css" media="all and (min-width: 240px) and (max-width: 640px)">
        <link rel="stylesheet" type="text/css" href="design/design.css" media="all and (min-width: 640px)">
        <link rel="icon" type="image/png" href="images/favicon.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FriendLeaf - Gustos</title>
        <style>
            body {
                cursor: url('images/pencil.png'), auto;
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
            <a href="GustosServlet?page=gustos" id="selected">Modificar Gustos</a>
            <a href="PerfilServlet">Perfil</a>
            <a href="ayuda.jsp">Ayuda</a>
            <a href="DestruirSesionServlet">Cerrar Sesi&oacute;n</a>
        </nav>
        <div id="cuerpo">
            <article>
                <h1>Selecciona las cosas que le gustan:</h1>
                <form action="ControladorServlet" method="post" name="gustos">
                    <input type="hidden" name="page" value="gustos.jsp">
                    <fieldset>
                        <legend align="center">M&uacute;sica</legend>
                        <input type="checkbox" name="genero" value="pop" <% if (pop) {%>checked<%}%>> Pop
                        <input type="checkbox" name="genero" value="rock" <% if (rock) {%>checked<%}%>> Rock
                        <input type="checkbox" name="genero" value="metal" <% if (metal) {%>checked<%}%>> Metal
                        <input type="checkbox" name="genero" value="blackmetal" <% if (blackmetal) {%>checked<%}%>> Black Metal
                        <input type="checkbox" name="genero" value="deathmetal" <% if (deathmetal) {%>checked<%}%>> Death Metal
                        <input type="checkbox" name="genero" value="funk" <% if (funk) {%>checked<%}%>> Funk
                        <input type="checkbox" name="genero" value="electro" <% if (electro) {%>checked<%}%>> House
                        <input type="checkbox" name="genero" value="rockandroll" <% if (rockandroll) {%>checked<%}%>> Rock & Roll
                        <input type="checkbox" name="genero" value="jazz" <% if (jazz) {%>checked<%}%>> Jazz
                        <input type="checkbox" name="genero" value="swing" <% if (swing) {%>checked<%}%>> Swing
                        <input type="checkbox" name="genero" value="country" <% if (country) {%>checked<%}%>> Country
                        <input type="checkbox" name="genero" value="blues" <% if (blues) {%>checked<%}%>> Blues
                        <input type="checkbox" name="genero" value="gospell" <% if (gospell) {%>checked<%}%>> Gospell
                        <input type="checkbox" name="genero" value="opera" <% if (opera) {%>checked<%}%>> &Oacute;pera
                        <input type="checkbox" name="genero" value="clasico" <% if (clasico) {%>checked<%}%>> Cl&aacute;sica
                    </fieldset>
                    <fieldset>
                        <legend align="center">Videojuegos</legend>
                        <input type="checkbox" name="videojuegos" value="accion" <% if (v_accion) {%>checked<%}%>> Acci&oacute;n
                        <input type="checkbox" name="videojuegos" value="aventura" <% if (v_aventura) {%>checked<%}%>> Aventura
                        <input type="checkbox" name="videojuegos" value="carreras" <% if (v_carreras) {%>checked<%}%>> Carreras
                        <input type="checkbox" name="videojuegos" value="deporte" <% if (v_deporte) {%>checked<%}%>> Deporte
                        <input type="checkbox" name="videojuegos" value="disparos" <% if (v_disparos) {%>checked<%}%>> Disparos
                        <input type="checkbox" name="videojuegos" value="estrategia" <% if (v_estrategia) {%>checked<%}%>> Estrategia
                        <input type="checkbox" name="videojuegos" value="fantasia" <% if (v_fantasia) {%>checked<%}%>> Fantas&iacute;a
                        <input type="checkbox" name="videojuegos" value="guerra" <% if (v_guerra) {%>checked<%}%>> Guerra
                        <input type="checkbox" name="videojuegos" value="musica" <% if (v_musica) {%>checked<%}%>> M&uacute;sica
                        <input type="checkbox" name="videojuegos" value="rol" <% if (v_rol) {%>checked<%}%>> Rol
                        <input type="checkbox" name="videojuegos" value="simulacion" <% if (v_simulacion) {%>checked<%}%>> Simulaci&oacute;n
                    </fieldset>
                    <fieldset>
                        <legend align="center">Cine</legend>
                        <input type="checkbox" name="cine" value="drama" <% if (c_drama) {%>checked<%}%>> Drama
                        <input type="checkbox" name="cine" value="comedia" <% if (c_comedia) {%>checked<%}%>> Comedia
                        <input type="checkbox" name="cine" value="accion" <% if (c_accion) {%>checked<%}%>> Acci&oacute;n
                        <input type="checkbox" name="cine" value="aventura" <% if (c_aventura) {%>checked<%}%>> Aventura
                        <input type="checkbox" name="cine" value="terror" <% if (c_terror) {%>checked<%}%>> Cine de Terror
                        <input type="checkbox" name="cine" value="ficcion" <% if (c_ciencia_ficcion) {%>checked<%}%>> Cine de Ciencia Ficci&oacute;n
                        <input type="checkbox" name="cine" value="romantico" <% if (c_romantico) {%>checked<%}%>> Cine Rom&aacute;ntico
                        <input type="checkbox" name="cine" value="musical" <% if (c_musical) {%>checked<%}%>> Cine Musical
                        <input type="checkbox" name="cine" value="melodrama" <% if (c_melodrama) {%>checked<%}%>> Melodrama
                        <input type="checkbox" name="cine" value="catastrofe" <% if (c_catastrofe) {%>checked<%}%>> Cine Cat&aacute;strofe
                        <input type="checkbox" name="cine" value="suspense" <% if (c_suspense) {%>checked<%}%>> Suspense
                        <input type="checkbox" name="cine" value="fantasia" <% if (c_fantasia) {%>checked<%}%>> Fantas&iacute;a
                        <input type="checkbox" name="cine" value="historico" <% if (c_historico) {%>checked<%}%>> Hist&oacute;rico
                        <input type="checkbox" name="cine" value="policiaco" <% if (c_policiaco) {%>checked<%}%>> Polic&iacute;aco
                        <input type="checkbox" name="cine" value="belico" <% if (c_belico) {%>checked<%}%>> B&eacute;lico
                        <input type="checkbox" name="cine" value="oeste" <% if (c_oeste) {%>checked<%}%>> del Oeste
                        <input type="checkbox" name="cine" value="deportivo" <% if (c_deportivo) {%>checked<%}%>> Deportivo
                        <input type="checkbox" name="cine" value="artesmarciales" <% if (c_marciales) {%>checked<%}%>> Cine de Artes Marciales
                        <input type="checkbox" name="cine" value="zombis" <% if (c_zombis) {%>checked<%}%>> Cine de Zombis
                        <input type="checkbox" name="cine" value="gore" <% if (c_gore) {%>checked<%}%>> Cine de Gore
                        <input type="checkbox" name="cine" value="misterio" <% if (c_misterio) {%>checked<%}%>> Cine de Misterio
                        <input type="checkbox" name="cine" value="animacion" <% if (c_animacion) {%>checked<%}%>> Animaci&oacute;n
                    </fieldset>
                    <fieldset>
                        <legend align="center">Literatura</legend>
                        <input type="checkbox" name="literatura" value="aprendizaje" <% if (n_aprendizaje) {%>checked<%}%>> Novelas de Aprendizaje
                        <input type="checkbox" name="literatura" value="aventuras" <% if (n_aventura) {%>checked<%}%>> Novelas de Aventuras
                        <input type="checkbox" name="literatura" value="belicas" <% if (n_belica) {%>checked<%}%>> Novelas B&eacute;licas
                        <input type="checkbox" name="literatura" value="biograficas" <% if (n_biografica) {%>checked<%}%>> Novelas Biogr&aacute;ficas
                        <input type="checkbox" name="literatura" value="ciberpunk" <% if (n_ciberpunk) {%>checked<%}%>> Novelas Ciberpunk
                        <input type="checkbox" name="literatura" value="cienciaficcion" <% if (n_ciencia_ficcion) {%>checked<%}%>> Novelas de Ciencia Ficci&oacute;n
                        <input type="checkbox" name="literatura" value="dramaticas" <% if (n_dramatica) {%>checked<%}%>> Novelas Dram&aacute;ticas
                        <input type="checkbox" name="literatura" value="epistolares" <% if (n_epistolar) {%>checked<%}%>> Novelas Epistolares
                        <input type="checkbox" name="literatura" value="eroticas" <% if (n_erotica) {%>checked<%}%>> Novelas Er&oacute;ticas
                        <input type="checkbox" name="literatura" value="espionaje" <% if (n_espionaje) {%>checked<%}%>> Novelas de Espionaje
                        <input type="checkbox" name="literatura" value="fantasia" <% if (n_fantasia) {%>checked<%}%>> Novelas de Fantas&iacute;a
                        <input type="checkbox" name="literatura" value="fantasticas" <% if (n_fantastica) {%>checked<%}%>> Novelas Fant&aacute;sticas
                        <input type="checkbox" name="literatura" value="ficcionespeculativa" <% if (n_ficcion_especulativa) {%>checked<%}%>> Novelas de Ficci&oacute;n Especulativa
                        <input type="checkbox" name="literatura" value="filosoficas" <% if (n_filosofica) {%>checked<%}%>> Novelas Filos&oacute;ficas
                        <input type="checkbox" name="literatura" value="historicas" <% if (n_historica) {%>checked<%}%>> Novelas Hist&oacute;ricas
                        <input type="checkbox" name="literatura" value="humor" <% if (n_humor) {%>checked<%}%>> Novelas de Humor
                        <input type="checkbox" name="literatura" value="temasjuridicos" <% if (n_juridico) {%>checked<%}%>> Novelas sobre Temas Jur&iacute;dicos
                        <input type="checkbox" name="literatura" value="misterio" <% if (n_misterio) {%>checked<%}%>> Novelas de Misterio
                        <input type="checkbox" name="literatura" value="negra" <% if (n_negra) {%>checked<%}%>> Novela Negra
                        <input type="checkbox" name="literatura" value="pastoriles" <% if (n_pastoril) {%>checked<%}%>> Novelas Pastoriles
                        <input type="checkbox" name="literatura" value="picarescas" <% if (n_picaresca) {%>checked<%}%>> Novelas Picarescas
                        <input type="checkbox" name="literatura" value="policiacas" <% if (n_policiaca) {%>checked<%}%>> Novelas Polic&iacute;acas
                        <input type="checkbox" name="literatura" value="posmodernas" <% if (n_posmoderna) {%>checked<%}%>> Novelas Posmodernas
                        <input type="checkbox" name="literatura" value="psicologicas" <% if (n_psicologica) {%>checked<%}%>> Novelas Psicol&oacute;gicas
                        <input type="checkbox" name="literatura" value="realismomagico" <% if (n_realismo) {%>checked<%}%>> Novelas de Realismo M&aacute;gico
                        <input type="checkbox" name="literatura" value="romanticas" <% if (n_romantica) {%>checked<%}%>> Novelas Rom&aacute;nticas
                        <input type="checkbox" name="literatura" value="satiricas" <% if (n_satirica) {%>checked<%}%>> Novelas Sat&iacute;ricas
                        <input type="checkbox" name="literatura" value="sentimentales" <% if (n_sentimental) {%>checked<%}%>> Novelas Sentimentales
                        <input type="checkbox" name="literatura" value="suspense" <% if (n_suspense) {%>checked<%}%>> Novelas de Suspense
                        <input type="checkbox" name="literatura" value="terror" <% if (n_terror) {%>checked<%}%>> Novelas de Terror
                        <input type="checkbox" name="literatura" value="tragicomicas" <% if (n_tragicomica) {%>checked<%}%>> Novelas Tragic&oacute;micas
                        <input type="checkbox" name="literatura" value="utopicas" <% if (n_utopica) {%>checked<%}%>> Novelas Ut&oacute;picas
                        <input type="checkbox" name="literatura" value="viajestiempo" <% if (n_viaje_tiempo) {%>checked<%}%>> Novelas de Viajes en el Tiempo
                        <input type="checkbox" name="literatura" value="victorianas" <% if (n_victoriana) {%>checked<%}%>> Novelas Victorianas
                    </fieldset>
                    <fieldset>
                        <legend align="center">Arte</legend>
                        <input type="checkbox" name="arte" value="pintura" <% if (pintura) {%>checked<%}%>> Pintura
                        <input type="checkbox" name="arte" value="fotografia" <% if (fotografia) {%>checked<%}%>> Fotograf&iacute;a
                        <input type="checkbox" name="arte" value="television" <% if (television) {%>checked<%}%>> Televisi&oacute;n
                        <input type="checkbox" name="arte" value="comic" <% if (comic) {%>checked<%}%>> C&oacute;mic
                        <input type="checkbox" name="arte" value="arquitectura" <% if (arquitectura) {%>checked<%}%>> Arquitectura
                        <input type="checkbox" name="arte" value="escultura" <% if (escultura) {%>checked<%}%>> Escultura
                    </fieldset>
                    <input id="boton" type="submit" value="Terminado">
                </form>
            </article>
            <aside>
                <iframe src="ChatServlet"></iframe>
            </aside>
        </div>
        <script type="text/javascript" src="javascript/submitevent.js"></script>
    </body>
</html>
