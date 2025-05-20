<?php
$mysqli = new mysqli("localhost", "root", "", "Problema6");
if($mysqli->connect_error) {
  exit('Could not connect');
}

$marime = isset($_POST["marime"]) ? $_POST["marime"] : "";
$tip = isset($_POST["tip"]) ? $_POST["tip"] : "";
$culoare = isset($_POST["culoare"]) ? $_POST["culoare"] : "";

$filtare = "";

if($marime !== "null" && $marime !== "" )
    $filtare .= " marime like '".$marime."'";
if($tip !== "null" && $tip !== "" )
        $filtare .= ($filtare !== "" ? " AND" : "")." tip like '".$tip."'";
if($culoare !== "null" && $culoare !== "")
    $filtare .= ($filtare !== "" ? " AND" : "")." culoare like '".$culoare."'";


if($filtare !== "")
    $filtare = " WHERE ".$filtare;

$sql = "SELECT DISTINCT marime, tip, culoare FROM Haine".$filtare;

$statement = $mysqli->prepare($sql);
$statement->execute();
$result = $statement->get_result();
$haine = array();

while($row = $result->fetch_assoc()) {
    $haina = array("marime" => $row["marime"], "tip" => $row["tip"], "culoare" => $row["culoare"]);
    array_push($haine, $haina);
}

echo json_encode($haine);
?>
