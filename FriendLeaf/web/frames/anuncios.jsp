<%-- 
    Document   : anuncios
    Created on : Nov 19, 2015, 3:59:58 PM
    Author     : andrew
--%>

<%@page import="java.util.Arrays"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="likes" class="datos.Like" scope="session"></jsp:useBean>
    <!DOCTYPE html>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <link rel="stylesheet" type="text/css" href="../design/design_iframe.css">
            <title>FriendLeaf</title>
        </head>
        <body>
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
            musica[0] = musica[0].substring(1, musica[0].length());
            musica[musica.length - 1] = musica[musica.length - 1].substring(0, musica[musica.length - 1].length() - 1);

            videojuegos[0] = videojuegos[0].substring(1, videojuegos[0].length());
            videojuegos[videojuegos.length - 1] = videojuegos[videojuegos.length - 1].substring(0, videojuegos[videojuegos.length - 1].length() - 1);
                                                        
            cine[0] = cine[0].substring(1, cine[0].length());
            cine[cine.length - 1] = cine[cine.length - 1].substring(0, cine[cine.length - 1].length() - 1);

            literatura[0] = literatura[0].substring(1, literatura[0].length());
            literatura[literatura.length - 1] = literatura[literatura.length - 1].substring(0, literatura[literatura.length - 1].length() - 1);

            arte[0] = arte[0].substring(1, arte[0].length());
            arte[arte.length - 1] = arte[arte.length - 1].substring(0, arte[arte.length - 1].length() - 1);

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
        <% if (musica.length != 0) {
        %>
        <fieldset>
            <legend align="center">M&uacute;sica</legend>
            <% if (pop) { %><a href="anuncio.jsp?link=http://www.snowpatrol.com/" target="_blank">Snow Patrol</a><hr><%}%>
            <% if (rock) { %><a href="anuncio.jsp?link=http://www.the-scorpions.com/" target="_blank">Scorpions</a><hr><%}%>
            <% if (metal) { %><a href="anuncio.jsp?link=https://metallica.com/" target="_blank">Metallica</a><hr><%}%>
            <% if (blackmetal) { %><a href="anuncio.jsp?link=http://equilibrium-metal.net/en/" target="_blank">Equilibrium</a><hr><%}%>
            <% if (deathmetal) { %><a href="anuncio.jsp?link=http://www.cradleoffilth.com/" target="_blank">Cradle of Filth</a><hr><%}%>
            <% if (funk) { %><a href="anuncio.jsp?link=http://www.jamesbrown.com/" target="_blank">James Brown</a><hr><%}%>
            <% if (electro) { %><a href="anuncio.jsp?link=http://www.keshasparty.com/" target="_blank">Kesha</a><hr><%}%>
            <% if (rockandroll) { %><a href="anuncio.jsp?link=http://www.biografiasyvidas.com/biografia/p/presley.htm" target="_blank">Biograf&iacute;a de Elvis Presley</a><hr><%}%>
            <% if (jazz) { %><a href="anuncio.jsp?link=http://www.danielodonnellfanclub.com/" target="_blank">Daniel O'Donnell</a><hr><%}%>
            <% if (swing) { %><a href="anuncio.jsp?link=http://glennmillerorchestra.com/" target="_blank">Glenn Miller</a><hr><%}%>
            <% if (country) { %><a href="anuncio.jsp?link=http://www.thealabamaband.com/welcome.html" target="_blank">Alabama Band</a><hr><%}%>
            <% if (blues) { %><a href="anuncio.jsp?link=http://www.bbking.com/" target="_blank">B. B. King</a><hr><%}%>
            <% if (gospell) { %><a href="anuncio.jsp?link=http://www.whitneyhouston.com/es" target="_blank">Whitney Houston</a><hr><%}%>
            <% if (opera) { %><a href="anuncio.jsp?link=http://www.rogerwaters.org/" target="_blank">Roger Waters Fan Club</a><hr><%}%>
            <% if (clasico) { %><a href="anuncio.jsp?link=http://www.mozart.cat/cast/biografia.htm" target="_blank">Biografía de Mozart</a><hr><%}%>
        </fieldset>
        <%
            }
            if (videojuegos.length != 0) {
        %>
        <fieldset>
            <legend align="center">Videojuegos</legend>
            <% if (v_accion) { %><a href="anuncio.jsp?link=https://godofwar.playstation.com/" target="_blank">God of War</a><hr><%}%>
            <% if (v_aventura) { %><a href="anuncio.jsp?link=http://www.juegos.com/juego/crash-bandicoot" target="_blank">Crash Bandicoot</a><hr><%}%>
            <% if (v_carreras) { %><a href="anuncio.jsp?link=http://www.needforspeed.com/es_ES" target="_blank">Need for Speed</a><hr><%}%>
            <% if (v_deporte) { %><a href="anuncio.jsp?link=http://es.fifa.com/" target="_blank">FIFA</a><hr><%}%>
            <% if (v_disparos) { %><a href="anuncio.jsp?link=https://www.callofduty.com/es/" target="_blank">Call of Duty</a><hr><%}%>
            <% if (v_estrategia) { %><a href="anuncio.jsp?link=https://www.guerrastribales.es/" target="_blank">Guerras Tribales</a><hr><%}%>
            <% if (v_fantasia) { %><a href="anuncio.jsp?link=http://es.finalfantasy.wikia.com/wiki/Final_Fantasy_IX" target="_blank">Final Fantasy IX</a><hr><%}%>
            <% if (v_guerra) { %><a href="anuncio.jsp?link=http://www.commandandconquer.com/" target="_blank">Command & Conquer</a><hr><%}%>
            <% if (v_musica) { %><a href="anuncio.jsp?link=https://www.guitarhero.com/es/" target="_blank">Guitar Hero</a><hr><%}%>
            <% if (v_rol) { %><a href="anuncio.jsp?link=http://eu.battle.net" target="_blank">Blizzard Entertainment</a><hr><%}%>
            <% if (v_simulacion) { %><a href="anuncio.jsp?link=http://es.thesims3.com/" target="_blank">Los Sims 3</a><hr><%}%>
        </fieldset>
        <%
            }
            if (cine.length != 0) {
        %>
        <fieldset>
            <legend align="center">Cine</legend>

            <% if (c_drama) { %><a href="anuncio.jsp" target="_blank">Drama</a><hr><%}%>
            <% if (c_comedia) { %><a href="anuncio.jsp" target="_blank">Comedia</a><hr><%}%>
            <% if (c_accion) { %><a href="anuncio.jsp?link=https://es.wikipedia.org/wiki/Hitman:_Agent_47" target="_blank">Hitman: Agente 47</a><hr><%}%>
            <% if (c_aventura) { %><a href="anuncio.jsp?link=https://es.wikipedia.org/wiki/Frozen_%28pel%C3%ADcula_de_2013%29" target="_blank">Frozen - Disney</a><hr><%}%>
            <% if (c_terror) { %><a href="anuncio.jsp" target="_blank">Cine de Terror</a><hr><%}%>
            <% if (c_ciencia_ficcion) { %><a href="anuncio.jsp" target="_blank">Ciencia Ficci&oacute;n</a><hr><%}%>
            <% if (c_romantico) { %><a href="anuncio.jsp" target="_blank">Cine Rom&aacute;ntico</a><hr><%}%>
            <% if (c_musical) { %><a href="anuncio.jsp" target="_blank">Cine Musical</a><hr><%}%>
            <% if (c_melodrama) { %><a href="anuncio.jsp" target="_blank">Melodrama</a><hr><%}%>
            <% if (c_catastrofe) { %><a href="anuncio.jsp" target="_blank">Cine Cat&aacute;strofe</a><hr><%}%>
            <% if (c_suspense) { %><a href="anuncio.jsp" target="_blank">Suspense</a><hr><%}%>
            <% if (c_fantasia) { %><a href="anuncio.jsp" target="_blank">Fantas&iacute;a</a><hr><%}%>
            <% if (c_historico) { %><a href="anuncio.jsp" target="_blank">Hist&oacute;rico</a><hr><%}%>
            <% if (c_policiaco) { %><a href="anuncio.jsp" target="_blank">Policiaco</a><hr><%}%>
            <% if (c_belico) { %><a href="anuncio.jsp" target="_blank">Cine B&eacute;lico</a><hr><%}%>
            <% if (c_oeste) { %><a href="anuncio.jsp" target="_blank">Lejano Oeste</a><hr><%}%>
            <% if (c_deportivo) { %><a href="anuncio.jsp?link=https://en.wikipedia.org/wiki/Slap_Shot_%28film%29" target="_blank">Slap Shot</a><hr><%}%>
            <% if (c_marciales) { %><a href="anuncio.jsp" target="_blank">Cine de Artes Marciales</a><hr><%}%>
            <% if (c_zombis) { %><a href="anuncio.jsp" target="_blank">Cine de Zombis</a><hr><%}%>
            <% if (c_gore) { %><a href="anuncio.jsp" target="_blank">Cine de Gore</a><hr><%}%>
            <% if (c_misterio) { %><a href="anuncio.jsp" target="_blank">Misterio</a><hr><%}%>
            <% if (c_animacion) { %><a href="anuncio.jsp" target="_blank">Animaci&oacute;n</a><hr><%}%>
        </fieldset>
        <%
            }
            if (literatura.length != 0) {
        %>
        <fieldset>
            <legend align="center">Literatura</legend>
            <% if (n_aprendizaje) { %><a href="anuncio.jsp" target="_blank">Novelas de Aprendizaje</a><hr><%}%>
            <% if (n_aventura) { %><a href="anuncio.jsp" target="_blank">Novelas de Aventuras</a><hr><%}%>
            <% if (n_belica) { %><a href="anuncio.jsp" target="_blank">Novelas B&eacute;licas</a><hr><%}%>
            <% if (n_biografica) { %><a href="anuncio.jsp" target="_blank">Novelas Biogr&aacute;ficas</a><hr><%}%>
            <% if (n_ciberpunk) { %><a href="anuncio.jsp" target="_blank">Novelas Ciberpunk</a><hr><%}%>
            <% if (n_ciencia_ficcion) { %><a href="anuncio.jsp" target="_blank">Novleas de Ciencia Ficci&oacute;n</a><hr><%}%>
            <% if (n_dramatica) { %><a href="anuncio.jsp" target="_blank">Novelas Dram&aacute;ticas</a><hr><%}%>
            <% if (n_epistolar) { %><a href="anuncio.jsp" target="_blank">Novelas Epistolares</a><hr><%}%>
            <% if (n_erotica) { %><a href="anuncio.jsp" target="_blank">Novelas Er&oacute;ticas</a><hr><%}%>
            <% if (n_espionaje) { %><a href="anuncio.jsp" target="_blank">Novelas de Espionaje</a><hr><%}%>
            <% if (n_fantasia) { %><a href="anuncio.jsp" target="_blank">Novelas de Fantas&iacute;a</a><hr><%}%>
            <% if (n_fantastica) { %><a href="anuncio.jsp" target="_blank">Novelas Fant&aacute;sticas</a><hr><%}%>
            <% if (n_ficcion_especulativa) { %><a href="anuncio.jsp" target="_blank">Novelas de Ficci&oacute;n Especulativa</a><hr><%}%>
            <% if (n_filosofica) { %><a href="anuncio.jsp" target="_blank">Novelas Filos&oacute;ficas</a><hr><%}%>
            <% if (n_historica) { %><a href="anuncio.jsp" target="_blank">Novelas Hist&oacute;ricas</a><hr><%}%>
            <% if (n_humor) { %><a href="anuncio.jsp" target="_blank">Novelas de Humor</a><hr><%}%>
            <% if (n_juridico) { %><a href="anuncio.jsp" target="_blank">Novelas de Temas Jur&iacute;dicos</a><hr><%}%>
            <% if (n_misterio) { %><a href="anuncio.jsp" target="_blank">Novelas de Misterio</a><hr><%}%>
            <% if (n_negra) { %><a href="anuncio.jsp" target="_blank">Novelas Negras</a><hr><%}%>
            <% if (n_pastoril) { %><a href="anuncio.jsp" target="_blank">Novelas Pastoriles</a><hr><%}%>
            <% if (n_picaresca) { %><a href="anuncio.jsp" target="_blank">Novelas Picarescas</a><hr><%}%>
            <% if (n_policiaca) { %><a href="anuncio.jsp" target="_blank">Novelas Policiacas</a><hr><%}%>
            <% if (n_posmoderna) { %><a href="anuncio.jsp" target="_blank">Novelas Posmodernas</a><hr><%}%>
            <% if (n_psicologica) { %><a href="anuncio.jsp" target="_blank">Novelas Psicol&oacute;gicas</a><hr><%}%>
            <% if (n_realismo) { %><a href="anuncio.jsp" target="_blank">Novelas de Realismo M&aacute;gico</a><hr><%}%>
            <% if (n_romantica) { %><a href="anuncio.jsp" target="_blank">Novelas Rom&aacute;nticas</a><hr><%}%>
            <% if (n_satirica) { %><a href="anuncio.jsp" target="_blank">Novelas Sat&iacute;ricas</a><hr><%}%>
            <% if (n_sentimental) { %><a href="anuncio.jsp" target="_blank">Novelas Sentimentales</a><hr><%}%>
            <% if (n_suspense) { %><a href="anuncio.jsp" target="_blank">Novelas de Suspense</a><hr><%}%>
            <% if (n_terror) { %><a href="anuncio.jsp" target="_blank">Novelas de Terror</a><hr><%}%>
            <% if (n_tragicomica) { %><a href="anuncio.jsp" target="_blank">Novelas Tragic&oacute;micas</a><hr><%}%>
            <% if (n_utopica) { %><a href="anuncio.jsp" target="_blank">Novelas Ut&oacute;picas</a><hr><%}%>
            <% if (n_viaje_tiempo) { %><a href="anuncio.jsp" target="_blank">Novelas de Viajes en el Tiempo</a><hr><%}%>
            <% if (n_victoriana) { %><a href="anuncio.jsp" target="_blank">Novelas Victorianas</a><hr><%}%>
        </fieldset>
        <%
            }
            if (arte.length != 0) {
        %>
        <fieldset>
            <legend align="center">Arte</legend>
            <% if (pintura) { %><a href="anuncio.jsp" target="_blank">Pintura</a><hr><%}%>
            <% if (fotografia) { %><a href="anuncio.jsp" target="_blank">Fotograf&iacute;a</a><hr><%}%>
            <% if (television) { %><a href="anuncio.jsp" target="_blank">Televisi&oacute;n</a><hr><%}%>
            <% if (comic) { %><a href="anuncio.jsp" target="_blank">C&oacute;mic</a><hr><%}%>
            <% if (arquitectura) { %><a href="anuncio.jsp" target="_blank">Arquitectura</a><hr><%}%>
            <% if (escultura) { %><a href="anuncio.jsp" target="_blank">Escultura</a><hr><%}%>
        </fieldset>
        <%
            }
        %>
    </body>
</html>
