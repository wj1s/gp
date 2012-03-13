<?php ?>
[<?php
$pId = "0";
$pName = "";
if(array_key_exists( 'id',$_REQUEST)) {
	$pId=$_REQUEST['id'];
}
if(array_key_exists('n',$_REQUEST)) {
	$pName=$_REQUEST['n'];
}
if ($pId==null || $pId=="") $pId = "0";
if ($pName==null) $pName = "";
else $pName = $pName.".";

//for ($i=1; $i<9999; $i++) {
//	for ($j=1; $j<999; $j++) {
//
//	}
//}

for ($i=1; $i<5; $i++) {
	$nId = $pId.$i;
	$nName = $pName."n".$i;
	echo "{ id:'".$nId."',	name:'".$nName."',	isParent:".(($i%2)!=0?"true":"false")."}";
	if ($i<4) {
		echo ",";
	}
}
?>]
