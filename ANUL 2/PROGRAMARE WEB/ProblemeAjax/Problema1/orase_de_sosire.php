<?php
$mysqli = new mysqli("localhost", "root", "", "Trenuri");
if($mysqli->connect_error) {
  exit('Could not connect');
}

$sql = "SELECT DISTINCT oras_sosire FROM Rute where oras_plecare like ?";

$statement = $mysqli->prepare($sql);
$start_city = $_GET["oras_plecare"];
$statement->bind_param("s",$start_city);
$statement->execute();
$result = $statement->get_result();
$cities = array();

while($row = $result->fetch_assoc()) {
    array_push($cities, $row['oras_sosire']);
}

echo json_encode($cities)
?>
