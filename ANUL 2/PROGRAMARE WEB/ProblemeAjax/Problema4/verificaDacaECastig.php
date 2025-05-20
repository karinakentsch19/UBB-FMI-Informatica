<?php
// Receive the array from JavaScript
$array = json_decode($_GET['lista'], true);

if($array[0] == $array[1] && $array[1] == $array[2] && $array[0] != null)
  echo $array[0]." a castigat";
else if($array[3] == $array[4] && $array[4] == $array[5] && $array[3] != null)
   echo $array[3]." a castigat";
else if($array[6] == $array[7] && $array[7] == $array[8] && $array[6] != null)
   echo $array[6]." a castigat";
else if($array[0] == $array[3] && $array[3] == $array[6] && $array[0] != null)
   echo $array[0]." a castigat";
else if($array[1] == $array[4] && $array[4] == $array[7] && $array[1] != null)
   echo $array[1]." a castigat";
else if($array[2] == $array[5] && $array[5] == $array[8] && $array[2] != null)
   echo $array[2]." a castigat";
else if($array[0] == $array[4] && $array[4] == $array[8] && $array[0] != null)
   echo $array[0]." a castigat";
else if($array[2] == $array[4] && $array[4] == $array[6] && $array[2] != null)
   echo $array[2]." a castigat";
else
  echo "Jocul continua";
?>