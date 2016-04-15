<%-- 
    Document   : perfil
    Created on : 04-nov-2015, 9:08:06
    Author     : andrew

    Página que nos muestra el perfil y en la que se podrán
    modificar los datos.
--%>
<% int k = 0; %>
<%@page import="datos.Feed"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="object" class="datos.Perfil" scope="session"/>
<jsp:useBean id="lista_feeds" class="java.util.ArrayList" scope="session"></jsp:useBean>
    <!DOCTYPE html>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <link rel="stylesheet" type="text/css" href="design/design_small.css" media="all and (min-width: 240px) and (max-width: 640px)">
            <link rel="stylesheet" type="text/css" href="design/design.css" media="all and (min-width: 640px)">
            <link rel="icon" type="image/png" href="images/favicon.png">
            <script tye="text/javascript" src="javascript/image-window.js"></script>
            <script type="text/javascript" src="javascript/provinces.js"></script>
            <title>FriendLeaf - Mi Perfil</title>
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
                <a href="PerfilServlet" id="selected">Perfil</a>
                <a href="ayuda.jsp">Ayuda</a>
                <a href="DestruirSesionServlet">Cerrar Sesi&oacute;n</a>
            </nav>
            <div id="cuerpo">
                <article>
                <% if (!object.isModificar()) {%> <h1>Perfil</h1>
                <fieldset>
                    <legend align="center">Foto</legend>
                    <img onclick="swipe(<%=k%>)" class="imagen" src="<%=object.getPhoto()%>" alt="Foto de <%=object.getUser()%>" width="320" height="320"><br>
                    <% k++;%>
                    <a href="FotosServlet">Modificar Foto de Perfil</a>
                </fieldset>
                <fieldset>
                    <legend align="center">Datos</legend>
                    <p>Usuario: <%= object.getUser()%></p>
                    <p>Nombre: <%= object.getName()%></p>
                    <p>Apellidos: <%= object.getSurname()%></p>
                    <p>DNI/NIE/Pasaporte: <%= object.getDni()%></p>
                    <p>Contrase&ntilde;a: <%= object.getPassword()%></p>
                    <p>Direcci&oacute;n: <%= object.getAddress()%></p>
                    <p>Correo Electr&oacute;nico: <%= object.getEmail()%></p>
                    <p>Tel&eacute;fono: <%= object.getTelephone()%></p>
                    <p>P&aacute;gina Web: <%= object.getWebpage()%></p>
                    <p>Pa&iacute;s: <%= object.getCountry()%></p>
                    <a href="Modificable">Modificar Cuenta</a><br><br>
                    <a href="EliminarCuentaServlet" class="confirmar">Dar de Baja la Cuenta</a><br>
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
                                }
                            %>
                    </fieldset>
                    <%
                            }
                        }
                    %>
                </fieldset>
                <% } else {%>
                <h1>Modificar Perfil:</h1>
                <form name="modificarperfil" action="ModificarCuentaServlet" method="post">
                    <fieldset>
                        <legend align="center">Datos Personales</legend>
                        <label for="name">Nombre: <span class="rojo">*</span></label><input class="cajatexto" type="text" id="name" name="name" value="<%= object.getName()%>" required title="Introduzca un Nombre"><br>
                        <label for="surname">Apellidos: <span class="rojo">*</span></label><input class="cajatexto" type="text" id="surname" name="surname" value="<%= object.getSurname()%>" required title="Introduzca tus Apellidos"><br>
                        <select name="dnionie">
                            <option value="dni">DNI</option>
                            <option value="nie">NIE</option>
                            <option value="pasaporte">Pasaporte</option>
                        </select><span class="rojo">*</span>
                        <input class="cajatexto" type="text" id="dni" name="dni" value="<%= object.getDni()%>" required title="Introduzca si DNI/NIE/Pasaporte"><br>
                        <label for="password">Contrase&ntilde;a: <span class="rojo">*</span></label><input class="cajatexto" type="password" id="password" name="password" value="<%= object.getStringPassword()%>" required title="Introduzca la Contraseña"><br>
                        <label for="passwordr">Repite Contrase&ntilde;a: <span class="rojo">*</span></label><input class="cajatexto" type="password" id="passwordr" name="passwordr" required title="Repita la Contraseña"><br>
                        <label for="address">Direcci&oacute;n: </label><input class="cajatexto" type="text" id="address" name="address" value="<%= object.getAddress().substring(0, object.getAddress().length() - 2)%>"><br>
                        <div id="stateSelect"><label for="province">Provincia:</label><select name="province" id="province" onchange="document.getElementById(\'stateValue\').innerHTML = this.value;">
                                <option value="AA">Álava</option>
                                <option value="AL">Albacete</option>
                                <option value="AI">Alicante/Alacant</option>
                                <option value="AM">Almería</option>
                                <option value="AS">Asturias</option>
                                <option value="AV">Ávila</option>
                                <option value="BA">Badajoz</option>
                                <option value="BC">Barcelona</option>
                                <option value="BU">Burgos</option>
                                <option value="CC">Cáceres</option>
                                <option value="CD">Cádiz</option>
                                <option value="CN">Cantabria</option>
                                <option value="CS">Castellón/Castelló</option>
                                <option value="CE">Ceuta</option>
                                <option value="CU">Ciudad Real</option>
                                <option value="CB">Córdoba</option>
                                <option value="CA">Cuenca</option>
                                <option value="GI">Girona</option>
                                <option value="LP">Las Palmas</option>
                                <option value="GR">Granada</option>
                                <option value="GU">Guadalajara</option>
                                <option value="GI">Guipúzcoa</option>
                                <option value="HU">Huelva</option>
                                <option value="HE">Huesca</option>
                                <option value="IB">Illes Balears</option>
                                <option value="JA">Jaén</option>
                                <option value="AC">A Coruña</option>
                                <option value="LR">La Rioja</option>
                                <option value="LE">León</option>
                                <option value="LL">Lleida</option>
                                <option value="LU">Lugo</option>
                                <option value="MD">Madrid</option>
                                <option value="ML">Málaga</option>
                                <option value="ME">Melilla</option>
                                <option value="MU" selected>Murcia</option>
                                <option value="NA">Navarra</option>
                                <option value="OU">Ourense</option>
                                <option value="PA">Palencia</option>
                                <option value="PO">Pontevedra</option>
                                <option value="SA">Salamanca</option>
                                <option value="SE">Segovia</option>
                                <option value="SV">Sevilla</option>
                                <option value="SO">Soria</option>
                                <option value="TA">Tarragona</option>
                                <option value="ST">Santa Cruz de Tenerife</option>
                                <option value="TE">Teruel</option>
                                <option value="TO">Toledo</option>
                                <option value="VA">Valencia/Valéncia</option>
                                <option value="VL">Valladolid</option>
                                <option value="VI">Vizcaya</option>
                                <option value="ZM">Zamora</option>
                                <option value="ZA">Zaragoza</option>
                            </select></div>
                        <label for="email">Correo Electr&oacute;nico: </label><input class="cajatexto" type="text" id="email" name="email" value="<%= object.getEmail()%>" pattern="^[a-z|\d]*[\.|_|-]?[a-z|\d]+@[a-z|\.]+\.(com|es|co\.uk|fr|de)$"><br>
                        <label for="telephone">Tel&eacute;fono: </label><input class="cajatexto" type="text" id="telephone" name="telephone" value="<%= object.getTelephone()%>"><br>
                        <label for="webpage">P&aacute;gina Web: </label><input class="cajatexto" type="text" id="webpage" name="webpage" value="<%= object.getWebpage()%>"><br>
                        <label for="country">Pa&iacute;s: <span class="rojo">*</span></label><select id="country" name="country" onChange="printStateMenu(this.value);">
                            <option value="AF">Afganistán</option>
                            <option value="AL">Albania</option>
                            <option value="DE">Alemania</option>
                            <option value="AD">Andorra</option>
                            <option value="AO">Angola</option>
                            <option value="AI">Anguilla</option>
                            <option value="AQ">Antártida</option>
                            <option value="AG">Antigua y Barbuda</option>
                            <option value="AN">Antillas Holandesas</option>
                            <option value="SA">Arabia Saudí</option>
                            <option value="DZ">Argelia</option>
                            <option value="AR">Argentina</option>
                            <option value="AM">Armenia</option>
                            <option value="AW">Aruba</option>
                            <option value="AU">Australia</option>
                            <option value="AT">Austria</option>
                            <option value="AZ">Azerbaiyán</option>
                            <option value="BS">Bahamas</option>
                            <option value="BH">Bahrein</option>
                            <option value="BD">Bangladesh</option>
                            <option value="BB">Barbados</option>
                            <option value="BE">Bélgica</option>
                            <option value="BZ">Belice</option>
                            <option value="BJ">Benin</option>
                            <option value="BM">Bermudas</option>
                            <option value="BY">Bielorrusia</option>
                            <option value="MM">Birmania</option>
                            <option value="BO">Bolivia</option>
                            <option value="BA">Bosnia y Herzegovina</option>
                            <option value="BW">Botswana</option>
                            <option value="BR">Brasil</option>
                            <option value="BN">Brunei</option>
                            <option value="BG">Bulgaria</option>
                            <option value="BF">Burkina Faso</option>
                            <option value="BI">Burundi</option>
                            <option value="BT">Bután</option>
                            <option value="CV">Cabo Verde</option>
                            <option value="KH">Camboya</option>
                            <option value="CM">Camerún</option>
                            <option value="CA">Canadá</option>
                            <option value="TD">Chad</option>
                            <option value="CL">Chile</option>
                            <option value="CN">China</option>
                            <option value="CY">Chipre</option>
                            <option value="VA">Ciudad del Vaticano (Santa Sede)</option>
                            <option value="CO">Colombia</option>
                            <option value="KM">Comores</option>
                            <option value="CG">Congo</option>
                            <option value="CD">Congo, República Democrática del</option>
                            <option value="KR">Corea</option>
                            <option value="KP">Corea del Norte</option>
                            <option value="CI">Costa de Marfíl</option>
                            <option value="CR">Costa Rica</option>
                            <option value="HR">Croacia (Hrvatska)</option>
                            <option value="CU">Cuba</option>
                            <option value="DK">Dinamarca</option>
                            <option value="DJ">Djibouti</option>
                            <option value="DM">Dominica</option>
                            <option value="EC">Ecuador</option>
                            <option value="EG">Egipto</option>
                            <option value="SV">El Salvador</option>
                            <option value="AE">Emiratos Árabes Unidos</option>
                            <option value="ER">Eritrea</option>
                            <option value="SI">Eslovenia</option>
                            <option value="ES" selected>España</option>
                            <option value="US">Estados Unidos</option>
                            <option value="EE">Estonia</option>
                            <option value="ET">Etiopía</option>
                            <option value="FJ">Fiji</option>
                            <option value="PH">Filipinas</option>
                            <option value="FI">Finlandia</option>
                            <option value="FR">Francia</option>
                            <option value="GA">Gabón</option>
                            <option value="GM">Gambia</option>
                            <option value="GE">Georgia</option>
                            <option value="GH">Ghana</option>
                            <option value="GI">Gibraltar</option>
                            <option value="GD">Granada</option>
                            <option value="GR">Grecia</option>
                            <option value="GL">Groenlandia</option>
                            <option value="GP">Guadalupe</option>
                            <option value="GU">Guam</option>
                            <option value="GT">Guatemala</option>
                            <option value="GY">Guayana</option>
                            <option value="GF">Guayana Francesa</option>
                            <option value="GN">Guinea</option>
                            <option value="GQ">Guinea Ecuatorial</option>
                            <option value="GW">Guinea-Bissau</option>
                            <option value="HT">Haití</option>
                            <option value="HN">Honduras</option>
                            <option value="HU">Hungría</option>
                            <option value="IN">India</option>
                            <option value="ID">Indonesia</option>
                            <option value="IQ">Irak</option>
                            <option value="IR">Irán</option>
                            <option value="IE">Irlanda</option>
                            <option value="BV">Isla Bouvet</option>
                            <option value="CX">Isla de Christmas</option>
                            <option value="IS">Islandia</option>
                            <option value="KY">Islas Caimán</option>
                            <option value="CK">Islas Cook</option>
                            <option value="CC">Islas de Cocos o Keeling</option>
                            <option value="FO">Islas Faroe</option>
                            <option value="HM">Islas Heard y McDonald</option>
                            <option value="FK">Islas Malvinas</option>
                            <option value="MP">Islas Marianas del Norte</option>
                            <option value="MH">Islas Marshall</option>
                            <option value="UM">Islas menores de Estados Unidos</option>
                            <option value="PW">Islas Palau</option>
                            <option value="SB">Islas Salomón</option>
                            <option value="SJ">Islas Svalbard y Jan Mayen</option>
                            <option value="TK">Islas Tokelau</option>
                            <option value="TC">Islas Turks y Caicos</option>
                            <option value="VI">Islas Vírgenes (EEUU)</option>
                            <option value="VG">Islas Vírgenes (Reino Unido)</option>
                            <option value="WF">Islas Wallis y Futuna</option>
                            <option value="IL">Israel</option>
                            <option value="IT">Italia</option>
                            <option value="JM">Jamaica</option>
                            <option value="JP">Japón</option>
                            <option value="JO">Jordania</option>
                            <option value="KZ">Kazajistán</option>
                            <option value="KE">Kenia</option>
                            <option value="KG">Kirguizistán</option>
                            <option value="KI">Kiribati</option>
                            <option value="KW">Kuwait</option>
                            <option value="LA">Laos</option>
                            <option value="LS">Lesotho</option>
                            <option value="LV">Letonia</option>
                            <option value="LB">Líbano</option>
                            <option value="LR">Liberia</option>
                            <option value="LY">Libia</option>
                            <option value="LI">Liechtenstein</option>
                            <option value="LT">Lituania</option>
                            <option value="LU">Luxemburgo</option>
                            <option value="MK">Macedonia, Ex-República Yugoslava de</option>
                            <option value="MG">Madagascar</option>
                            <option value="MY">Malasia</option>
                            <option value="MW">Malawi</option>
                            <option value="MV">Maldivas</option>
                            <option value="ML">Malí</option>
                            <option value="MT">Malta</option>
                            <option value="MA">Marruecos</option>
                            <option value="MQ">Martinica</option>
                            <option value="MU">Mauricio</option>
                            <option value="MR">Mauritania</option>
                            <option value="YT">Mayotte</option>
                            <option value="MX">México</option>
                            <option value="FM">Micronesia</option>
                            <option value="MD">Moldavia</option>
                            <option value="MC">Mónaco</option>
                            <option value="MN">Mongolia</option>
                            <option value="MS">Montserrat</option>
                            <option value="MZ">Mozambique</option>
                            <option value="NA">Namibia</option>
                            <option value="NR">Nauru</option>
                            <option value="NP">Nepal</option>
                            <option value="NI">Nicaragua</option>
                            <option value="NE">Níger</option>
                            <option value="NG">Nigeria</option>
                            <option value="NU">Niue</option>
                            <option value="NF">Norfolk</option>
                            <option value="NO">Noruega</option>
                            <option value="NC">Nueva Caledonia</option>
                            <option value="NZ">Nueva Zelanda</option>
                            <option value="OM">Omán</option>
                            <option value="NL">Países Bajos</option>
                            <option value="PA">Panamá</option>
                            <option value="PG">Papúa Nueva Guinea</option>
                            <option value="PK">Paquistán</option>
                            <option value="PY">Paraguay</option>
                            <option value="PE">Perú</option>
                            <option value="PN">Pitcairn</option>
                            <option value="PF">Polinesia Francesa</option>
                            <option value="PL">Polonia</option>
                            <option value="PT">Portugal</option>
                            <option value="PR">Puerto Rico</option>
                            <option value="QA">Qatar</option>
                            <option value="UK">Reino Unido</option>
                            <option value="CF">República Centroafricana</option>
                            <option value="CZ">República Checa</option>
                            <option value="ZA">República de Sudáfrica</option>
                            <option value="DO">República Dominicana</option>
                            <option value="SK">República Eslovaca</option>
                            <option value="RE">Reunión</option>
                            <option value="RW">Ruanda</option>
                            <option value="RO">Rumania</option>
                            <option value="RU">Rusia</option>
                            <option value="EH">Sahara Occidental</option>
                            <option value="KN">Saint Kitts y Nevis</option>
                            <option value="WS">Samoa</option>
                            <option value="AS">Samoa Americana</option>
                            <option value="SM">San Marino</option>
                            <option value="VC">San Vicente y Granadinas</option>
                            <option value="SH">Santa Helena</option>
                            <option value="LC">Santa Lucía</option>
                            <option value="ST">Santo Tomé y Príncipe</option>
                            <option value="SN">Senegal</option>
                            <option value="SC">Seychelles</option>
                            <option value="SL">Sierra Leona</option>
                            <option value="SG">Singapur</option>
                            <option value="SY">Siria</option>
                            <option value="SO">Somalia</option>
                            <option value="LK">Sri Lanka</option>
                            <option value="PM">St Pierre y Miquelon</option>
                            <option value="SZ">Suazilandia</option>
                            <option value="SD">Sudán</option>
                            <option value="SE">Suecia</option>
                            <option value="CH">Suiza</option>
                            <option value="SR">Surinam</option>
                            <option value="TH">Tailandia</option>
                            <option value="TW">Taiwán</option>
                            <option value="TZ">Tanzania</option>
                            <option value="TJ">Tayikistán</option>
                            <option value="TF">Territorios franceses del Sur</option>
                            <option value="TP">Timor Oriental</option>
                            <option value="TG">Togo</option>
                            <option value="TO">Tonga</option>
                            <option value="TT">Trinidad y Tobago</option>
                            <option value="TN">Túnez</option>
                            <option value="TM">Turkmenistán</option>
                            <option value="TR">Turquía</option>
                            <option value="TV">Tuvalu</option>
                            <option value="UA">Ucrania</option>
                            <option value="UG">Uganda</option>
                            <option value="UY">Uruguay</option>
                            <option value="UZ">Uzbekistán</option>
                            <option value="VU">Vanuatu</option>
                            <option value="VE">Venezuela</option>
                            <option value="VN">Vietnam</option>
                            <option value="YE">Yemen</option>
                            <option value="YU">Yugoslavia</option>
                            <option value="ZM">Zambia</option>
                            <option value="ZW">Zimbabue</option>
                        </select><br>
                    </fieldset>
                    <fieldset>
                        <legend align="center">Preguntas Secretas</legend>
                        <label for="answer1"><%= object.getQuestion1()%></label><br>
                        <span class="rojo">*</span><input class="cajatexto" type="text" id="answer1" name="answer1" required title="Introduce la Respuesta"><br>
                        <label for="answer2"><%= object.getQuestion2()%></label><br>
                        <span class="rojo">*</span><input class="cajatexto" type="text" id="answer2" name="answer2" required title="Introduce la Respuesta"><br>
                        <input id="boton" type="submit" value="Modificar">
                        <input id="botonreset" type="reset" value="Reiniciar Datos">
                        <a href="PerfilServlet">Volver</a>
                    </fieldset>
                </form>    

                <% } %>
                <p class="rojo"><% if (session.getAttribute("error") != null) {%><%= session.getAttribute("error")%><% }%></p>
            </article>
            <aside>
                <iframe src="ChatServlet"></iframe>
            </aside>
        </div>
        
        <script type="text/javascript" src="javascript/confirmar_baja.js"></script>
        <script type="text/javascript" src="javascript/submitevent.js"></script>
        <script type="text/javascript" src="javascript/diferencia_fechas.js"></script>
        <script type="text/javascript" src="javascript/emoticonos-perfil.js"></script>
    </body>
</html>
