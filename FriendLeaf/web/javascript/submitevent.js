//Este documento se encarga de cambiar el aspecto de los botones de enviar
//al pulsar en ellos.
document.getElementById("boton").onmousedown = function () {
    mouseDown();
};
document.getElementById("boton").onmouseup = function () {
    mouseUp();
};
document.getElementById("boton").onmouseover = function () {
    mouseOver();
};
document.getElementById("boton").onmouseout = function () {
    mouseOut();
};

document.getElementById("botonreset").onmousedown = function () {
    mouseDownReset();
};
document.getElementById("botonreset").onmouseup = function () {
    mouseUpReset();
};
document.getElementById("botonreset").onmouseover = function () {
    mouseOverReset();
};
document.getElementById("botonreset").onmouseout = function () {
    mouseOutReset();
};

function mouseDown() {
    document.getElementById("boton").style = "text-decoration: none;" +
            "color: darkgreen;" +
            "border-width: 3px;" +
            "border-style: inset;" +
            "border-color: darkgreen;" +
            "border-radius: 7px;" +
            "background-color: white;" +
            "padding: 2px;" +
            "margin: 1px;" +
            "font-size: 14px;";
}

function mouseUp() {
    document.getElementById("boton").style = "text-decoration: none;" +
            "color: darkgreen;" +
            "border-width: 3px;" +
            "border-style: outset;" +
            "border-color: darkgreen;" +
            "border-radius: 7px;" +
            "background-color: white;" +
            "padding: 2px;" +
            "margin: 1px;" +
            "font-size: 14px;";
}

function mouseOver() {
    document.getElementById("boton").style = "text-decoration: none;" +
            "color: darkgreen;" +
            "border-width: 3px;" +
            "border-style: outset;" +
            "border-color: darkgreen;" +
            "border-radius: 7px;" +
            "padding: 2px;" +
            "margin: 1px;" +
            "font-size: 14px;" +
            "background-color: white;" +
            "cursor: pointer;";
}

function mouseOut() {
    document.getElementById("boton").style = "text-decoration: none;" +
            "color: white;" +
            "border-width: 3px;" +
            "border-style: outset;" +
            "border-color: white;" +
            "border-radius: 7px;" +
            "background-color: darkgreen;" +
            "padding: 2px;" +
            "margin: 1px;" +
            "font-size: 14px;";
}

function mouseDownReset() {
    document.getElementById("botonreset").style = "text-decoration: none;" +
            "color: darkgreen;" +
            "border-width: 3px;" +
            "border-style: inset;" +
            "border-color: darkgreen;" +
            "border-radius: 7px;" +
            "background-color: white;" +
            "padding: 2px;" +
            "margin: 1px;" +
            "font-size: 14px;";
}

function mouseUpReset() {
    document.getElementById("botonreset").style = "text-decoration: none;" +
            "color: darkgreen;" +
            "border-width: 3px;" +
            "border-style: outset;" +
            "border-color: darkgreen;" +
            "border-radius: 7px;" +
            "background-color: white;" +
            "padding: 2px;" +
            "margin: 1px;" +
            "font-size: 14px;";
}

function mouseOverReset() {
    document.getElementById("botonreset").style = "text-decoration: none;" +
            "color: darkgreen;" +
            "border-width: 3px;" +
            "border-style: outset;" +
            "border-color: darkgreen;" +
            "border-radius: 7px;" +
            "padding: 2px;" +
            "margin: 1px;" +
            "font-size: 14px;" +
            "background-color: white;" +
            "cursor: pointer;";
}

function mouseOutReset() {
    document.getElementById("botonreset").style = "text-decoration: none;" +
            "color: white;" +
            "border-width: 3px;" +
            "border-style: outset;" +
            "border-color: white;" +
            "border-radius: 7px;" +
            "background-color: darkgreen;" +
            "padding: 2px;" +
            "margin: 1px;" +
            "font-size: 14px;";
}

