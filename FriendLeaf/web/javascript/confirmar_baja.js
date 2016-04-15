//Obtenemos los documentos de clase "confirmar"
var elems = document.getElementsByClassName('confirmar');
//Definimos la función de evento para sacar esta ventana.
var confirmIt = function (e) {
    if (!confirm('¿Seguro que quieres dar de baja la Cuenta?'))
        e.preventDefault();
};
//Y a todos los elementos de esa clase les agregamos el evento.
for (var i = 0, l = elems.length; i < l; i++) {
    elems[i].addEventListener('click', confirmIt, false);
}