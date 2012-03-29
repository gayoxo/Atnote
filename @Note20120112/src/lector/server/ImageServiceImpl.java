package lector.server;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import lector.client.book.reader.ImageService;
import lector.client.reader.BookBlob;


import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ImageServiceImpl extends RemoteServiceServlet implements
		ImageService {

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

}
