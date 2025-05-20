let freeX = null;
let freeY = null;

$(document).ready(function () {
    let tabel = $('#tabel');
    tabel.find('tr').each(function(i) {
        $(this).find('td').each(function(j) {
            if ($(this).text() === '') {
                freeX = i;
                freeY = j;
            }
        });
    });
});

$(document).keydown(joc);

function valid(x, y, nrLinii, nrColoane) {
    if (x == null || y == null)
        return false;
    if (x < 0 || x >= nrLinii || y < 0 || y >= nrColoane)
        return false;
    return true;
}

function verificaDacaACastigat() {
    let tabel = $('#tabel');
    let castigat = true;
    tabel.find('tr').each(function() {
        let cells = $(this).find('td');
        for (let i = 0; i < cells.length - 1; i++) {
            if (parseInt(cells.eq(i).text()) > parseInt(cells.eq(i + 1).text())) {
                castigat = false;
                break;
            }
        }
    });
    return castigat;
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
    let tabel = $('#tabel');
    let nrLinii = tabel.find('tr').length;
    let nrColoane = tabel.find('tr:first-child td').length;

    if (valid(xVecin, yVecin, nrLinii, nrColoane)){
        let td1 = tabel.find('tr').eq(freeX).find('td').eq(freeY);
        let td2 = tabel.find('tr').eq(xVecin).find('td').eq(yVecin);

        let aux = td1.text();
        td1.text(td2.text());
        td2.text(aux);

        freeX = xVecin;
        freeY = yVecin;
    }

    if (verificaDacaACastigat()){
        alert("Ai castigat!");
        $(document).off('keydown', joc);
    }
}
