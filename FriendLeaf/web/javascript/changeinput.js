function cambiarInput(array) {
    //Obtenemos el checkbox y el input.
    var tu_foto = document.getElementById("tu_foto");
    var photo = document.getElementById("photo");
    var contenedor_photo = document.getElementById("contenedor_photo");
    var insertar = document.getElementById("insertar");
    
    //Si nos ha chekeado el checkbox entonces hay que convertir el input
    //a un text.
    if (tu_foto.checked) {
        var contenido_html = "<label for='photo'>Imagen: </label><select id='photo' name='photo'><option value=''>Seleccione una de tus fotos...</option>";
        
        for (var i = 0; i < array.length; i++) {
            contenido_html += "<option value='" + array[i] + "'>" + array[i] + "</option>"; 
        }
        contenido_html += "</select>";
        contenedor_photo.innerHTML = contenido_html;
        insertar.setAttribute("value", "insertar_sin_file");
    //En otro caso le ponemos un tipo file.
    } else {
        contenedor_photo.innerHTML = "<label for='photo'>Imagen: </label><input type='file' id='photo' name='photo' accept='image/*'>";
        insertar.setAttribute("value", "insertar");
    }
}


