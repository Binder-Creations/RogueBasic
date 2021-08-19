setInterval(function() {
    var p = document.querySelector("#counter");
    var count = p.textContent * 1 - 1;
    p.textContent = count;
    if (count <= 0) {
        window.location.replace("/login");
    }
}, 1000);