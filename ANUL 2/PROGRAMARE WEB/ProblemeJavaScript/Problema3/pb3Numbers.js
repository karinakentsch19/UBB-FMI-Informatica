document.addEventListener('DOMContentLoaded', function () {
    let table = document.getElementById("tabel");
    for (let i = 0; i < table.rows.length; i++)
        for (let j = 0; j < table.rows[i].cells.length; j++) {
            let td = table.rows[i].cells[j];
            td.dataset.originalContent = td.textContent;
            td.textContent = "";
            td.addEventListener('click', showNumber);
        }
})

let firstSelectedCell, secondSelectedCell, numberOfMouseClicks = 0;

function showNumber() {
    numberOfMouseClicks++;
    this.textContent = this.dataset.originalContent;

    if (numberOfMouseClicks === 1) {
        firstSelectedCell = this;
        firstSelectedCell.removeEventListener('click', showNumber);
    }
    else {
        secondSelectedCell = this;
        secondSelectedCell.removeEventListener('click', showNumber);
        numberOfMouseClicks = 0;

        //disable la toate celule
        let tds = document.querySelectorAll('#tabel td');
        tds.forEach(function(td) {
            td.removeEventListener('click', showNumber);
        });

        blockPage(2000, function() {
            if (firstSelectedCell.textContent !== secondSelectedCell.textContent){
                firstSelectedCell.textContent = "";
                secondSelectedCell.textContent = "";
                firstSelectedCell.addEventListener('click', showNumber);
                secondSelectedCell.addEventListener('click', showNumber);
            }
            tds.forEach(function(td) {
                td.addEventListener('click', showNumber);
            });
        });
    }
}

function blockPage(duration, callback) {
    setTimeout(callback, duration);
}