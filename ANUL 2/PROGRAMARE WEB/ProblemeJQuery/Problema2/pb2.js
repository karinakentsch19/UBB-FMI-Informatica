function validareNume(nume) {
    let regexNume = /^.+$/;
    return regexNume.test(nume);
}

function validareDataNasterii(dataNasterii) {
    let regexDataNasterii = /^[0-9]{2}-[0-9]{2}-[1-9][0-9]{3}$/;
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
    let textBoxNume = $("#nume");
    let textBoxDataNasterii = $("#dataNasterii");
    let textBoxVarsta = $("#varsta");
    let textBoxEmail = $("#email");

    let nume = textBoxNume.val();
    let dataNasterii = textBoxDataNasterii.val();
    let varsta = textBoxVarsta.val();
    let email = textBoxEmail.val();

    let mesaj = "";

    textBoxNume.css("border-color", "black");
    textBoxDataNasterii.css("border-color", "black");
    textBoxVarsta.css("border-color", "black");
    textBoxEmail.css("border-color", "black");

    if (!validareNume(nume)) {
        mesaj += "Nume invalid\n";
        textBoxNume.css("border-color", "red");
    }
    if (!validareDataNasterii(dataNasterii)) {
        mesaj += "Data nasterii invalida\n";
        textBoxDataNasterii.css("border-color", "red");
    }
    if (!validareVarsta(varsta)) {
        mesaj += "Varsta invalida\n";
        textBoxVarsta.css("border-color", "red");
    }
    if (!validareEmail(email)) {
        mesaj += "Email invalid\n";
        textBoxEmail.css("border-color", "red");
    }

    if (mesaj === ""){
        alert("Datele sunt completate corect");
    }
    else{
        alert(mesaj);
    }
}
