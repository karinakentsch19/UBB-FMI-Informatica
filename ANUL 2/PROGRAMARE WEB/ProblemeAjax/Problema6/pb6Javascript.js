document.addEventListener("DOMContentLoaded", function (){
    genereazaComboBox();
    incarcaDateInTabel();
});

function genereazaUnSingurComboBox(comboBox){
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
           let values = JSON.parse(this.responseText);
           let option = document.createElement("option");
           option.value = null;
           option.text = comboBox.id;
           comboBox.appendChild(option);

           values.forEach(function (value){
               let option = document.createElement("option");
               option.value = value;
               option.text = value;
               comboBox.appendChild(option);
           });
        }

    }
    xhttp.open("GET", "valoriPtComboBox.php?column=" + comboBox.id, true);
    xhttp.send();
}

function genereazaComboBox() {
    let comboBoxes = document.getElementById("filtre").getElementsByTagName("select");
    for (let i = 0; i < comboBoxes.length; i++){
        comboBoxes[i].addEventListener("change", incarcaDateInTabel);
        genereazaUnSingurComboBox(comboBoxes[i]);
    }
}

function creeazaOLinieDinTabel(haina){
    let tr = document.createElement("tr");
    let td_marime = document.createElement("td");
    let td_tip = document.createElement("td");
    let td_culoare = document.createElement("td");

    td_marime.textContent = haina["marime"];
    td_tip.textContent = haina["tip"];
    td_culoare.textContent = haina["culoare"];

    tr.appendChild(td_marime);
    tr.appendChild(td_tip);
    tr.appendChild(td_culoare);

    return tr;
}

function incarcaTabel(haine){
    let tabel = document.getElementById("tabel");

    let trs = tabel.getElementsByTagName("tr");
    for (let i = trs.length - 1; i > 0; i--){
        tabel.removeChild(trs[i]);
    }

    haine.forEach(function (haina){
        tabel.appendChild(creeazaOLinieDinTabel(haina));
    });
}

function incarcaDateInTabel(){
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
           
            let haine = JSON.parse(this.responseText);
            incarcaTabel(haine);
        }

    }
    xhttp.open("POST", "filtrare.php", true);

    let marime = document.getElementById("marime").value;
    let tip = document.getElementById("tip").value;
    let culoare = document.getElementById("culoare").value;

    let params = `marime=${encodeURIComponent(marime)}&tip=${encodeURIComponent(tip)}&culoare=${encodeURIComponent(culoare)}`;

    xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhttp.send(params);
}