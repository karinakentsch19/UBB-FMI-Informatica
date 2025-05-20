<?php
// Receive the array from JavaScript
$array = json_decode($_GET['lista'], true);

// Array to store indices with null values
$nullIndices = [];

// Find indices with null values
foreach ($array as $key => $value) {
    if ($value === null) {
        $nullIndices[] = $key;
    }
}

// If there are no null values in the array
if (empty($nullIndices)) {
    echo "No null values found in the array.";
} else {
    // Select a random index with null value
    $randomIndex = $nullIndices[array_rand($nullIndices)];
    echo $randomIndex;
}
?>