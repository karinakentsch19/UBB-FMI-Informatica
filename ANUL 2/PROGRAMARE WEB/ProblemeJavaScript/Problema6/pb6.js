let freeX = null;
let freeY = null;

document.addEventListener('DOMContentLoaded', function () {
    let tabel = document.getElementById('tabel');
    for (let i = 0; i < tabel.rows.length; i++)
        for (let j = 0; j < tabel.rows[i].cells.length; j++) {
            let td = tabel.rows[i].cells[j];
            if (td.textContent === '') {
                freeX = i;
                freeY = j;
            }
        }
});

document.addEventListener('keydown', joc);

function valid(x, y, nrLinii, nrColoane) {
    if (x == null || y == null)
        return false;
    if (x < 0 || x > nrLinii || y < 0 || y > nrColoane)
        return false;
    return true;
}

function verificaDacaACastigat() {
    let tabel = document.getElementById('tabel');
    for (let i = 0; i < tabel.rows.length; i++)
        for (let j = 0; j < tabel.rows[i].cells.length - 1; j++) {
            let td1 = tabel.rows[i].cells[j];
            let td2 = tabel.rows[i].cells[j + 1];
            if (parseInt(td1.textContent) > parseInt(td2.textContent))
                return false;
        }
    return true;
}

function joc(event) {
    let xVecin, yVecin;
    if (event.key === 'ArrowUp') {
        xVecin = freeX - 1;
        yVecin = freeY;
    } else if (event.key === 'ArrowDown') {
        xVecin = freeX + 1;
        yVecin = freeY;
    } else if (event.key === 'ArrowLeft') {
        xVecin = freeX;
        yVecin = freeY - 1;
    } else if (event.key === 'ArrowRight') {
        xVecin = freeX;
        yVecin = freeY + 1;
    }
    let tabel = document.getElementById('tabel');
    let nrLinii = tabel.rows.length;
    let nrColoane = tabel.rows[0].cells.length;

    if (valid(xVecin, yVecin, nrLinii, nrColoane)){
        let td1 = tabel.rows[freeX].cells[freeY];
        let td2 = tabel.rows[xVecin].cells[yVecin];

        let aux = td1.textContent;
        td1.textContent = td2.textContent;
        td2.textContent = aux;

        freeX = xVecin;
        freeY = yVecin;
    }

    if (verificaDacaACastigat()){
        alert("Ai castigat!");
        document.removeEventListener('keydown', joc);
    }
}