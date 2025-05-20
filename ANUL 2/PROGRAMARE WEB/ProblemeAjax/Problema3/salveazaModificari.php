<?php
$mysqli = new mysqli("localhost", "root", "", "Problema3");
if($mysqli->connect_error) {
  exit('Could not connect');
}

$sql = "UPDATE Persoane set nume = ?, prenume = ?, varsta = ? where id_persoana = ?";

$id = $_POST["id"];
$nume = $_POST["nume"];
$prenume = $_POST["prenume"];
$varsta = $_POST["varsta"];

$statement = $mysqli->prepare($sql);
$statement -> bind_param("ssii", $nume, $prenume, $varsta, $id);
$execute = $statement->execute();

if ($execute === false) {
    exit('Execute failed.');
} else {
    echo "Data updated successfully.";
}
?>
