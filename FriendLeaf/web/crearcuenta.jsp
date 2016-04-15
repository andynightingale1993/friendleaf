<%-- 
    Document   : crearcuenta
    Created on : 04-nov-2015, 9:07:34
    Author     : andrew

    Página que muestra un formulario para crear
    nuestra cuenta, con las validaciones necesarias y un
    poco de JavaScript para cambiar menús según seleccionemos
    una opción u otra.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% session.setAttribute("error", "");%>
<jsp:useBean id="object" class="datos.Perfil" scope="session"></jsp:useBean>
    <!DOCTYPE html>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <link rel="stylesheet" type="text/css" href="design/design.css">
            <link rel="icon" type="image/png" href="images/favicon.png">
            <script type="text/javascript" src="javascript/provinces.js"></script>
            <title>FriendLeaf - Crear Cuenta</title>
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
                <a href="index.jsp">Inicio</a>
                <a href="crearcuenta.jsp" id="selected">Crear Cuenta</a>
            </nav>
            <h1>Crear Cuenta</h1>
            <form name="crearcuenta" action="ControladorServlet" method="post">
                <input type="hidden" name="page" value="crearcuenta.jsp">
                <fieldset>
                    <legend align="center">Datos Personales</legend>
                    <label for="name">Nombre: <span class="rojo">*</span></label><input class="cajatexto" type="text" name="name" id="name" required title="Hay que introducir un nombre" placeholder="Nombre" value="<%= object.getName()%>"><br>
                <label for="surname">Apellidos: <span class="rojo">*</span></label><input class="cajatexto" type="text" name="surname" id="surname" required title="Hay que introducir apellidos" placeholder="Apellidos" value="<%= object.getSurname()%>"><br>
                <label for="user">Usuario: <span class="rojo">*</span></label><input class="cajatexto" type="text" id="user" name="user" required title="Hay que introducir un usuario" placeholder="MiNick" value="<%= object.getUser()%>"><br>
                <select name="dnionie">
                    <option value="dni">DNI</option>
                    <option value="nie">NIE</option>
                    <option value="pasaporte">Pasaporte</option>
                </select><span class="rojo">*</span>
                <input class="cajatexto" type="text" id="dni" name="dni" required title="Hay que introducir un DNI/NIE/Pasaporte" placeholder="1234567890E" value="<%= object.getDni()%>"><br> 
                <label for="password">Contrase&ntilde;a: <span class="rojo">*</span></label><input class="cajatexto" type="password" id="password" name="password" required placeholder="Se recomienda buena seguridad" value="<%= object.getStringPassword()%>"><br>
                <label for="passwordr">Repite Contrase&ntilde;a: <span class="rojo">*</span></label><input class="cajatexto" type="password" id="passwordr" name="passwordr" required placeholder="Que coincida con la anterior" value="<%= object.getStringPassword()%>"><br>
                <label for="address1">Direcci&oacute;n L&iacute;nea 1: </label><input class="cajatexto" type="text" id="address1" name="address1" placeholder="Calle, N&uacute;mero, Pueblo" value="<%= object.getAddressline1()%>"><br>
                <label for="address2">Direcci&oacute;n L&iacute;nea 2: </label><input class="cajatexto" type="text" id="address2" name="address2" placeholder="Localidad" value="<%= object.getAddressline2()%>"><br>
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
                <label for="email">Correo Electr&oacute;nico: </label><input class="cajatexto" type="email" id="email" name="email" placeholder="ejemplo@gmail.com" value="<%= object.getEmail()%>" pattern="^[a-z|\d]*[\.|_|-]?[a-z|\d]+@[a-z|\.]+\.(com|es|co\.uk|fr|de)$"><br>
                <label for="telephone">Tel&eacute;fono: </label><select name="countrycode" id="countrycode">                    
                    <option data-countryCode="DZ" value="213">Algeria (+213)</option>
                    <option data-countryCode="AD" value="376">Andorra (+376)</option>
                    <option data-countryCode="AO" value="244">Angola (+244)</option>
                    <option data-countryCode="AI" value="1264">Anguilla (+1264)</option>
                    <option data-countryCode="AG" value="1268">Antigua &amp; Barbuda (+1268)</option>
                    <option data-countryCode="AR" value="54">Argentina (+54)</option>
                    <option data-countryCode="AM" value="374">Armenia (+374)</option>
                    <option data-countryCode="AW" value="297">Aruba (+297)</option>
                    <option data-countryCode="AU" value="61">Australia (+61)</option>
                    <option data-countryCode="AT" value="43">Austria (+43)</option>
                    <option data-countryCode="AZ" value="994">Azerbaijan (+994)</option>
                    <option data-countryCode="BS" value="1242">Bahamas (+1242)</option>
                    <option data-countryCode="BH" value="973">Bahrain (+973)</option>
                    <option data-countryCode="BD" value="880">Bangladesh (+880)</option>
                    <option data-countryCode="BB" value="1246">Barbados (+1246)</option>
                    <option data-countryCode="BY" value="375">Belarus (+375)</option>
                    <option data-countryCode="BE" value="32">B&eacute;lgica (+32)</option>
                    <option data-countryCode="BZ" value="501">Belize (+501)</option>
                    <option data-countryCode="BJ" value="229">Benin (+229)</option>
                    <option data-countryCode="BM" value="1441">Bermuda (+1441)</option>
                    <option data-countryCode="BT" value="975">Bhutan (+975)</option>
                    <option data-countryCode="BO" value="591">Bolivia (+591)</option>
                    <option data-countryCode="BA" value="387">Bosnia Herzegovina (+387)</option>
                    <option data-countryCode="BW" value="267">Botswana (+267)</option>
                    <option data-countryCode="BR" value="55">Brazil (+55)</option>
                    <option data-countryCode="BN" value="673">Brunei (+673)</option>
                    <option data-countryCode="BG" value="359">Bulgaria (+359)</option>
                    <option data-countryCode="BF" value="226">Burkina Faso (+226)</option>
                    <option data-countryCode="BI" value="257">Burundi (+257)</option>
                    <option data-countryCode="KH" value="855">Cambodia (+855)</option>
                    <option data-countryCode="CM" value="237">Cameroon (+237)</option>
                    <option data-countryCode="CA" value="1">Canad&aacute; (+1)</option>
                    <option data-countryCode="CV" value="238">Islas Cape Verde(+238)</option>
                    <option data-countryCode="KY" value="1345">Islas Caim&aacute;n (+1345)</option>
                    <option data-countryCode="CF" value="236">Rep&uacute;blica Central Americana (+236)</option>
                    <option data-countryCode="CL" value="56">Chile (+56)</option>
                    <option data-countryCode="CN" value="86">China (+86)</option>
                    <option data-countryCode="CO" value="57">Colombia (+57)</option>
                    <option data-countryCode="KM" value="269">Comoros (+269)</option>
                    <option data-countryCode="CG" value="242">Congo (+242)</option>
                    <option data-countryCode="CK" value="682">Islas Cook (+682)</option>
                    <option data-countryCode="CR" value="506">Costa Rica (+506)</option>
                    <option data-countryCode="HR" value="385">Croacia (+385)</option>
                    <option data-countryCode="CU" value="53">Cuba (+53)</option>
                    <option data-countryCode="CY" value="90392">Cyprus Norte (+90392)</option>
                    <option data-countryCode="CY" value="357">Cyprus Sur (+357)</option>
                    <option data-countryCode="CZ" value="42">Rep&uacute;ublica Checa (+42)</option>
                    <option data-countryCode="DK" value="45">Dinamarca (+45)</option>
                    <option data-countryCode="DJ" value="253">Djibouti (+253)</option>
                    <option data-countryCode="DM" value="1809">Dominica (+1809)</option>
                    <option data-countryCode="DO" value="1809">Rep&uacute;blica Dominicana (+1809)</option>
                    <option data-countryCode="EC" value="593">Ecuador (+593)</option>
                    <option data-countryCode="EG" value="20">Egipto (+20)</option>
                    <option data-countryCode="SV" value="503">El Salvador (+503)</option>
                    <option data-countryCode="GQ" value="240">Guinea Ecuatorial (+240)</option>
                    <option data-countryCode="ER" value="291">Eritrea (+291)</option>
                    <option data-countryCode="EE" value="372">Estonia (+372)</option>
                    <option data-countryCode="ET" value="251">Ethiopia (+251)</option>
                    <option data-countryCode="FK" value="500">Islas Falkland (+500)</option>
                    <option data-countryCode="FO" value="298">Islas Faroe (+298)</option>
                    <option data-countryCode="FJ" value="679">Fiji (+679)</option>
                    <option data-countryCode="FI" value="358">Finlandia (+358)</option>
                    <option data-countryCode="FR" value="33">Francia (+33)</option>
                    <option data-countryCode="GF" value="594">Guiana Francesa (+594)</option>
                    <option data-countryCode="PF" value="689">Polynesia Francesa (+689)</option>
                    <option data-countryCode="GA" value="241">Gabon (+241)</option>
                    <option data-countryCode="GM" value="220">Gambia (+220)</option>
                    <option data-countryCode="GE" value="7880">Georgia (+7880)</option>
                    <option data-countryCode="DE" value="49">Alemania (+49)</option>
                    <option data-countryCode="GH" value="233">Ghana (+233)</option>
                    <option data-countryCode="GI" value="350">Gibraltar (+350)</option>
                    <option data-countryCode="GR" value="30">Grecia (+30)</option>
                    <option data-countryCode="GL" value="299">Greenland (+299)</option>
                    <option data-countryCode="GD" value="1473">Grenada (+1473)</option>
                    <option data-countryCode="GP" value="590">Guadeloupe (+590)</option>
                    <option data-countryCode="GU" value="671">Guam (+671)</option>
                    <option data-countryCode="GT" value="502">Guatemala (+502)</option>
                    <option data-countryCode="GN" value="224">Guinea (+224)</option>
                    <option data-countryCode="GW" value="245">Guinea - Bissau (+245)</option>
                    <option data-countryCode="GY" value="592">Guyana (+592)</option>
                    <option data-countryCode="HT" value="509">Haiti (+509)</option>
                    <option data-countryCode="HN" value="504">Honduras (+504)</option>
                    <option data-countryCode="HK" value="852">Hong Kong (+852)</option>
                    <option data-countryCode="HU" value="36">Hungary (+36)</option>
                    <option data-countryCode="IS" value="354">Islandia (+354)</option>
                    <option data-countryCode="IN" value="91">India (+91)</option>
                    <option data-countryCode="ID" value="62">Indonesia (+62)</option>
                    <option data-countryCode="IR" value="98">Ir&aacute;n (+98)</option>
                    <option data-countryCode="IQ" value="964">Iraq (+964)</option>
                    <option data-countryCode="IE" value="353">Irlanda (+353)</option>
                    <option data-countryCode="IL" value="972">Israel (+972)</option>
                    <option data-countryCode="IT" value="39">Italia (+39)</option>
                    <option data-countryCode="JM" value="1876">Jamaica (+1876)</option>
                    <option data-countryCode="JP" value="81">Jap&oacute;n (+81)</option>
                    <option data-countryCode="JO" value="962">Jordan (+962)</option>
                    <option data-countryCode="KZ" value="7">Kazakhstan (+7)</option>
                    <option data-countryCode="KE" value="254">Kenya (+254)</option>
                    <option data-countryCode="KI" value="686">Kiribati (+686)</option>
                    <option data-countryCode="KP" value="850">Korea Norte (+850)</option>
                    <option data-countryCode="KR" value="82">Korea Sur (+82)</option>
                    <option data-countryCode="KW" value="965">Kuwait (+965)</option>
                    <option data-countryCode="KG" value="996">Kyrgyzstan (+996)</option>
                    <option data-countryCode="LA" value="856">Laos (+856)</option>
                    <option data-countryCode="LV" value="371">Letonia (+371)</option>
                    <option data-countryCode="LB" value="961">Lebanon (+961)</option>
                    <option data-countryCode="LS" value="266">Lesotho (+266)</option>
                    <option data-countryCode="LR" value="231">Liberia (+231)</option>
                    <option data-countryCode="LY" value="218">Libya (+218)</option>
                    <option data-countryCode="LI" value="417">Liechtenstein (+417)</option>
                    <option data-countryCode="LT" value="370">Lithuania (+370)</option>
                    <option data-countryCode="LU" value="352">Luxembourg (+352)</option>
                    <option data-countryCode="MO" value="853">Macao (+853)</option>
                    <option data-countryCode="MK" value="389">Macedonia (+389)</option>
                    <option data-countryCode="MG" value="261">Madagascar (+261)</option>
                    <option data-countryCode="MW" value="265">Malawi (+265)</option>
                    <option data-countryCode="MY" value="60">Malaysia (+60)</option>
                    <option data-countryCode="MV" value="960">Maldives (+960)</option>
                    <option data-countryCode="ML" value="223">Mali (+223)</option>
                    <option data-countryCode="MT" value="356">Malta (+356)</option>
                    <option data-countryCode="MH" value="692">Islas Marshall (+692)</option>
                    <option data-countryCode="MQ" value="596">Martinique (+596)</option>
                    <option data-countryCode="MR" value="222">Mauritania (+222)</option>
                    <option data-countryCode="YT" value="269">Mayotte (+269)</option>
                    <option data-countryCode="MX" value="52">M&eacute;xico (+52)</option>
                    <option data-countryCode="FM" value="691">Micronesia (+691)</option>
                    <option data-countryCode="MD" value="373">Moldova (+373)</option>
                    <option data-countryCode="MC" value="377">Monaco (+377)</option>
                    <option data-countryCode="MN" value="976">Mongolia (+976)</option>
                    <option data-countryCode="MS" value="1664">Montserrat (+1664)</option>
                    <option data-countryCode="MA" value="212">Morocco (+212)</option>
                    <option data-countryCode="MZ" value="258">Mozambique (+258)</option>
                    <option data-countryCode="MN" value="95">Myanmar (+95)</option>
                    <option data-countryCode="NA" value="264">Namibia (+264)</option>
                    <option data-countryCode="NR" value="674">Nauru (+674)</option>
                    <option data-countryCode="NP" value="977">Nepal (+977)</option>
                    <option data-countryCode="NL" value="31">Netherlands (+31)</option>
                    <option data-countryCode="NC" value="687">Nueva Caledonia (+687)</option>
                    <option data-countryCode="NZ" value="64">Nueva Zelanda (+64)</option>
                    <option data-countryCode="NI" value="505">Nicaragua (+505)</option>
                    <option data-countryCode="NE" value="227">Niger (+227)</option>
                    <option data-countryCode="NG" value="234">Nigeria (+234)</option>
                    <option data-countryCode="NU" value="683">Niue (+683)</option>
                    <option data-countryCode="NF" value="672">Islas Norfolk (+672)</option>
                    <option data-countryCode="NP" value="670">Northern Marianas (+670)</option>
                    <option data-countryCode="NO" value="47">Noruega (+47)</option>
                    <option data-countryCode="OM" value="968">Oman (+968)</option>
                    <option data-countryCode="PW" value="680">Palau (+680)</option>
                    <option data-countryCode="PA" value="507">Panam&aacute; (+507)</option>
                    <option data-countryCode="PG" value="675">Papua Nueva Guinea (+675)</option>
                    <option data-countryCode="PY" value="595">Paraguay (+595)</option>
                    <option data-countryCode="PE" value="51">Per&uacute; (+51)</option>
                    <option data-countryCode="PH" value="63">Filipinas (+63)</option>
                    <option data-countryCode="PL" value="48">Polanda (+48)</option>
                    <option data-countryCode="PT" value="351">Portugal (+351)</option>
                    <option data-countryCode="PR" value="1787">Puerto Rico (+1787)</option>
                    <option data-countryCode="QA" value="974">Qatar (+974)</option>
                    <option data-countryCode="RE" value="262">Reunion (+262)</option>
                    <option data-countryCode="RO" value="40">Ruman&iacute;a (+40)</option>
                    <option data-countryCode="RU" value="7">Rusia (+7)</option>
                    <option data-countryCode="RW" value="250">Rwanda (+250)</option>
                    <option data-countryCode="SM" value="378">San Marino (+378)</option>
                    <option data-countryCode="ST" value="239">Sao Tome &amp; Principe (+239)</option>
                    <option data-countryCode="SA" value="966">Arabia Saud&iacute; (+966)</option>
                    <option data-countryCode="SN" value="221">Senegal (+221)</option>
                    <option data-countryCode="CS" value="381">Serbia (+381)</option>
                    <option data-countryCode="SC" value="248">Seychelles (+248)</option>
                    <option data-countryCode="SL" value="232">Sierra Leone (+232)</option>
                    <option data-countryCode="SG" value="65">Singapur (+65)</option>
                    <option data-countryCode="SK" value="421">Rep&uacute;blica Slovakia (+421)</option>
                    <option data-countryCode="SI" value="386">Eslovenia (+386)</option>
                    <option data-countryCode="SB" value="677">Islas Solomon (+677)</option>
                    <option data-countryCode="SO" value="252">Somalia (+252)</option>
                    <option data-countryCode="ZA" value="27">&Aacute;frica Sur (+27)</option>
                    <option data-countryCode="ES" value="34" selected>Espa&ntilde;a (+34)</option>
                    <option data-countryCode="LK" value="94">Sri Lanka (+94)</option>
                    <option data-countryCode="SH" value="290">St. Helena (+290)</option>
                    <option data-countryCode="KN" value="1869">St. Kitts (+1869)</option>
                    <option data-countryCode="SC" value="1758">St. Luc&iacute;a (+1758)</option>
                    <option data-countryCode="SD" value="249">Sudan (+249)</option>
                    <option data-countryCode="SR" value="597">Suriname (+597)</option>
                    <option data-countryCode="SZ" value="268">Swaziland (+268)</option>
                    <option data-countryCode="SE" value="46">Suecia (+46)</option>
                    <option data-countryCode="CH" value="41">Suiza (+41)</option>
                    <option data-countryCode="SI" value="963">Siria (+963)</option>
                    <option data-countryCode="TW" value="886">Taiw&aacute;n (+886)</option>
                    <option data-countryCode="TJ" value="7">Tajikstan (+7)</option>
                    <option data-countryCode="TH" value="66">Tailandia (+66)</option>
                    <option data-countryCode="TG" value="228">Togo (+228)</option>
                    <option data-countryCode="TO" value="676">Tonga (+676)</option>
                    <option data-countryCode="TT" value="1868">Trinidad &amp; Tobago (+1868)</option>
                    <option data-countryCode="TN" value="216">Tunisia (+216)</option>
                    <option data-countryCode="TR" value="90">Turqu&iacute;a (+90)</option>
                    <option data-countryCode="TM" value="7">Turkmenist&aacute;n (+7)</option>
                    <option data-countryCode="TM" value="993">Turkmenist&aacute;n (+993)</option>
                    <option data-countryCode="TC" value="1649">Islas Turks &amp; Caicos (+1649)</option>
                    <option data-countryCode="TV" value="688">Tuvalu (+688)</option>
                    <option data-countryCode="UG" value="256">Uganda (+256)</option>
                    <option data-countryCode="GB" value="44">Reino Unido (+44)</option>
                    <option data-countryCode="UA" value="380">Ukrania (+380)</option>
                    <option data-countryCode="AE" value="971">United Arab Emirates (+971)</option>
                    <option data-countryCode="UY" value="598">Uruguay (+598)</option>
                    <option data-countryCode="US" value="1">EEUU (+1)</option>
                    <option data-countryCode="UZ" value="7">Uzbekistan (+7)</option>
                    <option data-countryCode="VU" value="678">Vanuatu (+678)</option>
                    <option data-countryCode="VA" value="379">Vatican City (+379)</option>
                    <option data-countryCode="VE" value="58">Venezuela (+58)</option>
                    <option data-countryCode="VN" value="84">Vietn&aacute;sm (+84)</option>
                    <option data-countryCode="VG" value="84">Virgin Islands - British (+1284)</option>
                    <option data-countryCode="VI" value="84">Virgin Islands - US (+1340)</option>
                    <option data-countryCode="WF" value="681">Wallis &amp; Futuna (+681)</option>
                    <option data-countryCode="YE" value="969">Yemen (North)(+969)</option>
                    <option data-countryCode="YE" value="967">Yemen (South)(+967)</option>
                    <option data-countryCode="ZM" value="260">Zambia (+260)</option>
                    <option data-countryCode="ZW" value="263">Zimbabwe (+263)</option>
                </select>
                <input class="cajatexto" type="text" id="telephone" name="telephone" placeholder="123 456 789" value="<%= object.getTelefono()%>"><br>
                <label for="webpage">P&aacute;gina Web: </label><input class="cajatexto" type="text" id="webpage" name="webpage" placeholder="http://miweb.com/" value="<%= object.getWebpage()%>"><br>
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
                </select>
            </fieldset>
            <fieldset>
                <legend align="center">Preguntas Secretas</legend>
                <label for="question1">Pregunta 1: </label><select id="question1" name="question1">
                    <option value="1" <% if (object.getPregunta1() == 1) { %> selected <% } %>>&iquest;Cu&aacute;l es el nombre de tu mascota favorita?</option>
                    <option value="2" <% if (object.getPregunta1() == 2) { %> selected <% } %>>&iquest;Cu&aacute;l fue tu primer tel&eacute;fono?</option>
                    <option value="3" <% if (object.getPregunta1() == 3) { %> selected <% } %>>&iquest;Cu&aacute;l es el nombre de tu madre?</option>
                    <option value="4" <% if (object.getPregunta1() == 4) { %> selected <% } %>>&iquest;Qui&eacute;n es tu mejor amigo?</option>
                    <option value="5" <% if (object.getPregunta1() == 5) { %> selected <% }%>>&iquest;Cu&aacute;l es tu g&eacute;nero favorito de pel&iacute;cula?</option>
                </select><br>
                <label for="answer1">Respuesta: </label><span class="rojo">*</span><input class="cajatexto" type="text" id="answer1" name="answer1" placeholder="Introduce tu respuesta..." required title="Introduce una Respuesta..." value="<%= object.getAnswer1()%>"><br>
                <label for="question2">Pregunta 2: </label><select id="question2" name="question2">
                    <option value="1" <% if (object.getPregunta2() == 1) { %> selected <% } %>>&iquest;Cu&aacute;l es el nombre de tu mascota favorita?</option>
                    <option value="2" <% if (object.getPregunta2() == 2) { %> selected <% } %>>&iquest;Cu&aacute;l fue tu primer tel&eacute;fono?</option>
                    <option value="3" <% if (object.getPregunta2() == 3) { %> selected <% } %>>&iquest;Cu&aacute;l es el nombre de tu madre?</option>
                    <option value="4" <% if (object.getPregunta2() == 4) { %> selected <% } %>>&iquest;Qui&eacute;n es tu mejor amigo?</option>
                    <option value="5" <% if (object.getPregunta2() == 5) { %> selected <% }%>>&iquest;Cu&aacute;l es tu g&eacute;nero favorito de pel&iacute;cula?</option>
                </select><br>    
                <label for="answer2">Respuesta: </label><span class="rojo">*</span><input class="cajatexto" type="text" id="answer2" name="answer2" placeholder="Introduce tu respuesta..." required title="Introduce una Respuesta..." value="<%= object.getAnswer2()%>"><br>
                <input type="checkbox" name="terminos" required title="Debes aceptar los términos y condiciones"> Acepto los t&eacute;rminos y condiciones.<br>
                <a class="enlace" href="download/terminos-condiciones.odt" download>Descargar documento</a><br>
            </fieldset>
            <input id="boton" type="submit" value="Crear Cuenta"><input id="botonreset" type="reset" value="Vaciar Formulario">

        </form>
        <p class="rojo"><% if (session.getAttribute("errorC") != null) {%><%= session.getAttribute("errorC")%><% }%></p>
        <script type="text/javascript" src="javascript/submitevent.js"></script>
    </body>
</html>
