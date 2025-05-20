$(document).ready(function () {
    genereazaComboBox();
    incarcaDateInTabel();
});

function genereazaUnSingurComboBox(comboBox) {
    $.ajax({
        url: "valoriPtComboBox.php",
        type: "GET",
        data: { column: comboBox.id },
        success: function (response) {
            let values = JSON.parse(response);
            let option = $("<option>").val(null).text(comboBox.id);
            $(comboBox).append(option);

            values.forEach(function (value) {
                let option = $("<option>").val(value).text(value);
                $(comboBox).append(option);
            });
        }
    });
}

function genereazaComboBox() {
    $("#filtre select").each(function () {
        $(this).on("change", incarcaDateInTabel);
        genereazaUnSingurComboBox(this);
    });
}

function creeazaOLinieDinTabel(haina) {
    let tr = $("<tr>");
    let td_marime = $("<td>").text(haina["marime"]);
    let td_tip = $("<td>").text(haina["tip"]);
    let td_culoare = $("<td>").text(haina["culoare"]);

    tr.append(td_marime).append(td_tip).append(td_culoare);

    return tr;
}

function incarcaTabel(haine) {
    let tabel = $("#tabel");
    tabel.find("tr:gt(0)").remove(); // Remove all rows except the first one

    haine.forEach(function (haina) {
        tabel.append(creeazaOLinieDinTabel(haina));
    });
}

function incarcaDateInTabel() {
    let marime = $("#marime").val();
    let tip = $("#tip").val();
    let culoare = $("#culoare").val();

    let params = {
        marime: marime,
        tip: tip,
        culoare: culoare
    };

    $.ajax({
        url: "filtrare.php",
        type: "POST",
        data: $.param(params),
        success: function (response) {
            let haine = JSON.parse(response);
            incarcaTabel(haine);
        },
        contentType: "application/x-www-form-urlencoded"
    });
}
