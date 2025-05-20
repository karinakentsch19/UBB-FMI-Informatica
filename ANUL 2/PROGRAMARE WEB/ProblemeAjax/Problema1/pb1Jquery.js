$(document).ready(function() {

    $('#orase_plecare').html('');
    $('#orase_plecare').append('<option value="Alege un oras">Alege un oras</option>');

    $.ajax({
        url: 'orase_de_plecare.php',
        type: 'GET',
        dataType: 'json',
        success: function(cities) {
            cities.forEach(function(city) {
                $('#orase_plecare').append('<option value="' + city + '">' + city + '</option>');
            });
        }
    });

});

function modificaOraseSosire(city) {
    $.ajax({
        url: 'orase_de_sosire.php',
        type: 'GET',
        data: { oras_plecare: city },
        dataType: 'json',
        success: function(end_cities) {
            $('#orase_sosire').html('');
            end_cities.forEach(function(end_city) {
                $('#orase_sosire').append('<option value="' + end_city + '">' + end_city + '</option>');
            });
        }
    });
}
