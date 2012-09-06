package lector.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesServiceFactory;

public class ParserHTML2RTF {

	
	static final String HEXES = "0123456789ABCDEF";
	
	
	public static String parser(String clear) {
		StringBuffer SB=new StringBuffer();
		int i=0;
		while (i<clear.length())
			{
			char A=clear.charAt(i);
			if (A=='<')
			{
				i++;
				i=evaluarsigno(clear,SB,i);
			}
				else{
				SB.append(A);
				i++;
			}
			}
		return SB.toString();
	}

	private static int evaluarsigno(String clear, StringBuffer sB, int i) {

		char A=clear.charAt(i);
		while (A!='<'&A!='>')
			{
			switch (A) {
			case 'a':
				i++;
				i=evaluarLink(clear, sB, i);
				break;
			case 'i':
				i++;
				i=evaluarimagen(clear, sB, i);
				break;
			default:
				i=evaluarHastaElFinal(clear, sB, i);
				break;
			}
			A=clear.charAt(i);
			}
		i++;
		if (A=='<')
			i=evaluarsigno(clear, sB, i);
					
		return i;
	}

	private static int evaluarHastaElFinal(String clear, StringBuffer sB, int i) {
		char A=clear.charAt(i);
		while (i<clear.length()&&A!='>')
		{	
			i++;
			A=clear.charAt(i);
		}
		return i;
	}

	private static int evaluarimagen(String clear, StringBuffer sB, int i) {
		StringBuffer SB2=new StringBuffer();
		char A=clear.charAt(i);
		while (i<clear.length()&&A!='>')
		{	
			SB2.append(A);
			i++;
			A=clear.charAt(i);
		}
		
		sB.append(StractImage(SB2.toString()));
		return i;
	}

	private static int evaluarLink(String clear, StringBuffer sB, int i) {
		StringBuffer SB2=new StringBuffer();
		char A=clear.charAt(i);
		while (i<clear.length()&&A!='>')
		{	
			SB2.append(A);
			i++;
			A=clear.charAt(i);
		}
		
		sB.append(findLinks(SB2.toString()));
		i=findCierre(clear, sB, i);
		i=evaluarHastaElFinal(clear, sB, i);
		return i;
	}

	private static int findCierre(String clear, StringBuffer sB, int i) {
		char A=clear.charAt(i);
		while (i<clear.length()&&A!='<')
		{	
			i++;
			A=clear.charAt(i);
		}
		return i;
	}

	private static String findLinks(String clear) {
		String[] res=clear.split("href=\"");
		StringBuffer Final=new StringBuffer();
		for (int ii = 1; ii < res.length; ii++) {
			String string = res[ii];
			StringBuffer SB=new StringBuffer();
			String[] res2=string.split("\"");
			int i=0;
			SB.append(res2[i]);
			while (res2[i].endsWith("\\"))
			{
				i++;
				SB.append(res2[i]);
			}
			Final.append("\\par ");
			Final.append("{\\field{\\*\\fldinst{HYPERLINK \"");
			Final.append(SB.toString());
			Final.append("\"}}{\\fldrslt{\\ul\\cf2 ");
			Final.append(SB.toString());
			Final.append("}}}");		
			//http://clientes.orange.es/especiales/app_miOrange.html#" http://clientes.orange.es/especiales/app_miOrange.html#}}}
			
		}
		return Final.toString();
	}
	
	private static String StractImage(String clear) {
		String[] res=clear.split("src=\"");
		StringBuffer Final=new StringBuffer();
		for (int ii = 1; ii < res.length; ii++) {
			String string = res[ii];
			StringBuffer SB=new StringBuffer();
			String[] res2=string.split("\"");
			int i=0;
			SB.append(res2[i]);
			while (res2[i].endsWith("\\"))
			{
				i++;
				SB.append(res2[i]);
			}
			Final.append(produceCutImagesListsmall(SB.toString()));
//			Final.append("\\par ");
//			Final.append("{\\field{\\*\\fldinst{HYPERLINK \"");
//			Final.append();
//			Final.append("\"}}{\\fldrslt{\\ul\\cf2 ");
//			Final.append(SB.toString());
//			Final.append("}}}");		
			//http://clientes.orange.es/especiales/app_miOrange.html#" http://clientes.orange.es/especiales/app_miOrange.html#}}}
			
		}
		return Final.toString();
	}

	private static Object produceCutImagesListsmall(String imageURL) {

		String contentType = getImageContentType(imageURL);
		byte[] oldImageData = getImageData(imageURL);
		Image oldImage = ImagesServiceFactory.makeImage(oldImageData);
		Image newImage = oldImage;
		byte[] newImageData = newImage.getImageData();


			StringBuffer sb = new StringBuffer();

			sb.append("\n{\\*\\shppict{\\pict")
			.append("\\picw").append(newImage.getWidth())
			.append("\\pich").append(newImage.getHeight())
			.append("\\")
			.append(contentType.replaceAll("image/", ""))
			.append("blip\n"); // for PNG images, use \pngblip

			String A=getHex(newImageData);
			
			//int mod= A.length()/64;
			
			sb.append(A);
			
			sb.append("\n}}");
			return sb.toString();
	}
	
	private static String getImageContentType(String urlImage) {

		String contentType = null;
		try {
			URL url = new URL(urlImage);
			URLConnection uc = null;
			uc = url.openConnection();
			contentType = uc.getContentType();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contentType;

	}

	private static byte[] getImageData(String urlImage) {
		byte[] data = null;
		try {
			URL url = new URL(urlImage);
			InputStream inputStream = url.openStream();
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];

			int n = 0;
			while (-1 != (n = inputStream.read(buffer))) {
				output.write(buffer, 0, n);
			}
			inputStream.close();

			// Here's the content of the image...
			data = output.toByteArray();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;

	}
	
	  public static String getHex( byte [] raw ) {
		    if ( raw == null ) {
		      return null;
		    }
		    final StringBuilder hex = new StringBuilder( 2 * raw.length );
		    for ( final byte b : raw ) {
		      hex.append(HEXES.charAt((b & 0xF0) >> 4))
		         .append(HEXES.charAt((b & 0x0F)));
		    }
		    return hex.toString();
		  }
	
}
