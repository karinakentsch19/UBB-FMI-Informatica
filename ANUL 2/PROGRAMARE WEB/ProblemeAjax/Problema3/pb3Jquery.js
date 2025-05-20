let idElementLista = null;
let amModificatFormularul = false;
let butonSave = null;

function populeazaFormular(persoana) {
    $("#nume").val(persoana["nume"]);
    $("#prenume").val(persoana["prenume"]);
    $("#varsta").val(persoana["varsta"]);
}

function gasestePersoanaDupaId() {
    if (amModificatFormularul) {
        if (confirm("Datele au fost modificate. Doriti sa le salvati?")) {
            salveazaModificari();
        }
    }
    let id = $(this).text();
    idElementLista = id;
    dateSalvate();
    $.ajax({
        url: "persoanaCuIdDat.php",
        type: "GET",
        data: { id: id },
        success: function(response) {
            let persoana = JSON.parse(response);
            populeazaFormular(persoana);
        }
    });
}

function initializaListaCuToateId() {
    let listaId = $("#lista");
    $.ajax({
        url: "toateId.php",
        type: "GET",
        success: function(response) {
            let id_uri = JSON.parse(response);
            for (let i = 0; i < id_uri.length; i++) {
                let li = $("<li>").text(id_uri[i]);
                li.on("click", gasestePersoanaDupaId);
                listaId.append(li);
            }
        }
    });
}

$(document).ready(function() {
    butonSave = $("#save");
    butonSave.on("click", salveazaModificari);
    initializaListaCuToateId();
    butonSave.prop("disabled", true);

    $("#nume, #prenume, #varsta").on("change", schimbareInFormular);
});

function salveazaModificari() {
    let nume = $("#nume").val();
    let prenume = $("#prenume").val();
    let varsta = $("#varsta").val();

    $.ajax({
        url: "salveazaModificari.php",
        type: "POST",
        data: {
            id: idElementLista,
            nume: nume,
            prenume: prenume,
            varsta: varsta
        },
        success: function() {
            alert("Datele s-au salvat cu succes!");
            dateSalvate();
        }
    });
}

function dateSalvate() {
    amModificatFormularul = false;
    butonSave.prop("disabled", true);
}

function schimbareInFormular() {
    amModificatFormularul = true;
    butonSave.prop("disabled", false);
}
