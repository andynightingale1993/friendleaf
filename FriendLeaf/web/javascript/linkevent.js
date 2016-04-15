//Este documento se encarga de modificar los enlaces para que cambien de
//aspecto al pulsarles.
var links = document.getElementsByTagName("a");

for (var i = 0; i < links.length; i++) {
    links[i].onmousedown = function () {
        mouseDown(i);
    };
    links[i].onmouseup = function () {
        mouseUp(i);
    };
    links[i].onmouseover = function () {
        mouseOver(i);
    };
    links[i].onmouseout = function () {
        mouseOut(i);
    };

}


function mouseDown(i) {
    var links = document.getElementsByTagName("a");
    links[i].style = "text-decoration: none;" +
            "color: white;" +
            "border-width: 3px;" +
            "border-style: inset;" +
            "border-color: darkblue;" +
            "border-radius: 7px;" +
            "background-color: blue;" +
            "padding: 2px;" +
            "margin: 1px;" +
            "font-size: 14px;";
}

function mouseUp(i) {
    var links = document.getElementsByTagName("a");
    links[i].style = "text-decoration: none;" +
            "color: white;" +
            "border-width: 3px;" +
            "border-style: outset;" +
            "border-color: darkblue;" +
            "border-radius: 7px;" +
            "background-color: blue;" +
            "padding: 2px;" +
            "margin: 1px;" +
            "font-size: 14px;";
}

function mouseOver(i) {
    var links = document.getElementsByTagName("a");
    links[i].style = "text-decoration: none;" +
            "color: white;" +
            "border-width: 3px;" +
            "border-style: outset;" +
            "border-color: darkblue;" +
            "border-radius: 7px;" +
            "padding: 2px;" +
            "margin: 1px;" +
            "font-size: 14px;" +
            "background-color: blue;" +
            "cursor: pointer;";
}

function mouseOut(i) {
    var links = document.getElementsByTagName("a");
    links[i].style = "text-decoration: none;" +
            "color: white;" +
            "border-width: 3px;" +
            "border-style: outset;" +
            "border-color: darkblue;" +
            "border-radius: 7px;" +
            "background-color: darkgreen;" +
            "padding: 2px;" +
            "margin: 1px;" +
            "font-size: 14px;";
}
