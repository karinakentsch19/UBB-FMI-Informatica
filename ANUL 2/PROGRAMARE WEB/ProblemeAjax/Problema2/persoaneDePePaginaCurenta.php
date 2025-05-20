<?php
$mysqli = new mysqli("localhost", "root", "", "Problema2");
if($mysqli->connect_error) {
  exit('Could not connect');
}

$sql = "SELECT DISTINCT nume, prenume, telefon, email FROM Persoane LIMIT ? OFFSET ?";
$paginaCurenta = $_GET["paginaCurenta"];
$numarElementePePagina = $_GET["numarElementePePagina"];

$statement = $mysqli->prepare($sql);
$nr = $paginaCurenta * $numarElementePePagina;
$statement -> bind_param("ii", $numarElementePePagina, $nr);
$statement->execute();
$result = $statement->get_result();
$persoane = array();

while($row = $result->fetch_assoc()) {
    $persoana = array("nume"=>$row["nume"], "prenume"=>$row["prenume"], "telefon"=>$row["telefon"], "email"=>$row["email"]);
    array_push($persoane, $persoana);
}

echo json_encode($persoane)
?>
