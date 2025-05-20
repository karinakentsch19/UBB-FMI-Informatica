$(document).ready(function () {
    let table = $("#tabel");
    table.find("td").each(function() {
        let td = $(this);
        td.data("originalContent", td.text());
        let image = td.find("img").hide();
        td.click(showNumber);
    });
});

let firstSelectedCell, secondSelectedCell, numberOfMouseClicks = 0;

function showNumber() {
    numberOfMouseClicks++;
    let image = $(this).find("img").show();

    if (numberOfMouseClicks === 1) {
        firstSelectedCell = $(this);
        firstSelectedCell.off('click', showNumber);
    } else {
        secondSelectedCell = $(this);
        secondSelectedCell.off('click', showNumber);
        numberOfMouseClicks = 0;

        // Disable all cells
        $("#tabel td").off('click', showNumber);

        blockPage(2000, function() {
            let img1 = firstSelectedCell.find("img");
            let img2 = secondSelectedCell.find("img");
            if (img1.attr("src") !== img2.attr("src")) {
                img1.hide();
                img2.hide();
            }
            $("#tabel td").click(showNumber);
        });
    }
}

function blockPage(duration, callback) {
    setTimeout(callback, duration);
}
