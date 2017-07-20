<?php
$i = 0;
foreach (getallheaders() as $name => $value) {
    if ($name == "title") {
        $i ++;
	}
    if ($name == "description") {
        $i ++;
	}
    if ($name == "file") {
        $i ++;
	}
    if ($name == "poster") {
        $i ++;
	}
}

if ($i == 4) {
	$title = base64_decode(getallheaders()['title']);
	$description = base64_decode(getallheaders()['description']);
	$file = base64_decode(getallheaders()['file']);
	$poster = base64_decode(getallheaders()['poster']);
	include ($_SERVER['DOCUMENT_ROOT'].'/engine/data/config.php');
	require_once($_SERVER['DOCUMENT_ROOT'].'/engine/api/api.class.php');
	$db -> query("INSERT INTO `android_practic_books`(`title`, `description`, `file`, `poster`) VALUES ('$title', '$description', '$file', '$poster')");	
}
?>