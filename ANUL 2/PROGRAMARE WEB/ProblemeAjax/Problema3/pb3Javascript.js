let idElementLista = null;
let amModificatFormularul = false;
let butonSave = null;
function populeazaFormular(persoana){
    let inputNume = document.getElementById("nume");
    let inputPrenume = document.getElementById("prenume");
    let inputVarsta = document.getElementById("varsta");

    inputNume.value = persoana["nume"];
    inputPrenume.value = persoana["prenume"];
    inputVarsta.value = persoana["varsta"];
}

function gasestePersoanaDupaId() {
    if (amModificatFormularul === true)
        if (confirm("Datele au fost modificate. Doriti sa le salvati?")){
            salveazaModificari();
        }
    let id = this.textContent;
    idElementLista = id;
    dateSalvate();
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            let persoana = JSON.parse(this.responseText);
            populeazaFormular(persoana);
        }
    }
    xhttp.open("GET", "persoanaCuIdDat.php?id=" + id, true);
    xhttp.send();
}

function initializaListaCuToateId(){
    let listaId = document.getElementById("lista");
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            let id_uri = JSON.parse(this.responseText);
            for(let i = 0; i < id_uri.length; i++){
                let li = document.createElement("li");
                li.textContent = id_uri[i];
                li.addEventListener("click", gasestePersoanaDupaId);
                listaId.appendChild(li);
            }
        }
    }
    xhttp.open("GET", "toateId.php", true);
    xhttp.send();
}

document.addEventListener("DOMContentLoaded", function (){
    butonSave = document.getElementById("save");
    butonSave.addEventListener("click", salveazaModificari);
    initializaListaCuToateId();
    butonSave.disabled = true;


    let inputNume = document.getElementById("nume");
    let inputPrenume = document.getElementById("prenume");
    let inputVarsta = document.getElementById("varsta");

    inputNume.addEventListener("change", schimbareInFormular);
    inputPrenume.addEventListener("change", schimbareInFormular);
    inputVarsta.addEventListener("change", schimbareInFormular);
});

function salveazaModificari(){

    let inputNume = document.getElementById("nume");
    let inputPrenume = document.getElementById("prenume");
    let inputVarsta = document.getElementById("varsta");

    let nume = inputNume.value;
    let prenume = inputPrenume.value;
    let varsta = inputVarsta.value;

    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            alert("Datele s-au salvat cu succes!");
            dateSalvate();
        }
    }
    xhttp.open("POST", "salveazaModificari.php", true);
    xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    let params = `id=${encodeURIComponent(idElementLista)}&nume=${encodeURIComponent(nume)}&prenume=${encodeURIComponent(prenume)}&varsta=${encodeURIComponent(varsta)}`;
    xhttp.send(params);
}

function dateSalvate(){
    amModificatFormularul = false;
    butonSave.disabled = true;
}
function schimbareInFormular(){
    amModificatFormularul = true;
    butonSave.disabled = false;
}