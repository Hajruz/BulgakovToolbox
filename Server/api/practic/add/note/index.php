<?php
$i = 0;
foreach (getallheaders() as $name => $value) {
    if ($name == "top") {
        $i ++;
	}
    if ($name == "text") {
        $i ++;
	}
    if ($name == "date") {
        $i ++;
	}
    if ($name == "budgetFor") {
        $i ++;
	}
}
if ($i == 4) {
	$top = base64_decode(getallheaders()['top']);
	$text = base64_decode(getallheaders()['text']);
	$date = base64_decode(getallheaders()['date']);
	$budgetFor = base64_decode(getallheaders()['budgetFor']);
	include ($_SERVER['DOCUMENT_ROOT'].'/engine/data/config.php');
	require_once($_SERVER['DOCUMENT_ROOT'].'/engine/api/api.class.php');
	$db -> query("INSERT INTO `android_practic_notes`(`top`, `text`, `date`, `budgetFor`) VALUES ('$top', '$text', '$date', '$budgetFor')");
}
?>