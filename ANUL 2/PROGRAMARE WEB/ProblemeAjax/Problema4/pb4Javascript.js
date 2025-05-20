let ocupareaCelulelor = [];
let jucatorCurent = null;

function seteazaJucatorulCurent() {
    let p = document.getElementById("jucatorCurent");
    p.textContent = "Jucatorul curent este: " + jucatorCurent;
}

document.addEventListener("DOMContentLoaded", function () {
    if (Math.random() < 0.5)
        jucatorCurent = "computer";
    else
        jucatorCurent = "utilizator";
    seteazaJucatorulCurent();
    initializareTabel();
    if (jucatorCurent === "computer") {
        setTimeout(mutareaComputerului, 1000);
    }
});

function initializareTabel() {
    let tds = document.getElementById("tabel").getElementsByTagName("td");
    for (let i = 0; i < tds.length; i++) {
        tds[i].addEventListener("click", setareCasuta);
    }

    for (let i = 0; i < 9; i++) {
        ocupareaCelulelor[i] = null;
    }
}

function pozitieCasuta(td) {
    let tds = document.getElementById("tabel").getElementsByTagName("td");
    for (let i = 0; i < tds.length; i++)
        if (tds[i] === td) {
            return i;
        }
}

function schimbaJucator() {
    if (jucatorCurent === "computer") {
        jucatorCurent = "utilizator";
    } else {
        jucatorCurent = "computer";
    }
    seteazaJucatorulCurent();
}

function verificaDacaACastigat() {
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            let mesaj = this.responseText;
            if (mesaj === "computer a castigat" || mesaj === "utilizator a castigat") {
                alert(mesaj);
                blocheazaCasute();
            }

        }
    }
    xhttp.open("GET", "verificaDacaECastig.php?lista=" + JSON.stringify(ocupareaCelulelor), true);
    xhttp.send();
}

function blocheazaCasute() {
    let tds = document.getElementById("tabel").getElementsByTagName("td");
    for (let i = 0; i < tds.length; i++)
        tds[i].removeEventListener("click", setareCasuta);
}

function setareCasuta() {
    let poz = pozitieCasuta(this);
    ocupareaCelulelor[poz] = jucatorCurent;
    if (jucatorCurent === "computer") {
        this.textContent = "O";
    } else {
        this.textContent = "X";
    }
    verificaDacaACastigat();
    schimbaJucator();
    if (jucatorCurent === "computer") {
        setTimeout(mutareaComputerului, 1000);
    }
}

function mutareaComputerului() {
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            let tds = document.getElementById("tabel").getElementsByTagName("td");
            tds[this.responseText].click();
        }

    }
    xhttp.open("GET", "mutareComputer.php?lista=" + JSON.stringify(ocupareaCelulelor), true);
    xhttp.send();
}