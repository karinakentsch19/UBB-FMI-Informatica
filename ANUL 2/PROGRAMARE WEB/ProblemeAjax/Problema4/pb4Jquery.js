let ocupareaCelulelor = [];
let jucatorCurent = null;

function seteazaJucatorulCurent() {
    let p = $("#jucatorCurent");
    p.text("Jucatorul curent este: " + jucatorCurent);
}

$(document).ready(function() {
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
    let tds = $("#tabel td");
    tds.on("click", setareCasuta);

    for (let i = 0; i < 9; i++) {
        ocupareaCelulelor[i] = null;
    }
}

function pozitieCasuta(td) {
    let tds = $("#tabel td");
    for (let i = 0; i < tds.length; i++) {
        if (tds[i] === td) {
            return i;
        }
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
    $.ajax({
        url: "verificaDacaECastig.php",
        type: "GET",
        data: { lista: JSON.stringify(ocupareaCelulelor) },
        success: function(mesaj) {
            if (mesaj === "computer a castigat" || mesaj === "utilizator a castigat") {
                alert(mesaj);
                blocheazaCasute();
            }
        }
    });
}

function blocheazaCasute() {
    let tds = $("#tabel td");
    tds.off("click", setareCasuta);
}

function setareCasuta() {
    let poz = pozitieCasuta(this);
    ocupareaCelulelor[poz] = jucatorCurent;
    if (jucatorCurent === "computer") {
        $(this).text("O");
    } else {
        $(this).text("X");
    }
    verificaDacaACastigat();
    schimbaJucator();
    if (jucatorCurent === "computer") {
        setTimeout(mutareaComputerului, 1000);
    }
}

function mutareaComputerului() {
    $.ajax({
        url: "mutareComputer.php",
        type: "GET",
        data: { lista: JSON.stringify(ocupareaCelulelor) },
        success: function(response) {
            let tds = $("#tabel td");
            tds.eq(response).click();
        }
    });
}
