document.addEventListener('DOMContentLoaded', function () {
    let table = document.getElementById("tabel");
    for (let i = 0; i < table.rows.length; i++)
        for (let j = 0; j < table.rows[i].cells.length; j++) {
            let td = table.rows[i].cells[j];
            td.dataset.originalContent = td.textContent;
            let image = td.getElementsByTagName("img")[0];
            image.style.display = 'none';
            td.addEventListener('click', showNumber);
        }
})

let firstSelectedCell, secondSelectedCell, numberOfMouseClicks = 0;

function showNumber() {
    numberOfMouseClicks++;
    let image = this.getElementsByTagName("img")[0];
    image.style.display = 'initial';

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
            let img1 = firstSelectedCell.getElementsByTagName("img")[0];
            let img2 = secondSelectedCell.getElementsByTagName("img")[0];
            if (img1.src !== img2.src){
               img1.style.display = 'none';
               img2.style.display = 'none';
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