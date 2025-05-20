<?php
// Get the file path from the GET method
$filePath = $_GET['path'];

// Check if the file exists and is readable
if (file_exists($filePath) && is_readable($filePath)) {
    // Read the content of the file
    $fileContent = file_get_contents($filePath);

    // Send the content as response
    header('Content-Type: text/plain'); // Set appropriate content type
    echo $fileContent;
} else {
    // File does not exist or is not readable
    http_response_code(404); // Set 404 Not Found status code
    echo "File not found or is not readable.";
}
?>