let root = "C:\\xampp\\htdocs\\ProblemeAjax\\Problema5\\root";

function loadDirectoryStructure(path, $container) {
    $.ajax({
        url: "fisiereSiDirectoare.php",
        type: "GET",
        data: { path: path },
        success: function(response) {
            let fisiereSiDirectoare = response;
            let $ul = $("<ul></ul>");
            for (let i = 0; i < fisiereSiDirectoare.length; i++) {
                let $li = $("<li></li>")
                    .text(fisiereSiDirectoare[i]["name"])
                    .data("original-content", fisiereSiDirectoare[i]["name"]);

                if (fisiereSiDirectoare[i]["type"] === "directory") {
                    $li.on("click", loadMoreDirectories);
                } else {
                    $li.on("click", loadFileContent);
                }
                $ul.append($li);
            }
            $container.append($ul);
        }
    });
}

$(document).ready(function() {
    let $div = $("#div");
    loadDirectoryStructure(root, $div);
});

function getPath($li) {
    let path = "";
    let $itemCurent = $li;
    while ($itemCurent.attr("id") !== "div") {
        path = $itemCurent.data("original-content") + "\\" + path;
        $itemCurent = $itemCurent.parent().parent();
    }
    return root + "\\" + path.slice(0, -1);
}

function loadMoreDirectories() {
    $(this).off("click", loadMoreDirectories);
    loadDirectoryStructure(getPath($(this)), $(this));
}

function loadFileContent() {
    $(this).off("click", loadFileContent);
    let $p = $("<p></p>");
    $(this).append($p);

    $.ajax({
        url: "continutFisier.php",
        type: "GET",
        data: { path: getPath($(this)) },
        success: function(response) {
            $p.text(response);
        }
    });
}
