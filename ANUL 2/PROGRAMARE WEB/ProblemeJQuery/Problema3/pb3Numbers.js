$(document).ready(function () {
    let table = $("#tabel");
    table.find("td").each(function() {
        let td = $(this);
        td.data("originalContent", td.text());
        td.text("");
        td.click(showNumber);
    });
});

let firstSelectedCell, secondSelectedCell, numberOfMouseClicks = 0;

function showNumber() {
    numberOfMouseClicks++;
    $(this).text($(this).data("originalContent"));

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
            if (firstSelectedCell.text() !== secondSelectedCell.text()) {
                firstSelectedCell.text("");
                secondSelectedCell.text("");
                firstSelectedCell.click(showNumber);
                secondSelectedCell.click(showNumber);
            }
            $("#tabel td").click(showNumber);
        });
    }
}

function blockPage(duration, callback) {
    setTimeout(callback, duration);
}
