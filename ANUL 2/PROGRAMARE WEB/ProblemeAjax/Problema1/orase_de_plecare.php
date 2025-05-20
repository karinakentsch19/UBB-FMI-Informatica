<?php
$mysqli = new mysqli("localhost", "root", "", "Trenuri");
if($mysqli->connect_error) {
  exit('Could not connect');
}

$sql = "SELECT DISTINCT oras_plecare FROM Rute";

$statement = $mysqli->prepare($sql);
$statement->execute();
$result = $statement->get_result();
$cities = array();

while($row = $result->fetch_assoc()) {
    array_push($cities, $row['oras_plecare']);
}

echo json_encode($cities)
?>
