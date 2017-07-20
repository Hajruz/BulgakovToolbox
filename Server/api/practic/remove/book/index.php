<?php
$i = 0;
foreach (getallheaders() as $name => $value) {
    if ($name == "id") {
        $i ++;
	}
}
if ($i != 0) {
	$id = getallheaders()['id'];
	include ($_SERVER['DOCUMENT_ROOT'].'/engine/data/config.php');
	require_once($_SERVER['DOCUMENT_ROOT'].'/engine/api/api.class.php');
	$db -> query("DELETE FROM `android_practic_books` WHERE id = $id");
}
?>