function creazaTabel(persoane) {
    let tabel = document.getElementById("tabel");
    let tr = tabel.getElementsByTagName("tr");

    for (let i = tr.length - 1; i > 0; i--) {
        tabel.removeChild(tr[i]);
    }

    persoane.forEach(function (persoana) {
        let tr = document.createElement("tr");
        let td_nume = document.createElement("td");
        let td_prenume = document.createElement("td");
        let td_telefon = document.createElement("td");
        let td_email = document.createElement("td");

        td_nume.textContent = persoana["nume"];
        td_prenume.textContent = persoana["prenume"];
        td_telefon.textContent = persoana["telefon"];
        td_email.textContent = persoana["email"];

        tr.appendChild(td_nume);
        tr.appendChild(td_prenume);
        tr.appendChild(td_telefon);
        tr.appendChild(td_email);
        tabel.appendChild(tr);
    });
}

let paginaCurenta;
let numarElementePePagina;
let numarElementeInTotal;

function populeazaTabel() {
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {

            let persoane = JSON.parse(this.responseText);
            creazaTabel(persoane);
        }
    }
    xhttp.open("GET", "persoaneDePePaginaCurenta.php?paginaCurenta=" + paginaCurenta + "&numarElementePePagina=" + numarElementePePagina, true);
    xhttp.send();
}

document.addEventListener("DOMContentLoaded", function () {
    paginaCurenta = 0;
    numarElementePePagina = 3;
    populeazaTabel();
    let previousButton = document.getElementById("prev");
    let nextButton = document.getElementById("next");
    disablePrevAndNext();
    previousButton.addEventListener("click", previousPage);
    nextButton.addEventListener("click", nextPage);

    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {

            numarElementeInTotal = this.responseText;
        }
    }
    xhttp.open("GET", "numarElementeInTotal.php", true);
    xhttp.send();
});

function disablePrevAndNext() {
    let nextButton = document.getElementById("next");
    let previousButton = document.getElementById("prev");
    previousButton.disabled = false;
    nextButton.disabled = false;

    if (paginaCurenta === 0) {
        previousButton.disabled = true;
        nextButton.disabled = false;
    }

    if (paginaCurenta === Math.ceil(numarElementeInTotal / numarElementePePagina) - 1) {
        nextButton.disabled = true;
        previousButton.disabled = false;
    }
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