package lector.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import lector.client.reader.BookBlob;
import lector.client.reader.ExportObject;
import lector.client.reader.TextSelector;

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
			ArrayList<TextSelector> anchors, int imageWidth, int imageHeight) {
		ArrayList<String> images = new ArrayList<String>();
		if (imageURL.startsWith("/serve")) {
			for (TextSelector anchor : anchors) {
				images.add(imageFromBlob(imageURL, anchor, imageWidth,
						imageHeight));
			}

		} else {
			for (TextSelector anchor : anchors) {
				images.add(imageTransformed(imageURL, anchor, imageWidth,
						imageHeight));
			}
		}

		return produceImagesFormatted(images);
	}

	private String produceImagesFormatted(ArrayList<String> images) {
		String format = "";
		for (String image : images) {
			format += image + "<br />";
		}

		return format;
	}

	public String imageTransformed(String imageURL, TextSelector anchor,
			int imageWidth, int imageHeight) {
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
		Base64 base64codec = new Base64();
		base64codec.encode(newImageData);
		String encodedText = new String(Base64.encodeBase64(newImageData));
		encodedText = "data:" + contentType + ";base64," + encodedText;
		String htmlReturn = "<img src=\"" + encodedText + "\">";
		return htmlReturn;

	}

	public String imageFromBlob(String blobKeyString, TextSelector anchor,
			int imageWidth, int imageHeight) {
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
		String html = "<table>";
		for (ExportObject exportObject : exportObjects) {
			html += "<tr>";
			String imageURL = exportObject.getImageURL();
			ArrayList<TextSelector> anchors = exportObject.getAnnotation()
					.getTextSelectors();
			int imageWidth = exportObject.getWidth();
			int imageHeight = exportObject.getHeight();
			html += "<td>"
					+ produceCutImagesList(imageURL, anchors, imageWidth,
							imageHeight) + "</td><td>";
			html += exportObject.getAnnotation().getComment().getValue()
					+ "</td>";
			ArrayList<String> fileNames = generalAppService
					.getFileNamesByIds(exportObject.getAnnotation()
							.getFileIds());
			for (String fileName : fileNames) {
				html += "<td>" + fileName + ", </td>";
			}
			html += "</tr>";
		}
		html += "</table>";
		return html;
	}

}
