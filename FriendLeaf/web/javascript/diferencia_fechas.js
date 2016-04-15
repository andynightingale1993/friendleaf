//Esta script cambia las fechas por valores de tiempo (segundos, minutos, etc).
    var fechas = document.getElementsByClassName("fecha");
    for (var i = 0; i < fechas.length; i++) {
        var t = fechas[i].innerHTML.split(/[- :]/);
        var fecha_de_post = new Date(t[0], t[1]-1, t[2], t[3], t[4], t[5]);
        var fecha_actual = Date.now();

        var diferencia = fecha_actual - fecha_de_post;
        
        if (diferencia > 51536000000) {
            fechas[i].innerHTML = "hace " + parseInt(diferencia / 51536000000) + " años.";
        }
        else if (diferencia > 2419200000) {
            fechas[i].innerHTML = "hace " + parseInt(diferencia / 2419200000) + " meses.";
        }
        else if (diferencia > 604800000) {
            fechas[i].innerHTML = "hace " + parseInt(diferencia / 604800000) + " semanas.";
        }
        else if (diferencia > 86400000) {
            fechas[i].innerHTML = "hace " + parseInt(diferencia / 86400000) + " días.";
        }
        else if (diferencia > 3600000) {
            fechas[i].innerHTML = "hace " + parseInt(diferencia / 3600000) + "  horas.";
        }
        else if (diferencia > 60000) {
            fechas[i].innerHTML = "hace " + parseInt(diferencia / 60000) + "  minutos.";
        }
        else if (diferencia > 1000) {
            fechas[i].innerHTML = "hace " + parseInt(diferencia / 1000) + "  segundos.";
        } else {
            fechas[i].innerHTML = "hace pocos segundos.";
        }
       
    }
