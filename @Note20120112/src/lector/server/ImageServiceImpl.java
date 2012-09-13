package lector.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.commons.codec.binary.Base64;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.ImageService;
import lector.client.reader.ExportObject;
import lector.share.model.BookBlob;
import lector.share.model.TextSelector;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ImageServiceImpl extends RemoteServiceServlet implements
		ImageService {
	GWTService generalAppService = new GWTServiceImpl();

	public String getBlobstoreUploadUrl() {
		BlobstoreService blobstoreService = BlobstoreServiceFactory
				.getBlobstoreService();
		return blobstoreService.createUploadUrl("/upload");
	}

	public ArrayList<BookBlob> getBookBlobsByUserId(Long userAppId) {
		EntityManager entityManager = EMF.get().createEntityManager();
		List<BookBlob> list;
		ArrayList<BookBlob> bookBlobs;
		String sql = "SELECT r FROM BookBlob r WHERE r.userApp=" + userAppId;
		list = entityManager.createQuery(sql).getResultList();
		bookBlobs = new ArrayList<BookBlob>(list);

		if (list.isEmpty()) {
			return null;
		} else {
			for (BookBlob bookieBlob : bookBlobs) {
				java.util.ArrayList<String> webLinks = new java.util.ArrayList<String>(
						(java.util.ArrayList<String>) bookieBlob.getWebLinks());
				bookieBlob.getWebLinks().clear();
				bookieBlob.setWebLinks(webLinks);
			}

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return bookBlobs;
	}

	public BookBlob loadBookBlobById(Long id) {
		EntityManager entityManager = EMF.get().createEntityManager();
		List<BookBlob> list;
		ArrayList<BookBlob> bookBlobs;
		String sql = "SELECT r FROM BookBlob r WHERE r.id=" + id;
		list = entityManager.createQuery(sql).getResultList();
		bookBlobs = new ArrayList<BookBlob>(list);

		if (list.isEmpty()) {
			return null;
		} else {
			BookBlob bookieBlob = bookBlobs.get(0);
			java.util.ArrayList<String> webLinks = new java.util.ArrayList<String>(
					(java.util.ArrayList<String>) bookieBlob.getWebLinks());
			bookieBlob.getWebLinks().clear();
			bookieBlob.setWebLinks(webLinks);
		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return bookBlobs.get(0);
	}

	public void saveBookBlob(BookBlob bookBlob) {
		EntityManager entityManager = EMF.get().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();

		entityTransaction.begin();
		if (bookBlob.getId() == null) {
			entityManager.persist(bookBlob);
		} else {

			entityManager.merge(bookBlob);
		}
		entityManager.flush();
		entityTransaction.commit();
		entityManager.close();

	}

	private String produceCutImagesList(String imageURL,
			ArrayList<TextSelector> anchors, int imageWidth, int imageHeight, boolean isRTF) {
		ArrayList<String> images = new ArrayList<String>();
		if (imageURL.startsWith("/serve")) {
			for (TextSelector anchor : anchors) {
				images.add(imageFromBlob(imageURL, anchor, imageWidth,
						imageHeight,isRTF));
			}

		} else {
			for (TextSelector anchor : anchors) {
				images.add(imageTransformed(imageURL, anchor, imageWidth,
						imageHeight, isRTF));
			}
		}

		return produceImagesFormatted(images,isRTF);
	}

	private String produceImagesFormatted(ArrayList<String> images, boolean isRTF) {
		String format = "";
		for (String image : images) {
			if (isRTF) format += image + "\\par";
			else format += image + "<br />";
		}

		return format;
	}

	public String imageTransformed(String imageURL, TextSelector anchor,
			int imageWidth, int imageHeight, boolean isRTF) {
		float height = imageHeight;
		float width = imageWidth; 
		float prop = height / 830;
		float widthResize = (width / prop);
		String contentType = getImageContentType(imageURL);
		byte[] oldImageData = getImageData(imageURL);
		ImagesService imagesService = ImagesServiceFactory.getImagesService();

		Image oldImage = ImagesServiceFactory.makeImage(oldImageData);
		Transform resize = ImagesServiceFactory.makeResize((int) widthResize,
				(int) 830);
		float leftX = anchor.getX() / widthResize;
		float topY = anchor.getY() / 830f;
		float rightX = (anchor.getX() + anchor.getWidth()) / widthResize;
		float bottomY = (anchor.getY() + anchor.getHeight()) / 830f;

		Transform crop = ImagesServiceFactory.makeCrop(leftX, topY, rightX,
				bottomY);
		Image newImage = imagesService.applyTransform(resize, oldImage);
		newImage = imagesService.applyTransform(crop, newImage);
		byte[] newImageData = newImage.getImageData();
		if(isRTF){

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
		Base64 base64codec = new Base64();
		base64codec.encode(newImageData);
		String encodedText = new String(Base64.encodeBase64(newImageData));
		encodedText = "data:" + contentType + ";base64," + encodedText;
		String htmlReturn = "<img src=\"" + encodedText + "\">";
		return htmlReturn;

	}

	static final String HEXES = "0123456789ABCDEF";
	
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
	
	private String imageFromBlob(String blobKeyString, TextSelector anchor,
			int imageWidth, int imageHeight, boolean isRTF) {
		String[] split = blobKeyString.split("=");
		BlobKey blobKey = new BlobKey(split[1]);
		BlobInfoFactory blobInfoFactory = new BlobInfoFactory();
		BlobInfo blobInfo = blobInfoFactory.loadBlobInfo(blobKey);

		float height = imageHeight;
		float width = imageWidth;
		float prop = height / 830;
		float widthResize = (width / prop);

		ImagesService imagesService = ImagesServiceFactory.getImagesService();
		Image oldImage = ImagesServiceFactory.makeImageFromBlob(blobKey);
		Transform resize = ImagesServiceFactory.makeResize((int) widthResize,
				(int) 830);
		float leftX = anchor.getX() / widthResize;
		float topY = anchor.getY() / 830f;
		float rightX = (anchor.getX() + anchor.getWidth()) / widthResize;
		float bottomY = (anchor.getY() + anchor.getHeight()) / 830f;

		Transform crop = ImagesServiceFactory.makeCrop(leftX, topY, rightX,
				bottomY);
		Image newImage = imagesService.applyTransform(resize, oldImage);
		newImage = imagesService.applyTransform(crop, newImage);

		byte[] newImageData = newImage.getImageData();
		if(isRTF){

			StringBuffer sb = new StringBuffer();

			sb.append("\n{\\*\\shppict{\\pict")
			.append("\\picw").append(newImage.getWidth())
			.append("\\pich").append(newImage.getHeight())
			.append("\\")
			.append(blobInfo.getContentType())
			.append("blip\n"); // for PNG images, use \pngblip

			String A=getHex(newImageData);
			
			//int mod= A.length()/64;
			
			sb.append(A);
			
			sb.append("\n}}");
			return sb.toString();
		}
		Base64 base64codec = new Base64();
		base64codec.encode(newImageData);
		String encodedText = new String(Base64.encodeBase64(newImageData));
		encodedText = "data:image/" + blobInfo.getContentType() + ";base64,"
				+ encodedText;

		String htmlReturn = "<img src=" + encodedText + ">";
		return htmlReturn;
	}

	public String logoImage() {
		BookBlob logo = loadBookBlobById(283002l);
		String blobKeyString = logo.getWebLinks().get(0);
		String[] split = blobKeyString.split("=");
		BlobKey blobKey = new BlobKey(split[1]);
		BlobInfoFactory blobInfoFactory = new BlobInfoFactory();
		BlobInfo blobInfo = blobInfoFactory.loadBlobInfo(blobKey);

		ImagesService imagesService = ImagesServiceFactory.getImagesService();
		Image oldImage = ImagesServiceFactory.makeImageFromBlob(blobKey);
		Transform resize = ImagesServiceFactory.makeResize(300, 200);

		Image newImage = imagesService.applyTransform(resize, oldImage);
		byte[] newImageData = newImage.getImageData();
		Base64 base64codec = new Base64();
		base64codec.encode(newImageData);
		String encodedText = new String(Base64.encodeBase64(newImageData));
		encodedText = "data:image/" + blobInfo.getContentType() + ";base64,"
				+ encodedText;

		String htmlReturn = "<img src=" + encodedText + ">";
		return htmlReturn;
	}

	private String getImageContentType(String urlImage) {

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

	private byte[] getImageData(String urlImage) {
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

	public String loadHTMLStringForExport(ArrayList<ExportObject> exportObjects) {
		StringBuffer html = new StringBuffer(
				"<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></head>");
		html.append("<title>Export:");
		html.append(System.nanoTime());
		html.append("</title><body><table width=\"100%\"><tr><td><h1>Export:");
		html.append(System.nanoTime());
		html.append("</h1></td><td align=\"right\">" + logoImage()
				+ "</td></tr></table>");
		for (ExportObject exportObject : exportObjects) {
			html.append("<tr><hr><table align=\"center\" width=\"80%\" border=\"1\" bordercolor=\"blue\">");
			String imageURL = exportObject.getImageURL();
			ArrayList<TextSelector> anchors = exportObject.getAnnotation()
					.getTextSelectors();
			int imageWidth = exportObject.getWidth();
			int imageHeight = exportObject.getHeight();
			html.append("<td rowspan=\"3\"><p>"
					+ produceCutImagesList(imageURL, anchors, imageWidth,
							imageHeight, false) + "</p></td><td colspan=\"2\"><p>");
			String Clear;
			try {
				Clear = new String(exportObject.getAnnotation().getComment()
						.getValue().getBytes(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				Clear = exportObject.getAnnotation().getComment().getValue();
			}
			html.append(Clear + "</p></td></tr><tr>");
			ArrayList<String> fileNames = generalAppService
					.getFileNamesByIds(exportObject.getAnnotation()
							.getFileIds());
			html.append("<td colspan=\"2\"><p>");
			for (String fileName : fileNames) {
				html.append(fileName + ", ");
			}
			html.append("</p></td>");
			html.append("</tr><tr><td><p>" + exportObject.getAuthorName()
					+ "</p></td><td><p>" + exportObject.getDate()
					+ "</p></td></tr>");
			html.append("<tr><td colspan=\"2\"></td></tr>");
			html.append("</table>");
		}
		html.append("</body></html>");

		try {
			String htmlUTF = new String(html.toString().getBytes(), "UTF-8");
			return htmlUTF;
		} catch (UnsupportedEncodingException e) {

			return html.toString();
		}

	}

	public String loadHTMLStringForExportUni(ExportObject exportObject) {
		StringBuffer html = new StringBuffer();
		/*
		 * StringBuffer html = new StringBuffer(
		 * "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></head>"
		 * ); html.append("<title>Export:"); html.append(System.nanoTime());
		 * html
		 * .append("</title><body><table width=\"100%\"><tr><td><h1>Export:");
		 * html.append(System.nanoTime());
		 * html.append("</h1></td><td align=\"right\">"
		 * +logoImage()+"</td></tr></table>"); for (ExportObject exportObject :
		 * exportObjects) {
		 */
		html.append("<tr><hr><table align=\"center\" width=\"80%\" border=\"1\" bordercolor=\"blue\">");
		String imageURL = exportObject.getImageURL();
		ArrayList<TextSelector> anchors = exportObject.getAnnotation()
				.getTextSelectors();
		int imageWidth = exportObject.getWidth();
		int imageHeight = exportObject.getHeight();
		html.append("<td rowspan=\"4\"><p>"
				+ produceCutImagesList(imageURL, anchors, imageWidth,
						imageHeight,false) + "</p></td><td colspan=\"2\"><p>");
		String clear;
		clear = exportObject.getAnnotation().getComment().getValue();
		// try {
		// Clear = new String(exportObject.getAnnotation().getComment()
		// .getValue().getBytes(), "UTF-8");
		// } catch (UnsupportedEncodingException e) {
		// Clear = exportObject.getAnnotation().getComment().getValue();
		// }

		html.append(clear + "</p></td></tr><tr>");
		ArrayList<String> fileNames = generalAppService
				.getFileNamesByIds(exportObject.getAnnotation().getFileIds());
		html.append("<td colspan=\"2\"><p>");
		for (String fileName : fileNames) {
			html.append(fileName + ", ");
		}
		html.append("</p></td>");
		html.append("</tr><tr><td><p>" + exportObject.getAuthorName()
				+ "</p></td><td><p>" + exportObject.getDate()
				+ "</p></td></tr>");
		html.append("<tr><td colspan=\"2\"></td></tr>");
		html.append("</table>");
		/* } */

		// try {
		// String htmlUTF = new String(html.toString().getBytes(), "UTF-8");
		// return htmlUTF;
		// } catch (UnsupportedEncodingException e) {
		//
		// return html.toString();
		// }
		return html.toString();
	}

	public String loadRTFStringForExportUni(ExportObject exportObject) {
		StringBuffer rtf = new StringBuffer();
		String imageURL = exportObject.getImageURL();
		ArrayList<TextSelector> anchors = exportObject.getAnnotation()
				.getTextSelectors();
		int imageWidth = exportObject.getWidth();
		int imageHeight = exportObject.getHeight();
		String clear = exportObject.getAnnotation().getComment().getValue();
		clear=clear.replace("<div>", "\\par ");
		clear=ParserHTML2RTF.parser(clear);
//		String Links =findLinks(clear);
//		String Image =StractImage(clear);
//		clear=clear.replace("<div>", "\\par ");
//		clear=clear.replaceAll("\\<a.*?/a\\>", "");
//		clear=clear.replaceAll("\\<.*?\\>","");
//		clear=clear+Links+Image;
		rtf.append("\\trowd\\trgaph15\\trleft-15\\trqc\\trbrdrl\\brdrdash\\brdrw15\\brdrcf2 \\trbrdrt\\brdrdash\\brdrw15\\brdrcf2 \\trbrdrr\\brdrdash\\brdrw15\\brdrcf2 \\trbrdrb\\brdrdash\\brdrw15\\brdrcf2 \\trpaddl15\\trpaddr15\\trpaddfl3\\trpaddfr3"
				+ "\\clvmgf\\clvertalc\\clbrdrl\\brdrw15\\brdrs\\brdrcf2\\clbrdrt\\brdrw15\\brdrs\\brdrcf2\\clbrdrr\\brdrw15\\brdrs\\brdrcf2\\clbrdrb\\brdrw15\\brdrs\\brdrcf2 \\cellx2066\\clvertalc\\clbrdrl\\brdrw15\\brdrs\\brdrcf2\\clbrdrt\\brdrw15\\brdrs\\brdrcf2\\clbrdrr\\brdrw15\\brdrs\\brdrcf2\\clbrdrb\\brdrw15\\brdrs\\brdrcf2 \\cellx7151\\pard\\intbl\\nowidctlpar\\sb100\\sa100\\cf1\\fs27"
				+ produceCutImagesList(imageURL, anchors, imageWidth,
						imageHeight,true)
				+ "\\cell "
				+ clear
				+ "\\cell\\row\\trowd\\trgaph15\\trleft-15\\trqc\\trbrdrl\\brdrdash\\brdrw15\\brdrcf2 \\trbrdrt\\brdrdash\\brdrw15\\brdrcf2 \\trbrdrr\\brdrdash\\brdrw15\\brdrcf2 \\trbrdrb\\brdrdash\\brdrw15\\brdrcf2 \\trpaddl15\\trpaddr15\\trpaddfl3\\trpaddfr3"
				+ "\\clvmrg\\clvertalc\\clbrdrl\\brdrw15\\brdrs\\brdrcf2\\clbrdrt\\brdrw15\\brdrs\\brdrcf2\\clbrdrr\\brdrw15\\brdrs\\brdrcf2\\clbrdrb\\brdrw15\\brdrs\\brdrcf2 \\cellx2066\\clvertalc\\clbrdrl\\brdrw15\\brdrs\\brdrcf2\\clbrdrt\\brdrw15\\brdrs\\brdrcf2\\clbrdrr\\brdrw15\\brdrs\\brdrcf2\\clbrdrb\\brdrw15\\brdrs\\brdrcf2 \\cellx7151\\pard\\intbl\\nowidctlpar\\cell\\pard\\intbl\\nowidctlpar\\sb100\\sa100"
				+ getFileNames(exportObject.getAnnotation().getFileIds())
				+ "\\cell\\row\\trowd\\trgaph15\\trleft-15\\trqc\\trbrdrl\\brdrdash\\brdrw15\\brdrcf2 \\trbrdrt\\brdrdash\\brdrw15\\brdrcf2 \\trbrdrr\\brdrdash\\brdrw15\\brdrcf2 \\trbrdrb\\brdrdash\\brdrw15\\brdrcf2 \\trpaddl15\\trpaddr15\\trpaddfl3\\trpaddfr3"
				+ "\\clvmrg\\clvertalc\\clbrdrl\\brdrw15\\brdrs\\brdrcf2\\clbrdrt\\brdrw15\\brdrs\\brdrcf2\\clbrdrr\\brdrw15\\brdrs\\brdrcf2\\clbrdrb\\brdrw15\\brdrs\\brdrcf2 \\cellx2066\\clvertalc\\clbrdrl\\brdrw15\\brdrs\\brdrcf2\\clbrdrt\\brdrw15\\brdrs\\brdrcf2\\clbrdrr\\brdrw15\\brdrs\\brdrcf2\\clbrdrb\\brdrw15\\brdrs\\brdrcf2 \\cellx4722\\clvertalc\\clbrdrl\\brdrw15\\brdrs\\brdrcf2\\clbrdrt\\brdrw15\\brdrs\\brdrcf2\\clbrdrr\\brdrw15\\brdrs\\brdrcf2\\clbrdrb\\brdrw15\\brdrs\\brdrcf2 \\cellx7151\\pard\\intbl\\nowidctlpar\\cell\\pard\\intbl\\nowidctlpar\\sb100\\sa100 " + exportObject.getAuthorName() + " \\cell " +exportObject.getDate() + "\\cell\\row\\trowd\\trgaph15\\trleft-15\\trqc\\trbrdrl\\brdrdash\\brdrw15\\brdrcf2 \\trbrdrt\\brdrdash\\brdrw15\\brdrcf2 \\trbrdrr\\brdrdash\\brdrw15\\brdrcf2 \\trbrdrb\\brdrdash\\brdrw15\\brdrcf2 \\trpaddl15\\trpaddr15\\trpaddfl3\\trpaddfr3"
				+ "\\clvmrg\\clvertalc\\clbrdrl\\brdrw15\\brdrs\\brdrcf2\\clbrdrt\\brdrw15\\brdrs\\brdrcf2\\clbrdrr\\brdrw15\\brdrs\\brdrcf2\\clbrdrb\\brdrw15\\brdrs\\brdrcf2 \\cellx2066\\clvertalc\\clbrdrl\\brdrw15\\brdrs\\brdrcf2\\clbrdrt\\brdrw15\\brdrs\\brdrcf2\\clbrdrr\\brdrw15\\brdrs\\brdrcf2\\clbrdrb\\brdrw15\\brdrs\\brdrcf2 \\cellx4722\\clvertalc\\clbrdrr\\brdrw15\\brdrs\\brdrcf2\\clbrdrb\\brdrw15\\brdrs\\brdrcf2 \\cellx7151\\pard\\intbl\\nowidctlpar\\cell\\cf0\\fs24\\cell\\fs20\\cell\\row\\pard\\nowidctlpar\\fs24\\par");
		
		return rtf.toString();
	}

	private String getFileNames(ArrayList<Long> ids) {
		String tags = "";
		ArrayList<String> fileNames = generalAppService.getFileNamesByIds(ids);
		for (String fileName : fileNames) {
			tags += fileName + ", ";
		}
		return tags;
	}

}
