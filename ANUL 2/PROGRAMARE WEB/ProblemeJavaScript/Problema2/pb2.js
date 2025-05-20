function validareNume(nume) {
    let regexNume = /^.+$/;
    return regexNume.test(nume);
}

function validareDataNasterii(dataNasterii) {
    let regexDataNasterii = /^[0-9]{2}-[0-9]{2}-[1-9][0-9][0-9][0-9]$/;
    return regexDataNasterii.test(dataNasterii);
}

function validareVarsta(varsta) {
    let regexVarsta = /^[0-9]+$/;
    return regexVarsta.test(varsta);
}

function validareEmail(email) {
    let regexEmail = /^.+@.+\..+$/;
    return regexEmail.test(email);
}

function checkValues() {
    let textBoxNume = document.getElementById("nume");
    let textBoxDataNasterii = document.getElementById("dataNasterii");
    let textBoxVarsta = document.getElementById("varsta");
    let textBoxEmail = document.getElementById("email");

    let nume = textBoxNume.value;
    let dataNasterii = textBoxDataNasterii.value;
    let varsta = textBoxVarsta.value;
    let email = textBoxEmail.value;

    let mesaj = "";

    textBoxNume.style.borderColor = "black";
    textBoxDataNasterii.style.borderColor = "black";
    textBoxVarsta.style.borderColor = "black";
    textBoxEmail.style.borderColor = "black";

    if (!validareNume(nume)) {
        mesaj += "Nume invalid\n";
        textBoxNume.style.borderColor = "red";
    }
    if (!validareDataNasterii(dataNasterii)) {
        mesaj += "Data nasterii invalida\n";
        textBoxDataNasterii.style.borderColor = "red";
    }
    if (!validareVarsta(varsta)) {
        mesaj += "Varsta invalida\n";
        textBoxVarsta.style.borderColor = "red";
    }
    if (!validareEmail(email)) {
        mesaj += "Email invalid\n";
        textBoxEmail.style.borderColor = "red";
    }

    if (mesaj === ""){
        alert("Datele sunt completate corect");
    }
    else{
        alert(mesaj);
    }
}