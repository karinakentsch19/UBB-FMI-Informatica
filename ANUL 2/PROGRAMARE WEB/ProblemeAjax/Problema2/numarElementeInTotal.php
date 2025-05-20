<?php
$mysqli = new mysqli("localhost", "root", "", "Problema2");
if($mysqli->connect_error) {
  exit('Could not connect');
}

$sql = "SELECT count(*) AS numarElementeInTotal FROM Persoane";

$statement = $mysqli->prepare($sql);
$statement->execute();
$result = $statement->get_result();

$numarElementeInTotal=0;
if($row = $result->fetch_assoc()) {
    $numarElementeInTotal = $row["numarElementeInTotal"];
}

echo json_encode($numarElementeInTotal)
?>
