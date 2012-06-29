<?PHP

/* En los encabezados indicamos que se trata de un documento de MS-WORD
  y en el nombre de archivo le ponemos la extensi�n RTF.            */
header('Content-type: application/msword');
header('Content-Disposition: inline; filename=ejemplo.rtf'); 


/*  Comenzamos a armar el documento  */
$output="{\\rtf1";   //<-- Iniciamos un documento RTF

$output.= "{\\fs48 Internet Explorer 7}"; //<-- Texto de tama�o 48 para el T�tulo
$output.= "\\par ";  //<-- ENTER       

$output.= "{\\fs30 El navegador N�mero 1}"; //<-- Texto de tama�o 30 para el Subt�tulo
$output.= "\\par ";  //<-- ENTER       
$output.= "\\par ";  //<-- ENTER       

/* Parrafo */
$output.= "Windows Internet Explorer (tambi�n conocido antes como Internet Explorer, IE o MSIE) 
es un navegador de Internet producido por Microsoft para su plataforma 
Windows y m�s tarde para Apple Macintosh. ";
$output.= "\\par ";  //<-- ENTER       

/* Parrafo */
$output.= "El 18 de octubre de 2006, Microsoft lanz� la versi�n 
n�mero 7 de Internet Explorer. Entre otros detalles el programa fue 
renombrado a Windows Internet Explorer, como muestra de 
la integraci�n de la aplicaci�n con el Sistema Operativo. 
IE7 esta disponible solamente para Windows XP Service Pack 2, 
Windows Server 2003 Service Pack 1 y ";
$output.= "{\\b Windows Vista}."; //<-- Poner negritas

$output.= "\\par ";  //<-- ENTER
$output.= "\\par ";  //<-- ENTER
$output.= "{\\qr Tomado de wikipedia\\par}"; // <-- Alineado a la derecha

$output.= "{\\i Bill Gates III}"; // <-- It�lica
$output.= "\\par ";  //<-- ENTER
$output.= "{\\ul Fundador de Microsoft}"; // <-- Subrayado

$output.="}"; //<-- Terminador del RTF

/*  Enviamos el documento completo a la salida  */
echo $output; 
?>