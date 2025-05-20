<?php
// Function to get directories and files in a directory
function getDirectoryContents($path) {

    $contents = array();

    if (is_dir($path)) {

        if ($handle = opendir($path)) {

            while (($entry = readdir($handle)) !== false) {

                if ($entry != "." && $entry != ".." && !is_hidden($entry)) {
                    $fullPath = $path . "/" . $entry;
                    $type = is_dir($fullPath) ? "directory" : "file";
                    $contents[] = array(
                        "name" => $entry,
                        "type" => $type
                    );
                }
            }

            closedir($handle);
        }
    }

    return $contents;
}

// Function to check if a file or directory is hidden
function is_hidden($file) {
    return (substr($file, 0, 1) == ".");
}

// Read the directory path from the GET request
$path = isset($_GET['path']) ? $_GET['path'] : '';

// If path is provided, get the directory contents
if (!empty($path)) {
    // Get the directory contents
    $directoryContents = getDirectoryContents($path);

    // Encode the directory contents as JSON and output it
    header('Content-Type: application/json');
    echo json_encode($directoryContents);
} else {
    // If path is not provided, output an error message
    echo "Error: Path parameter is missing.";
}
?>