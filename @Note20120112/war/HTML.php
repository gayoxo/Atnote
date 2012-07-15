<?PHP
$number=$_POST[ExportN];

/* En los encabezados indicamos que se trata de un documento de MS-WORD
  y en el nombre de archivo le ponemos la extensión RTF.            */
header('Content-type: application/msword');
header('Content-Disposition: inline; filename='.$number.'.html'); 





/*  Comenzamos a armar el documento  */

$Cesar=$_POST['html'];

$content=utf8($Cesar);

$output=$content;
 
function utf8($txt) {
	$encoding = mb_detect_encoding($txt, 'ASCII,UTF-8,ISO-8859-1');
	if ($encoding == "ISO-8859-1") {
		$txt = utf8_encode($txt);
	}
	return $txt;
}


/*  Enviamos el documento completo a la salida  */
echo $output; 
?>