let root = "C:\\xampp\\htdocs\\ProblemeAjax\\Problema5\\root";

function loadDirectoryStructure(path, container) {
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            let fisiereSiDirectoare = JSON.parse(this.responseText);
            let ul = document.createElement("ul");
            for (let i = 0; i < fisiereSiDirectoare.length; i++) {
                let li = document.createElement("li");
                li.textContent = fisiereSiDirectoare[i]["name"];
                li.dataset.OriginalContent = fisiereSiDirectoare[i]["name"];

                if (fisiereSiDirectoare[i]["type"] === "directory") {
                    li.addEventListener("click", loadMoreDirectories);
                } else {
                    li.addEventListener("click", loadFileContent);
                }
                ul.appendChild(li);
            }
            container.appendChild(ul);
        }

    }
    xhttp.open("GET", "fisiereSiDirectoare.php?path=" + path, true);
    xhttp.send();
}

document.addEventListener("DOMContentLoaded", function () {
    let div = document.getElementById("div");
    loadDirectoryStructure(root, div);
});

function getPath(li) {
    let path = "";
    let itemCurent = li;
    while (itemCurent.id !== "div") {
        path = itemCurent.dataset.OriginalContent + "\\" + path;
        itemCurent = itemCurent.parentNode.parentNode;
    }
    return root + "\\" + path.slice(0, -1);
}

function loadMoreDirectories() {
    this.removeEventListener("click", loadMoreDirectories);
    loadDirectoryStructure(getPath(this), this);
}

function loadFileContent() {
    this.removeEventListener("click", loadFileContent);
    let p = document.createElement("p");
    this.appendChild(p);

    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            p.textContent = this.responseText;
        }

    }
    xhttp.open("GET", "continutFisier.php?path=" + getPath(this), true);
    xhttp.send();
}
