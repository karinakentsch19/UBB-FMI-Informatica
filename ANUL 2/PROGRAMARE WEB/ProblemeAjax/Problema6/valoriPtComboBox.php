<?php
$mysqli = new mysqli("localhost", "root", "", "Problema6");
if($mysqli->connect_error) {
  exit('Could not connect');
}

$sql = "SELECT DISTINCT ".$_GET["column"]." as 'column' FROM Haine";

$statement = $mysqli->prepare($sql);
$statement->execute();
$result = $statement->get_result();
$ids = array();

while($row = $result->fetch_assoc()) {
    array_push($ids,$row["column"]);
}

echo json_encode($ids)
?>
