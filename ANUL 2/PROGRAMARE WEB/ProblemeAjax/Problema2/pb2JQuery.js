function creazaTabel(persoane) {
    let tabel = $("#tabel");
    tabel.find("tr:gt(0)").remove();
    persoane.forEach(function(persoana) {
        let tr = $("<tr>");
        let td_nume = $("<td>").text(persoana["nume"]);
        let td_prenume = $("<td>").text(persoana["prenume"]);
        let td_telefon = $("<td>").text(persoana["telefon"]);
        let td_email = $("<td>").text(persoana["email"]);

        tr.append(td_nume, td_prenume, td_telefon, td_email);
        tabel.append(tr);
    });
}

let paginaCurenta;
let numarElementePePagina;
let numarElementeInTotal;

function populeazaTabel() {
    $.get("persoaneDePePaginaCurenta.php", {
        paginaCurenta: paginaCurenta,
        numarElementePePagina: numarElementePePagina
    }).done(function(persoane) {
        persoane = JSON.parse(persoane);
        creazaTabel(persoane);
    });
}

$(document).ready(function() {
    paginaCurenta = 0;
    numarElementePePagina = 3;
    populeazaTabel();

    let previousButton = $("#prev");
    let nextButton = $("#next");
    disablePrevAndNext();

    previousButton.on("click", previousPage);
    nextButton.on("click", nextPage);

    $.get("numarElementeInTotal.php").done(function(response) {
        numarElementeInTotal = response;
    });
});

function disablePrevAndNext() {
    let nextButton = $("#next");
    let previousButton = $("#prev");
    previousButton.prop("disabled", paginaCurenta === 0);
    nextButton.prop("disabled", paginaCurenta === Math.ceil(numarElementeInTotal / numarElementePePagina) - 1);
}

function previousPage() {
    paginaCurenta--;
    populeazaTabel();
    disablePrevAndNext();
}

function nextPage() {
    paginaCurenta++;
    populeazaTabel();
    disablePrevAndNext();
}
