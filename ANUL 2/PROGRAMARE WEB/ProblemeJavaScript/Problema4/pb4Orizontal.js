class Fruct {
    constructor(tip, pret, cantitate) {
        this.tip = tip;
        this.pret = parseInt(pret);
        this.cantitate = parseInt(cantitate);
    }
}

const fructe = []

let ultimaOrdonare = 'None'
let tipDeOrdonare = 'Ascending'

document.addEventListener('DOMContentLoaded', function () {
    let tabel = document.getElementById('tabel');
    let numarLinii = tabel.rows.length;
    for (let i = 1; i < numarLinii; i++) {
        let tip = tabel.rows[i].cells[0].textContent;
        let pret = tabel.rows[i].cells[1].textContent;
        let cantitate = tabel.rows[i].cells[2].textContent;

        let fruct = new Fruct(tip, pret, cantitate);
        fructe.push(fruct);
    }

    for (let i = 0; i < tabel.rows[0].cells.length; i++) {
        let th = tabel.rows[0].cells[i];
        th.addEventListener('click', sorteazaTabel);
    }

});

function sorteazaDupaTip() {
    for (let i = 0; i < fructe.length - 1; i++) {
        for (let j = i + 1; j < fructe.length; j++) {
            if (fructe[i].tip > fructe[j].tip && tipDeOrdonare === 'Ascending' || fructe[i].tip < fructe[j].tip && tipDeOrdonare === 'Descending') {
                const aux = fructe[i];
                fructe[i] = fructe[j];
                fructe[j] = aux;
            }
        }
    }
}

function sorteazaDupaPret() {
    for (let i = 0; i < fructe.length - 1; i++) {
        for (let j = i + 1; j < fructe.length; j++) {
            if (fructe[i].pret > fructe[j].pret && tipDeOrdonare === 'Ascending' || fructe[i].pret < fructe[j].pret && tipDeOrdonare === 'Descending') {
                const aux = fructe[i];
                fructe[i] = fructe[j];
                fructe[j] = aux;
            }
        }
    }
}

function sorteazaDupaCantitate() {
    for (let i = 0; i < fructe.length - 1; i++) {
        for (let j = i + 1; j < fructe.length; j++) {
            if (fructe[i].cantitate > fructe[j].cantitate && tipDeOrdonare === 'Ascending' || fructe[i].cantitate < fructe[j].cantitate && tipDeOrdonare === 'Descending') {
                const aux = fructe[i];
                fructe[i] = fructe[j];
                fructe[j] = aux;
            }
        }
    }
}

function sorteazaTabel() {
    if (this.textContent === 'Fructe') {
        if (ultimaOrdonare === 'Fructe') {
            if (tipDeOrdonare === 'Ascending') {
                tipDeOrdonare = 'Descending';
            } else {
                tipDeOrdonare = 'Ascending';
            }
        } else {
            tipDeOrdonare = 'Ascending';
            ultimaOrdonare = 'Fructe';
        }
        sorteazaDupaTip();
    } else {
        if (this.textContent === 'Pret') {
            if (ultimaOrdonare === 'Pret') {
                if (tipDeOrdonare === 'Ascending') {
                    tipDeOrdonare = 'Descending';
                } else {
                    tipDeOrdonare = 'Ascending';

                }
            } else {
                tipDeOrdonare = 'Ascending';
                ultimaOrdonare = 'Pret';
            }
            sorteazaDupaPret();
        } else {
            if (this.textContent === 'Cantitate') {
                if (ultimaOrdonare === 'Cantitate') {
                    if (tipDeOrdonare === 'Ascending') {
                        tipDeOrdonare = 'Descending';
                    } else {
                        tipDeOrdonare = 'Ascending';
                    }
                } else {
                    tipDeOrdonare = 'Ascending';
                    ultimaOrdonare = 'Cantitate';
                }
                sorteazaDupaCantitate();
            }
        }
    }
    let tabel = document.getElementById('tabel');
    let numarDeLinii = tabel.rows.length;
    for (let j = 1; j < numarDeLinii; j++) {
        tabel.rows[j].cells[0].textContent = fructe[j - 1].tip;
        tabel.rows[j].cells[1].textContent = fructe[j - 1].pret;
        tabel.rows[j].cells[2].textContent = fructe[j - 1].cantitate;
    }
}