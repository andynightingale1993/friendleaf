var num = 3;
var min = 0;

function incrementoNumber() {
    num--;

    // si el máximo no es alcanzado ejecutar otro setTimeout
    document.getElementById("mensaje").innerHTML = num;
    if (num > min) {
        setTimeout(incrementoNumber, 1000);
    } else {
        window.close();
    }
}

setTimeout(incrementoNumber, 1000);
