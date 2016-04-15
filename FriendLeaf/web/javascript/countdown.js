var num = 5;
var min = 0;

function incrementoNumber() {
    num--;

    // si el máximo no es alcanzado ejecutar otro setTimeout
    document.getElementById("mensaje").innerHTML = num;
    if (num > min) {
        setTimeout(incrementoNumber, 1000);
    } else {
        window.location = document.getElementById("link").innerHTML;
    }
}

setTimeout(incrementoNumber, 1000);
