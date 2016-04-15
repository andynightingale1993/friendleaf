//Esta script se encarga de abrir una ventana con una imagen cuando le hacemos click.
function swipe(numero) {
    var w = 400;
    var h = 400;
    var left = (screen.availWidth / 2) - (w / 2);
    var top = (screen.availHeight / 2) - (h / 2);
    var largeImage = document.getElementsByClassName('imagen');
    var url = largeImage[numero].getAttribute('src');
    window.open(url, 'Image', 'resizable=1, width=' + w + ', height=' + h + ', top=' + top + ', left=' + left);
}
