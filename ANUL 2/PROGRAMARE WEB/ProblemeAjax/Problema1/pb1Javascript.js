document.addEventListener("DOMContentLoaded", function () {

        let selectElement = document.getElementById('orase_plecare');
        selectElement.innerHTML = '';
        let option = document.createElement('option');
        option.value = "Alege un oras";
        option.text = "Alege un oras";
        selectElement.appendChild(option);
        let xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.readyState === 4 && this.status === 200) {

                let cities = JSON.parse(this.responseText);
                cities.forEach(function (city) {
                    let option = document.createElement('option');
                    option.value = city;
                    option.text = city;
                    selectElement.appendChild(option);
                });
            }
        }

        xhttp.open("GET", "orase_de_plecare.php", true);
        xhttp.send();

    }
);

function modificaOraseSosire(city){
    let xhttp = new XMLHttpRequest();
    let selectElement = document.getElementById('orase_sosire');
    selectElement.innerHTML = '';
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            let end_cities = JSON.parse(this.responseText);
            end_cities.forEach(function (end_city) {
                let option = document.createElement('option');
                option.value = end_city;
                option.text = end_city;
                selectElement.appendChild(option);
            });
        }
    }

    xhttp.open("GET", "orase_de_sosire.php?oras_plecare="+city, true);
    xhttp.send();
}


