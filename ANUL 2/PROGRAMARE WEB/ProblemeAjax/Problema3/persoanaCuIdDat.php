<?php
$mysqli = new mysqli("localhost", "root", "", "Problema3");
if($mysqli->connect_error) {
  exit('Could not connect');
}

$sql = "SELECT nume, prenume, varsta FROM Persoane WHERE id_persoana = ?";

$id = $_GET["id"];
$statement = $mysqli->prepare($sql);
$statement -> bind_param("i", $id);
$statement->execute();
$result = $statement->get_result();
$persoana = null;

while($row = $result->fetch_assoc()) {
    $nume = $row["nume"];
    $prenume = $row["prenume"];
    $varsta = $row["varsta"];
    $persoana = array("nume"=>$nume, "prenume"=>$prenume, "varsta"=>$varsta);
}

echo json_encode($persoana);
?>
