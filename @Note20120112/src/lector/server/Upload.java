package lector.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lector.client.controler.Constants;
import lector.share.model.BookBlob;
import lector.share.model.UserApp;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

@SuppressWarnings("serial")
public class Upload extends HttpServlet {
	private ImageServiceImpl imageService = new ImageServiceImpl();
	private GWTServiceImpl gwtServiceImpl = new GWTServiceImpl();
	private final static Logger _logger = Logger.getLogger(Upload.class
			.getName());
	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
		ArrayList<String> webLinks = new ArrayList<String>();
		for(int i = 0; i < blobs.size(); i++){
			String webLink = "/serve?blob-key="
				+ (blobs.get(String.valueOf(i)).getKeyString());
			webLinks.add(webLink);
		}
		

		// Iterator itr = blobs.entrySet().iterator();
		// while (itr.hasNext()) {
		// Map.Entry e = (Map.Entry) itr.next();
		// // blobKeys.add((BlobKey) e.getValue());
		// String webLink = "/serve?blob-key="
		// + (((BlobKey) e.getValue()).getKeyString());
		// webLinks.add(webLink);
		//	}

		String pagesCount = String.valueOf(blobs.size());
		String publishedYear = req.getParameter(Constants.BLOB_PUBLISHED_YEAR);
		String title = req.getParameter(Constants.BLOB_TITLE);
		String author = req.getParameter(Constants.BLOB_AUTHOR);
		Long userAppId = Long.parseLong(req
				.getParameter(Constants.BLOB_UPLOADER));
		BookBlob bookBlob = new BookBlob(pagesCount, publishedYear, title,
				author, webLinks, userAppId);
		// BlobInfoFactory blobInfoFactory = new BlobInfoFactory();
		// BlobInfo blobInfo = blobInfoFactory.loadBlobInfo(blobKeys.get(0));

		// String s = blobInfo.getContentType() + "/t" + blobInfo.getCreation()
		// + "/t" + blobInfo.getFilename() + "/t" + blobInfo.getSize()
		// + "/t" + blobInfo.getMd5Hash();
		// System.out.println(s);

		// if (blobKey1 == null) {
		// res.sendRedirect("/");
		// } else {
		// BookBlob bookBlob = new BookBlob(blobKey1.getKeyString(), "prueba");
		// imageService.saveBookBlob(bookBlob);
		// res.sendRedirect("/upload?blob-key=" + blobKey1.getKeyString());
		//
		//
		//
		// }

		imageService.saveBookBlob(bookBlob);

		UserApp upLoader = gwtServiceImpl.loadUserById(userAppId);
		String book = title + " - " + "##" + bookBlob.getId();
		upLoader.getBookIds().add(book);
		gwtServiceImpl.saveUser(upLoader);

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String imageUrl = req.getParameter("imageUrl");
		resp.setHeader("Content-Type", "text/html");

		resp.getWriter().print(imageUrl);
	}
}