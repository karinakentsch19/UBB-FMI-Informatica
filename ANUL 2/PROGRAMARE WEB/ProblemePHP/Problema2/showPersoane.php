<?php
	$con = mysqli_connect("localhost", "root", "","Problema2");
	if (!$con) {
		die('Could not connect: ' . mysqli_error());
	}

	$pageLimit = $_GET["persoane"];

	if ($pageLimit == 0) {
		die('Nu putem pune 0 persoane pe o pagina');
	}

	$pattern = '/[0-9]{1,30}$/';
	preg_match($pattern, $pageLimit, $matches, PREG_OFFSET_CAPTURE);
	if (count($matches)==0){
		header("Location: http://localhost/ProblemePHP/Problema2/pb2.html");
	}

	$pageNr = $_GET["nrPagini"];
	$sql = "SELECT * FROM persoane limit " .$pageNr *$pageLimit ."," . $pageLimit .";";
	$result = mysqli_query($con, $sql);

	echo '<form action="showPersoane.php" method="GET">
                  <input type="text" hidden name="nrPagini" value=0> '.
                  '<input list="persoane" name="persoane">
                      <datalist id="persoane">
                          <option value="1">
                          <option value="2">
                          <option value="3">
                          <option value="4">
                          <option value="5">
                      </datalist>
                  <input type="submit" value="Send">
              </form>';
	echo "<hr>";
	echo "<table><tr><th>Nume</th><th>Prenume</th><th>Email</th><th>Telefon</th></tr>";

	while($row = mysqli_fetch_array($result)){
		echo "<tr><td>" .$row["nume"] ."</td><td>" .$row["prenume"] ."</td><td>".$row["email"] ."</td>" . "<td>" .$row["telefon"] . "</td></tr>";
	}
	echo "</table>";
	echo "<hr>";

	echo "<table><tr> <td>";
	$existaPrev = false;
	if($pageNr -1 > -1){
		$existaPrev = true;
	}
	echo "<form action= 'showPersoane.php' method = 'GET'>";
	echo "<input type='text' hidden name='nrPagini' value=" . ($pageNr - 1 ). ">";
	echo "<input type='text' hidden name='persoane' value=" . $pageLimit. ">";
	if($existaPrev == false){
		echo "<input type = 'submit' value = 'Prev' disabled>";
	}
	else {
		echo "<input type = 'submit' value = 'Prev'>";
	}
	echo "</form></td><td>";

	$existaNext = false;
	$sql = "SELECT * FROM persoane limit " .($pageNr+1) *$pageLimit ."," . $pageLimit .";";
	$result = mysqli_query($con, $sql);
	while($row = mysqli_fetch_array($result)){
		$existaNext = true;
		break;
	}

	echo "<form action= 'showPersoane.php' method = 'GET'>";
	echo "<input type='text' hidden name='nrPagini' value=" . ($pageNr + 1 ). ">";
	echo "<input type='text' hidden name='persoane' value=" . $pageLimit. ">";
	if ($existaNext == true){
		echo "<input type = 'submit' value = 'Next'> ";
	}
	else {
		echo "<input type = 'submit' value = 'Next' disabled> ";
	}
	echo "</form></td></tr></table>";
	mysqli_close($con);
?>