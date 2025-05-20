class Fruct {
    constructor(tip, pret, cantitate) {
        this.tip = tip;
        this.pret = parseInt(pret);
        this.cantitate = parseInt(cantitate);
    }
}

const fructe = [];

let ultimaOrdonare = 'None';
let tipDeOrdonare = 'Ascending';

$(document).ready(function () {
    let tabel = $('#tabel');

    let rows = tabel.find('tr');
    let numarColoane = rows.eq(0).find('td').length;

    for (let j = 1; j < rows.length; j++) {
        let tip = rows.eq(j).find('td').eq(0).text();
        let pret = rows.eq(j).find('td').eq(1).text();
        let cantitate = rows.eq(j).find('td').eq(2).text();

        let fruct = new Fruct(tip, pret, cantitate);
        fructe.push(fruct);
    }

    tabel.find('th').each(function () {
        $(this).click(sorteazaTabel)
    })
});

function sorteazaDupaTip() {
    fructe.sort(function(a, b) {
        if (tipDeOrdonare === 'Ascending') {
            return a.tip.localeCompare(b.tip);
        } else {
            return b.tip.localeCompare(a.tip);
        }
    });
}

function sorteazaDupaPret() {
    fructe.sort(function(a, b) {
        if (tipDeOrdonare === 'Ascending') {
            return a.pret - b.pret;
        } else {
            return b.pret - a.pret;
        }
    });
}

function sorteazaDupaCantitate() {
    fructe.sort(function(a, b) {
        if (tipDeOrdonare === 'Ascending') {
            return a.cantitate - b.cantitate;
        } else {
            return b.cantitate - a.cantitate;
        }
    });
}

function sorteazaTabel() {
    let coloana = $(this).text();

    if (coloana === 'Fructe') {
        if (ultimaOrdonare === 'Fructe') {
            tipDeOrdonare = (tipDeOrdonare === 'Ascending') ? 'Descending' : 'Ascending';
        } else {
            ultimaOrdonare = 'Fructe';
        }
        sorteazaDupaTip();
    } else if (coloana === 'Pret') {
        if (ultimaOrdonare === 'Pret') {
            tipDeOrdonare = (tipDeOrdonare === 'Ascending') ? 'Descending' : 'Ascending';
        } else {
            ultimaOrdonare = 'Pret';
        }
        sorteazaDupaPret();
    } else if (coloana === 'Cantitate') {
        if (ultimaOrdonare === 'Cantitate') {
            tipDeOrdonare = (tipDeOrdonare === 'Ascending') ? 'Descending' : 'Ascending';
        } else {
            ultimaOrdonare = 'Cantitate';
        }
        sorteazaDupaCantitate();
    }

    let tabel = $('#tabel');
    for (let j = 1; j < tabel.find('tr').length; j++) {
        tabel.find('tr').eq(j).find('td').eq(0).text(fructe[j - 1].tip);
        tabel.find('tr').eq(j).find('td').eq(1).text(fructe[j - 1].pret);
        tabel.find('tr').eq(j).find('td').eq(2).text(fructe[j - 1].cantitate);
    }
}
