<?php
$mysqli = new mysqli("localhost", "root", "", "Problema3");
if($mysqli->connect_error) {
  exit('Could not connect');
}

$sql = "SELECT id_persoana FROM Persoane";

$statement = $mysqli->prepare($sql);
$statement->execute();
$result = $statement->get_result();
$iduri = array();

while($row = $result->fetch_assoc()) {
    $id = $row["id_persoana"];
    array_push($iduri, $id);
}

echo json_encode($iduri)
?>
