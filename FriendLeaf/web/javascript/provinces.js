//Este documento se encarga de modificar la lista de provincias según
//el país que tengamos seleccionado.
function printStateMenu(country) {
var province = '';
        if (country == 'US') {
province = '<label for="province">Provincia:</label><select name="province" id="province" onchange="document.getElementById(\'stateValue\').innerHTML = this.value;">' +
        '<option value="AK">Alaska</option>' +
        '<option value="AL">Alabama</option>' +
        '<option value="AR">Arkansas</option>' +
        '<option value="AZ">Arizona</option>' +
        '<option value="CA">California</option>' +
        '<option value="CO">Colorado</option>' +
        '<option value="CT">Connecticut</option>' +
        '<option value="DC">District of Columbia</option>' +
        '<option value="DE">Delaware</option>' +
        '<option value="FL">Florida</option>' +
        '<option value="GA">Georgia</option>' +
        '<option value="HI">Hawaii</option>' +
        '<option value="IA">Iowa</option>' +
        '<option value="ID">Idaho</option>' +
        '<option value="IL">Illinois</option>' +
        '<option value="IN">Indiana</option>' +
        '<option value="KS">Kansas</option>' +
        '<option value="KY">Kentucky</option>' +
        '<option value="LA">Louisiana</option>' +
        '<option value="MA">Massachusetts</option>' +
        '<option value="MD">Maryland</option>' +
        '<option value="ME">Maine</option>' +
        '<option value="MI">Michigan</option>' +
        '<option value="MN">Minnesota</option>' +
        '<option value="MO">Missouri</option>' +
        '<option value="MS">Mississippi</option>' +
        '<option value="MT">Montana</option>' +
        '<option value="NC">North Carolina</option>' +
        '<option value="ND">North Dakota</option>' +
        '<option value="NE">Nebraska</option>' +
        '<option value="NH">New Hampshire</option>' +
        '<option value="NJ">New Jersey</option>' +
        '<option value="NM">New Mexico</option>' +
        '<option value="NV">Nevada</option>' +
        '<option value="NY">New York</option>' +
        '<option value="OH">Ohio</option>' +
        '<option value="OK">Oklahoma</option>' +
        '<option value="OR">Oregon</option>' +
        '<option value="PA">Pennsylvania</option>' +
        '<option value="RI">Rhode Island</option>' +
        '<option value="SC">South Carolina</option>' +
        '<option value="SD">South Dakota</option>' +
        '<option value="TN">Tennessee</option>' +
        '<option value="TX">Texas</option>' +
        '<option value="UT">Utah</option>' +
        '<option value="VA">Virginia</option>' +
        '<option value="VT">Vermont</option>' +
        '<option value="WA">Washington</option>' +
        '<option value="WI">Wisconsin</option>' +
        '<option value="WV">West Virginia</option>' +
        '<option value="WY">Wyoming</option>' +
        '</select>';
}
else if (country == 'CA') {
province = '<label for="province">Provincia:</label><select name="province" id="province" onchange="document.getElementById(\'stateValue\').innerHTML = this.value;">' +
        '<option value="AB">Alberta</option>' +
        '<option value="BC">British Columbia</option>' +
        '<option value="MB">Manitoba</option>' +
        '<option value="NB">New Brunswick</option>' +
        '<option value="NL">Newfoundland and Labrador</option>' +
        '<option value="NT">Northwest Territories</option>' +
        '<option value="NS">Nova Scotia</option>' +
        '<option value="NU">Nunavut</option>' +
        '<option value="ON">Ontario</option>' +
        '<option value="PE">Prince Edward Island</option>' +
        '<option value="QC">Quebec</option>' +
        '<option value="SK">Saskatchewan</option>' +
        '<option value="YT">Yukon</option>' +
        '</select>';
}
else if (country == 'ES') {
province = '<label for="province">Provincia:</label><select name="province" id="province" onchange="document.getElementById(\'stateValue\').innerHTML = this.value;">' +
        '<option value="AA">Álava</option>' +
        '<option value="AL">Albacete</option>' +
        '<option value="AI">Alicante/Alacant</option>' +
        '<option value="AM">Almería</option>' +
        '<option value="AS">Asturias</option>' +
        '<option value="AV">Ávila</option>' +
        '<option value="BA">Badajoz</option>' +
        '<option value="BC">Barcelona</option>' +
        '<option value="BU">Burgos</option>' +
        '<option value="CC">Cáceres</option>' +
        '<option value="CD">Cádiz</option>' +
        '<option value="CN">Cantabria</option>' +
        '<option value="CS">Castellón/Castelló</option>' +
        '<option value="CE">Ceuta</option>' +
        '<option value="CU">Ciudad Real</option>' +
        '<option value="CB">Córdoba</option>' +
        '<option value="CA">Cuenca</option>' +
        '<option value="GI">Girona</option>' +
        '<option value="LP">Las Palmas</option>' +
        '<option value="GR">Granada</option>' +
        '<option value="GU">Guadalajara</option>' +
        '<option value="GI">Guipúzcoa</option>' +
        '<option value="HU">Huelva</option>' +
        '<option value="HE">Huesca</option>' +
        '<option value="IB">Illes Balears</option>' +
        '<option value="JA">Jaén</option>' +
        '<option value="AC">A Coruña</option>' +
        '<option value="LR">La Rioja</option>' +
        '<option value="LE">León</option>' +
        '<option value="LL">Lleida</option>' +
        '<option value="LU">Lugo</option>' +
        '<option value="MD">Madrid</option>' +
        '<option value="ML">Málaga</option>' +
        '<option value="ME">Melilla</option>' +
        '<option value="MU">Murcia</option>' +
        '<option value="NA">Navarra</option>' +
        '<option value="OU">Ourense</option>' +
        '<option value="PA">Palencia</option>' +
        '<option value="PO">Pontevedra</option>' +
        '<option value="SA">Salamanca</option>' +
        '<option value="SE">Segovia</option>' +
        '<option value="SV">Sevilla</option>' +
        '<option value="SO">Soria</option>' +
        '<option value="TA">Tarragona</option>' +
        '<option value="ST">Santa Cruz de Tenerife</option>' +
        '<option value="TE">Teruel</option>' +
        '<option value="TO">Toledo</option>' +
        '<option value="VA">Valencia/Valéncia</option>' +
        '<option value="VL">Valladolid</option>' +
        '<option value="VI">Vizcaya</option>' +
        '<option value="ZM">Zamora</option>' +
        '<option value="ZA">Zaragoza</option>' +
        '</select>';
}
else if (country == 'UK') {
province = '<label for="province">Provincia:</label><select name="province" id="province" onchange="document.getElementById(\'stateValue\').innerHTML = this.value;">' +
        '<optgroup label="Inglaterra">'+
        '<option value="BE">Bedfordshire</option>'+
        '<option value="BK">Berkshire</option>'+
        '<option value="BR">Bristol</option>'+
        '<option value="BU">Buckinghamshire</option>'+
        '<option value="CA">Cambridgeshire</option>'+
        '<option value="CH">Cheshire</option>'+
        '<option value="CL">City of London</option>'+
        '<option value="CO">Cornwall</option>'+
        '<option value="CU">Cumbria</option>'+
        '<option value="DB">Derbyshire</option>'+
        '<option value="DE">Devon</option>'+
        '<option value="DO">Dorset</option>'+
        '<option value="DU">Durham</option>'+
        '<option value="EY">East Riding of Yorkshire</option>'+
        '<option value="ES">East Sussex</option>'+
        '<option value="EE">Essex</option>'+
        '<option value="GL">Gloucestershire</option>'+
        '<option value="GR">Greater London</option>'+
        '<option value="GM">Greater Manchester</option>'+
        '<option value="HA">Hampshire</option>'+
        '<option value="HE">Herefordshire</option>'+
        '<option value="HT">Hertfordshire</option>'+
        '<option value="IW">Isle of Wight</option>'+
        '<option value="KE"> Kent</option>'+
        '<option value="LA">Lancashire</option>'+
        '<option value="LE">Leicestershire</option>'+
        '<option value="LI">Lincolnshire</option>'+
        '<option value="ME">Merseyside</option>'+
        '<option value="NF">Norfolk</option>'+
        '<option value="NY">North Yorkshire</option>'+
        '<option value="NH">Northamptonshire</option>'+
        '<option value="NO">Northumberland</option>'+
        '<option value="NT">Nottinghamshire</option>'+
        '<option value="OX">Oxfordshire</option>'+
        '<option value="RU">Rutland</option>'+
        '<option value="SH">Shropshire</option>'+
        '<option value="SO">Somerset</option>'+
        '<option value="SY">South Yorkshire</option>'+
        '<option value="ST">Staffordshire</option>'+
        '<option value="SF">Suffolk</option>'+
        '<option value="SU">Surrey</option>'+
        '<option value="TW">Tyne and Wear</option>'+
        '<option value="WA">Warwickshire</option>'+
        '<option value="WM">West Midlands</option>'+
        '<option value="WS">West Sussex</option>'+
        '<option value="WY">West Yorkshire</option>'+
        '<option value="WI">Wiltshire</option>'+
        '<option value="WO">Worcestershire</option>'+
        '</optgroup>'+
        '<optgroup label="Gales">'+
        '<option value="AN">Anglesey</option>'+
        '<option value="BR">Brecknockshire</option>'+
        '<option value="CN">Caernarfonshire</option>'+
        '<option value="CM">Carmarthenshire</option>'+
        '<option value="CD">Cardiganshire</option>'+
        '<option value="DE">Denbighshire</option>'+
        '<option value="FL">Flintshire</option>'+
        '<option value="GL">Glamorgan</option>'+
        '<option value="ME">Merioneth</option>'+
        '<option value="MO">Monmouthshire</option>'+
        '<option value="MT">Montgomeryshire</option>'+
        '<option value="PM">Pembrokeshire</option>'+
        '<option value="RD">Radnorshire</option>'+
        '</optgroup>'+
        '<optgroup label="Escocia">'+
        '<option value="AB">Aberdeenshire</option>'+
        '<option value="AG">Angus</option>'+
        '<option value="AL">Argyllshire</option>'+
        '<option value="AY">Ayrshire</option>'+
        '<option value="BF">Banffshire</option>'+
        '<option value="BW">Berwickshire</option>'+
        '<option value="BU">Buteshire</option>'+
        '<option value="CR">Cromartyshire</option>'+
        '<option value="CI">Caithness</option>'+
        '<option value="CK">Clackmannanshire</option>'+
        '<option value="DM">Dumfriesshire</option>'+
        '<option value="DB">Dunbartonshire</option>'+
        '<option value="EL">East Lothian</option>'+
        '<option value="FF">Fife</option>'+
        '<option value="IV">Inverness - shire</option>'+
        '<option value="KD">Kincardineshire</option>'+
        '<option value="KI">Kinross</option>'+
        '<option value="KK"> Kirkcudbrightshire</option>'+
        '<option value="LK">Lanarkshire</option>'+
        '<option value="ML">Midlothian</option>'+
        '<option value="MY">Morayshire</option>'+
        '<option value="NI">Nairnshire</option>'+
        '<option value="OK">Orkney</option>'+
        '<option value="PE">Peeblesshire</option>'+
        '<option value="PT">Perthshire</option>'+
        '<option value="RE">Renfrewshire</option>'+
        '<option value="RO">Ross - shire</option>'+
        '<option value="RX">Roxburghshire</option>'+
        '<option value="SK">Selkirkshire</option>'+
        '<option value="SH">Shetland</option>'+
        '<option value="SL">Stirlingshire</option>'+
        '<option value="SU">Sutherland</option>'+
        '<option value="WL">West Lothian</option>'+
        '<option value="WG">Wigtownshire</option>'+
        '</select>';
}
else if (country == 'IE') {
province = '<label for="province">Provincia:</label><select name="province" id="province" onchange="document.getElementById(\'stateValue\').innerHTML = this.value;">' +
        '<option value="AM">Antrim</option>'+
        '<option value="AR">Armagh</option>' +
        '<option value="DO">Down</option>' +
        '<option value="FE">Fermanagh</option>' +
        '<option value="LO">Londonderry</option>' +
        '<option value="TY">Tyrone</option>' +
        '</select>';
}


        else {
        province = '<label for="province">Provincia:</label><select name="province" id="province" disabled>' +
                '<option value="">No hay provincias</option>' +
                '</select>';
        }
        document.getElementById('stateSelect').innerHTML = province;
        }