//Este script sustituye los textos de emoticonos por imágenes.
var elemento = document.getElementById("posts");
var parrafos = elemento.getElementsByTagName("p");

for (var i = 0; i < parrafos.length; i++) {
    //Sonrisa
    while (parrafos[i].innerHTML.indexOf(":)") !== -1) {
        parrafos[i].innerHTML = parrafos[i].innerHTML.replace(":)", "<img src='smilies/smile.png' alt='smile'>");
    }
    
    //Tristeza
    while (parrafos[i].innerHTML.indexOf(":(") !== -1) {
        parrafos[i].innerHTML = parrafos[i].innerHTML.replace(":(", "<img src='smilies/sad.png' alt='sad'>");
    }
    
    //Slant
    while (parrafos[i].innerHTML.indexOf(":/") !== -1) {
        parrafos[i].innerHTML = parrafos[i].innerHTML.replace(":/", "<img src='smilies/slant.png' alt='slant'>");
    }
    
    //Confuso
    while (parrafos[i].innerHTML.indexOf(":S") !== -1) {
        parrafos[i].innerHTML = parrafos[i].innerHTML.replace(":S", "<img src='smilies/confused.png' alt='confused'>");
    }
    
    //Muy feliz
    while (parrafos[i].innerHTML.indexOf(":D") !== -1) {
        parrafos[i].innerHTML = parrafos[i].innerHTML.replace(":D", "<img src='smilies/very-happy.png' alt='very happy'>");
    }
    
    //Lengua
    while (parrafos[i].innerHTML.indexOf(":P") !== -1) {
        parrafos[i].innerHTML = parrafos[i].innerHTML.replace(":P", "<img src='smilies/silly.png' alt='silly'>");
    }
    
    //Mudo
    while (parrafos[i].innerHTML.indexOf(":|") !== -1) {
        parrafos[i].innerHTML = parrafos[i].innerHTML.replace(":|", "<img src='smilies/mute.png' alt='mute'>");
    }
    
    //Lágrima
    while (parrafos[i].innerHTML.indexOf(":*(") !== -1) {
        parrafos[i].innerHTML = parrafos[i].innerHTML.replace(":*(", "<img src='smilies/tear.png' alt='tear'>");
    }
    
    //Sorpresa
    while (parrafos[i].innerHTML.indexOf(":O") !== -1) {
        parrafos[i].innerHTML = parrafos[i].innerHTML.replace(":O", "<img src='smilies/surprise.png' alt='surprise'>");
    }
    
    //Beso
    while (parrafos[i].innerHTML.indexOf("muak") !== -1) {
        parrafos[i].innerHTML = parrafos[i].innerHTML.replace("muak", "<img src='smilies/kiss.png' alt='kiss'>");
    }
    
    //Amor
    while (parrafos[i].innerHTML.indexOf("(L)") !== -1) {
        parrafos[i].innerHTML = parrafos[i].innerHTML.replace("(L)", "<img src='smilies/love.png' alt='love'>");
    }
    
    //Corazón
    while (parrafos[i].innerHTML.indexOf("corazon") !== -1) {
        parrafos[i].innerHTML = parrafos[i].innerHTML.replace("corazon", "<img src='smilies/heart.png' alt='heart'>");
    }
    
    //Guiño
    while (parrafos[i].innerHTML.indexOf(";)") !== -1) {
        parrafos[i].innerHTML = parrafos[i].innerHTML.replace(";)", "<img src='smilies/wink.png' alt='wink'>");
    }
    
    //Enfadado
    while (parrafos[i].innerHTML.indexOf(":@") !== -1) {
        parrafos[i].innerHTML = parrafos[i].innerHTML.replace(":@", "<img src='smilies/angry.png' alt='angry'>");
    }
}