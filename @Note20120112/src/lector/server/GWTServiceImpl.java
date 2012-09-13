package lector.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

import java.util.Date;

import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.Basic;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import org.mortbay.util.ajax.JSON;

import lector.client.book.reader.GWTService;
import lector.client.catalogo.client.Catalog;
import lector.client.catalogo.client.DecendanceException;
import lector.client.catalogo.client.Entity;
import lector.client.catalogo.client.File;
import lector.client.catalogo.client.FileException;
import lector.client.catalogo.client.Folder;
import lector.client.catalogo.client.FolderException;
import lector.client.controler.Constants;
import lector.client.language.LanguageNotFoundException;
import lector.client.login.GroupNotFoundException;
import lector.client.login.UserNotFoundException;
import lector.client.reader.AnnotationNotFoundException;
import lector.client.reader.Book;
import lector.client.reader.BookNotFoundException;
import lector.client.reader.GeneralException;
import lector.client.reader.IlegalFolderFusionException;
import lector.client.reader.NullParameterException;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import lector.client.service.AnnotationSchema;
import lector.share.model.Annotation;
import lector.share.model.AnnotationThread;
import lector.share.model.BookBlob;
import lector.share.model.Catalogo;
import lector.share.model.Entry;
import lector.share.model.FileDB;
import lector.share.model.FolderDB;
import lector.share.model.GroupApp;
import lector.share.model.Language;
import lector.share.model.ReadingActivity;
import lector.share.model.TextSelector;
import lector.share.model.UserApp;

public class GWTServiceImpl extends RemoteServiceServlet implements GWTService {

	private static ArrayList<Long> ids;
	private static ArrayList<Long> annotationThreadIds;
	private static List<Long> sonIds; // used in schema generator
	@PersistenceContext(name = "BookReader11Abr01PU")
	private EntityManager entityManager;
	private EntityTransaction entityTransaction;

	public ArrayList<Annotation> getAnnotationsByBookId(String bookId)
			throws GeneralException, AnnotationNotFoundException,
			NullParameterException {
		entityManager = EMF.get().createEntityManager();
		List<Annotation> list;
		ArrayList<Annotation> listAnnotations;
		if (bookId == null) {
			throw new NullParameterException(
					"Parameter aniId cant be null in method loadAnnotationById");
		}
		try {
			String sql = "SELECT a FROM Annotation a WHERE a.bookId='" + bookId
					+ "'";
			list = entityManager.createQuery(sql).getResultList();
			listAnnotations = new ArrayList<Annotation>(list);
		} catch (Exception e) {
			throw new GeneralException(
					"Exception in method loadAnnotationById: " + e.getMessage());
		} finally {
			if (entityManager.isOpen()) {
				entityManager.close();

			}
		}
		if (list.isEmpty()) {
			throw new AnnotationNotFoundException(
					"Annotation not found in method loadAnnotationById");
		}
		return listAnnotations;
	}

	public synchronized Long saveAnnotation(Annotation annotation) {
		ArrayList<Long> fileIdsBeforeUpdate = null;
		ArrayList<Long> fileIdsAfterUpdate = null;
		boolean isNewFile = false;
		EntityManager entityManager;
		EntityTransaction entityTransaction;
		entityManager = EMF.get().createEntityManager();
		entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		now = calendar.getTime();
		annotation.setCreatedDate(now);
		if (annotation.getId() != null) {
			fileIdsBeforeUpdate = getFilesIdsByAnnotationId(annotation.getId());
			entityManager.merge(annotation);
		} else {
			entityManager.persist(annotation);
			isNewFile = true;
		}
		entityManager.flush();
		entityTransaction.commit();
		entityManager.close();
		if (isNewFile) {
			fileIdsBeforeUpdate = annotation.getFileIds();
			if (fileIdsBeforeUpdate != null && !fileIdsBeforeUpdate.isEmpty()) {
				saveAnnotationIdInFiles(annotation.getId(), fileIdsBeforeUpdate);
			}
			fileIdsAfterUpdate = fileIdsBeforeUpdate;
		} else {
			fileIdsAfterUpdate = getFilesIdsByAnnotationId(annotation.getId());
		}

		ArrayList<Long> newFileIds = newFilesInAnnotation(fileIdsBeforeUpdate,
				fileIdsAfterUpdate);
		if (newFileIds != null) {
			saveAnnotationIdInFiles(annotation.getId(), newFileIds);
		}

		return annotation.getId();
	}

	private ArrayList<Long> newFilesInAnnotation(ArrayList<Long> oldFiles,
			ArrayList<Long> oldFilesUpdated) {
		ArrayList<Long> newFileIds = null;
		if (oldFiles != null) {
			if (oldFiles.size() != oldFilesUpdated.size()) {
				newFileIds = new ArrayList<Long>();
				for (int i = 0; i < oldFilesUpdated.size(); i++) {
					if (!(containsInArray(oldFiles, oldFilesUpdated.get(i)))) {
						newFileIds.add(oldFilesUpdated.get(i));
					}
				}
			}
		}

		return newFileIds;
	}

	private boolean containsInArray(ArrayList<Long> oldFileIds, Long newId) {
		for (Long id : oldFileIds) {
			if (id.equals(newId))
				return true;
		}
		return false;
	}

	private ArrayList<Long> getFilesIdsByAnnotationId(Long annotationId) {
		EntityManager entityManager = EMF.get().createEntityManager();
		List<List<Long>> list;
		ArrayList<Long> finalList;
		String sql = "SELECT DISTINCT g.fileIds FROM Annotation g WHERE g.id="
				+ annotationId;
		list = entityManager.createQuery(sql).getResultList();
		if (!list.isEmpty())
			finalList = new ArrayList<Long>(list.get(0));
		else
			finalList = new ArrayList<Long>();
		entityManager.close();
		return finalList;
	}

	private void saveAnnotationIdInFiles(Long annotationId,
			ArrayList<Long> fileIds) {
		EntityManager entityManager;
		EntityTransaction entityTransaction;
		for (int i = 0; i < fileIds.size(); i++) {
			entityManager = EMF.get().createEntityManager();
			entityTransaction = entityManager.getTransaction();
			FileDB fileChanged = entityManager.find(FileDB.class,
					fileIds.get(i));
			if (!(fileChanged.getAnnotationsIds().contains(annotationId))) {
				fileChanged.getAnnotationsIds().add(annotationId);
				// entityTransaction = entityManager.getTransaction();
				entityTransaction.begin();
				entityManager.merge(fileChanged);
				entityTransaction.commit();
			}
		}
	}

	private Annotation swapAnnotation(Annotation annotation) {
		Annotation annotationAux = new Annotation();
		annotationAux.setId(annotation.getId());
		annotationAux.setBookId(annotation.getBookId());
		annotationAux.setUserId(annotation.getUserId());
		annotationAux.setVisibility(annotation.getVisibility());
		annotationAux.setUpdatability(annotation.getUpdatability());
		annotationAux.setTextSelectors(annotation.getTextSelectors());
		annotationAux.setIsPersisted(annotation.isPersisted());
		annotationAux.setPageNumber(annotation.getPageNumber());
		annotationAux.setFileIds(annotation.getFileIds());
		annotationAux.setComment(annotation.getComment());
		annotationAux.setCreatedDate(annotation.getCreatedDate());
		if (annotation.getUserName() == null)
			annotationAux.setUserName("Unknown");
		else
			annotationAux.setUserName(annotation.getUserName());
		return annotationAux;
	}

	private Annotation loadAnnotationById(Long id) {
		EntityManager entityManager;
		entityManager = EMF.get().createEntityManager();
		List<Annotation> annotations = new ArrayList<Annotation>();
		ArrayList<Annotation> annotationList;
		entityManager = EMF.get().createEntityManager();
		String sql = "SELECT a FROM Annotation a WHERE a.id=" + id;
		annotations = entityManager.createQuery(sql).getResultList();
		annotationList = new ArrayList<Annotation>(annotations);
		if (entityManager.isOpen())
			entityManager.close();
		if (annotations.isEmpty()) {
			annotationList.add(null);
		}
		return annotationList.get(0);

	}

	private Annotation loadAnnotationByIdAndReadingActivity(Long id,
			Long readingActivityId) {
		EntityManager entityManager;
		entityManager = EMF.get().createEntityManager();
		List<Annotation> annotations = new ArrayList<Annotation>();
		ArrayList<Annotation> annotationList;
		entityManager = EMF.get().createEntityManager();
		String sql = "SELECT a FROM Annotation a WHERE a.id=" + id
				+ " AND a.readingActivity=" + readingActivityId;
		annotations = entityManager.createQuery(sql).getResultList();
		annotationList = new ArrayList<Annotation>(annotations);
		if (entityManager.isOpen())
			entityManager.close();
		if (annotations.isEmpty()) {
			annotationList.add(null);
		}
		return annotationList.get(0);

	}

	public ArrayList<Annotation> getAnnotationsByPageNumber(Integer pageNumber,
			String bookId, Long readingActivityId) throws GeneralException,
			AnnotationNotFoundException, NullParameterException,
			BookNotFoundException {
		entityManager = EMF.get().createEntityManager();
		List<Annotation> list;
		java.util.ArrayList<Annotation> listAnnotations2 = new ArrayList<Annotation>();

		if (pageNumber == null) {
			throw new NullParameterException(
					"Parameter aniId cant be null in method loadAnnotationById");
		}
		try {
			String sql = "SELECT a FROM Annotation a WHERE a.pageNumber="
					+ pageNumber + " and a.bookId='" + bookId
					+ "' AND a.readingActivity=" + readingActivityId;
			list = entityManager.createQuery(sql).getResultList();
			for (int i = 0; i < list.size(); i++) {
				Annotation annotationAux = swapAnnotation(list.get(i));
				annotationAux.setEditable(true);
				listAnnotations2.add(annotationAux);
				annotationAux.setFileIds(new ArrayList<Long>(annotationAux
						.getFileIds()));
				annotationAux.setTextSelectors(new ArrayList<TextSelector>(
						annotationAux.getTextSelectors()));

			}
		} catch (Exception e) {
			throw new GeneralException(
					"Exception in method loadAnnotationById: " + e.getMessage());
		} finally {
			if (entityManager.isOpen()) {
				entityManager.close();
			}
		}

		if (list.isEmpty()) {
			throw new AnnotationNotFoundException(
					"Annotation not found in method loadAnnotationById");
		}

		return listAnnotations2;

	}

	public int deleteAnnotation(Annotation annotationId)
			throws GeneralException, NullParameterException,
			AnnotationNotFoundException {
		entityManager = EMF.get().createEntityManager();
		Long id = annotationId.getId();
		try {
			Annotation annotationDeleted = entityManager.find(Annotation.class,
					id);
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			entityManager.remove(annotationDeleted);
			entityTransaction.commit();
		} catch (Exception e) {
			entityTransaction.rollback();
			throw new GeneralException("Exception in method saveSubject: "
					+ e.getMessage());
		} finally {
			if (entityManager.isOpen()) {
				entityManager.close();
			}
			deleteAnnotationThreads(getAnnotationThreadsByItsFather(id,
					Constants.THREADFATHERNULLID));
		}
		return 1;
	}

	public ArrayList<Book> getBooks(String query) {
		String cleanQuery = removeSpaces(query);
		URL url;
		URLConnection connection;
		String line;
		StringBuilder builder = new StringBuilder();
		BufferedReader reader;
		ArrayList<Book> books = new ArrayList<Book>();
		Book book = null;
		try {
			url = new URL(
					"https://ajax.googleapis.com/ajax/services/search/books?"
							+ "v=1.0&as_brr=1&q="
							+ cleanQuery
							+ "&rsz=8&start=0&rsz=8&key=ABQIAAAAgGfd0Syld4wI6M_8-PchExQ_l6-Ytnm_KJl3gFahMrxfvqMmehRrB92flZ-iJptRd3l62UsasikVhg");
			connection = url.openConnection();
			connection.addRequestProperty("Referer",
					"http://kido180020783.appspot.com/");
			reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}

			JSONObject json = new JSONObject(builder.toString());
			JSONObject responseObject = json.getJSONObject("responseData");
			JSONArray results = responseObject.getJSONArray("results");
			String authors = results.getJSONObject(0).getString("authors");
			book = new Book();
			book.setAuthor(authors);
			for (int i = 0; i < results.length(); i++) {
				JSONObject jsonBook = results.getJSONObject(i);
				books.add(new Book(jsonBook.getString("authors"), jsonBook
						.getString("bookId"), jsonBook.getString("pageCount"),
						jsonBook.getString("publishedYear"), jsonBook
								.getString("title"), jsonBook
								.getString("tbUrl"), jsonBook
								.getString("unescapedUrl")));
			}

		} catch (MalformedURLException ex) {
			Logger.getLogger(GWTServiceImpl.class.getName()).log(Level.SEVERE,
					null, ex);
		} catch (IOException ex) {
			Logger.getLogger(GWTServiceImpl.class.getName()).log(Level.SEVERE,
					null, ex);
		} catch (JSONException ex) {
			Logger.getLogger(GWTServiceImpl.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		return books;
	}

	public ArrayList<Book> getBooks(String query, int start) {
		String cleanQuery = removeSpaces(query);
		URL url;
		URLConnection connection;
		String line;
		StringBuilder builder = new StringBuilder();
		BufferedReader reader;
		ArrayList<Book> books = new ArrayList<Book>();
		Book book = null;
		try {
			url = new URL(
					"https://ajax.googleapis.com/ajax/services/search/books?"
							+ "v=1.0&as_brr=1&q="
							+ cleanQuery
							+ "&rsz=8&start="
							+ start
							+ "&rsz=8&key=ABQIAAAAgGfd0Syld4wI6M_8-PchExQ_l6-Ytnm_KJl3gFahMrxfvqMmehRrB92flZ-iJptRd3l62UsasikVhg");
			connection = url.openConnection();
			connection.addRequestProperty("Referer",
					"http://kido180020783.appspot.com/");
			reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}

			JSONObject json = new JSONObject(builder.toString());
			JSONObject responseObject = json.getJSONObject("responseData");
			JSONArray results = responseObject.getJSONArray("results");
			String authors = results.getJSONObject(0).getString("authors");
			book = new Book();
			book.setAuthor(authors);
			for (int i = 0; i < results.length(); i++) {
				JSONObject jsonBook = results.getJSONObject(i);
				books.add(new Book(jsonBook.getString("authors"), jsonBook
						.getString("bookId"), jsonBook.getString("pageCount"),
						jsonBook.getString("publishedYear"), jsonBook
								.getString("title"), jsonBook
								.getString("tbUrl"), jsonBook
								.getString("unescapedUrl")));
			}

		} catch (MalformedURLException ex) {
			Logger.getLogger(GWTServiceImpl.class.getName()).log(Level.SEVERE,
					null, ex);
		} catch (IOException ex) {
			Logger.getLogger(GWTServiceImpl.class.getName()).log(Level.SEVERE,
					null, ex);
		} catch (JSONException ex) {
			Logger.getLogger(GWTServiceImpl.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		return books;
	}

	public int getAnnotationsNumberByFileName(String name)
			throws GeneralException, NullParameterException,
			AnnotationNotFoundException {
		int total = 0;
		List<Annotation> annotations;
		entityManager = EMF.get().createEntityManager();
		if (name == null) {
			throw new NullParameterException(
					"Parameter cant be null in method deleteDnServices");
		}
		try {
			String sql = "SELECT a FROM Annotation a where a.fileName='" + name
					+ "'";
			annotations = entityManager.createQuery(sql).getResultList();
			total = annotations.size();
		} catch (Exception e) {
			throw new GeneralException(
					"Exception in method saveAnnotationType: " + e.getMessage());
		} finally {
			if (entityManager.isOpen()) {
				entityManager.close();
			}
		}
		return total;
	}

	public ArrayList<Annotation> getAnnotationsByAnnotationType(
			String annotationTypeName) throws GeneralException,
			NullParameterException, AnnotationNotFoundException {

		entityManager = EMF.get().createEntityManager();
		List<Annotation> list;
		ArrayList<Annotation> listAnnotations;
		if (annotationTypeName == null) {
			throw new NullParameterException(
					"Parameter aniId cant be null in method loadAnnotationById");
		}
		try {
			String sql = "SELECT a FROM Annotation a WHERE a.annotationType='"
					+ annotationTypeName + "'";
			list = entityManager.createQuery(sql).getResultList();
			listAnnotations = new ArrayList<Annotation>(list);
		} catch (Exception e) {
			throw new GeneralException(
					"Exception in method loadAnnotationById: " + e.getMessage());
		} finally {
			if (entityManager.isOpen()) {
				entityManager.close();
			}
		}
		if (list.isEmpty()) {
			throw new AnnotationNotFoundException(annotationTypeName);
		}
		return listAnnotations;

	}

	public ArrayList<Annotation> getAnnotationsByAnnotationTypeAndBook(
			String annotationType, String bookId, Integer pageNumber)
			throws GeneralException, AnnotationNotFoundException,
			NullParameterException, BookNotFoundException {
		entityManager = EMF.get().createEntityManager();
		List<Annotation> list;
		ArrayList<Annotation> listAnnotations;
		if (pageNumber == null) {
			throw new NullParameterException(
					"Parameter aniId cant be null in method loadAnnotationById");
		}
		try {
			String sql = "SELECT a FROM Annotation a WHERE a.pageNumber="
					+ pageNumber + " AND a.bookId='" + bookId
					+ "' AND a.annotationType='" + annotationType + "'";
			list = entityManager.createQuery(sql).getResultList();
			listAnnotations = new ArrayList<Annotation>(list);
		} catch (Exception e) {
			throw new GeneralException(
					"Exception in method loadAnnotationById: " + e.getMessage());
		} finally {
			if (entityManager.isOpen()) {
				entityManager.close();
			}
		}
		if (list.isEmpty()) {
			throw new AnnotationNotFoundException(
					"Annotation not found in method loadAnnotationById");
		}
		return listAnnotations;
	}

	// private void setFileAndAnnotationRelation(Long annotationId, FileDB
	// fileTo) {
	// EntityManager entityManager = EMF.get().createEntityManager();
	// EntityTransaction entityTransaction = entityManager.getTransaction();
	// Annotation annotationChanged = entityManager.find(Annotation.class,
	// annotationId);
	// annotationChanged.setFileId(fileTo.getId());
	// entityTransaction = entityManager.getTransaction();
	// entityTransaction.begin();
	// entityManager.merge(annotationChanged);
	// entityTransaction.commit();
	//
	// FileDB fileDBChanged = entityManager.find(FileDB.class, fileTo.getId());
	// if (!(fileDBChanged.getAnnotationsIds().contains(annotationId))) {
	// fileDBChanged.getAnnotationsIds().add(annotationId);
	// entityTransaction = entityManager.getTransaction();
	// entityTransaction.begin();
	// entityManager.merge(fileDBChanged);
	// entityTransaction.commit();
	//
	// }
	// }

	private FileDB swapFileDB(FileDB fileDB) {
		FileDB fileAux = new FileDB();
		fileAux.setId(fileDB.getId());
		fileAux.setCatalogId(fileDB.getCatalogId());
		fileAux.setName(fileDB.getName());
		fileAux.setFathers(fileDB.getFathers());
		fileAux.setAnnotationsIds(fileDB.getAnnotationsIds());
		return fileAux;
	}

	private Book getBookInGoogleByISBN(String query) {
		String cleanQuery = removeSpaces(query);
		URL url;
		URLConnection connection;
		String line;
		StringBuilder builder = new StringBuilder();
		BufferedReader reader;
		ArrayList<Book> books = new ArrayList<Book>();
		Book book = null;
		try {
			url = new URL(
					"https://ajax.googleapis.com/ajax/services/search/books?"
							+ "as_brr=1&v=1.0&q="
							+ cleanQuery
							+ "&rsz=8&start=3&key=ABQIAAAAgGfd0Syld4wI6M_8-PchExQ_l6-Ytnm_KJl3gFahMrxfvqMmehRrB92flZ-iJptRd3l62UsasikVhg");
			connection = url.openConnection();
			connection.addRequestProperty("Referer",
					"http://kido180020783.appspot.com/");
			reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}

			JSONObject json = new JSONObject(builder.toString());
			JSONObject responseObject = json.getJSONObject("responseData");
			JSONArray results = responseObject.getJSONArray("results");
			String authors = results.getJSONObject(0).getString("authors");
			book = new Book();
			book.setAuthor(authors);
			for (int i = 0; i < results.length(); i++) {
				JSONObject jsonBook = results.getJSONObject(i);
				books.add(new Book(jsonBook.getString("authors"), jsonBook
						.getString("bookId"), jsonBook.getString("pageCount"),
						jsonBook.getString("publishedYear"), jsonBook
								.getString("title"), jsonBook
								.getString("tbUrl"), jsonBook
								.getString("unescapedUrl")));
			}

		} catch (MalformedURLException ex) {
			Logger.getLogger(GWTServiceImpl.class.getName()).log(Level.SEVERE,
					null, ex);
		} catch (IOException ex) {
			Logger.getLogger(GWTServiceImpl.class.getName()).log(Level.SEVERE,
					null, ex);
		} catch (JSONException ex) {
			Logger.getLogger(GWTServiceImpl.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		return books.get(0);
	}

	public Book loadFullBookInGoogle(String query) {
		Book book = getBookInGoogleByISBN(query);
		String cleanGoogleBookId = book.getUrl().substring(33, 45);
		book.setWebLinks(getBookImageInGoole(getBookImageStringInGoogle(cleanGoogleBookId)));
		book.setImagesPath(book.getWebLinks().get(0));
		return book;
	}

	public boolean isIntNumber(String num) {
		try {
			Integer.parseInt(num);

		} catch (NumberFormatException nfe) {

			return false;
		}
		return true;
	}

	public String removeSpaces(String s) {
		StringTokenizer st = new StringTokenizer(s, " ", false);
		String t = "";
		while (st.hasMoreElements()) {
			t += st.nextElement();
		}
		return t;
	}

	public String getBookImageStringInGoogle(String id) {
		URL url;
		URLConnection connection;
		String line;
		StringBuilder builder = new StringBuilder();
		BufferedReader reader;
		try {
			url = new URL(
					"http://books.google.com/ebooks/reader?id="
							+ id
							+ "&pg=PP0&key=ABQIAAAAgGfd0Syld4wI6M_8-PchExQ_l6-Ytnm_KJl3gFahMrxfvqMmehRrB92flZ-iJptRd3l62UsasikVhg");
			connection = url.openConnection();
			connection.addRequestProperty("Referer",
					"http://kido180020783.appspot.com/");
			reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
		} catch (MalformedURLException ex) {
			Logger.getLogger(GWTServiceImpl.class.getName()).log(Level.SEVERE,
					null, ex);
		} catch (IOException ex) {
			Logger.getLogger(GWTServiceImpl.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		return builder.toString();
	}

	public ArrayList<String> getBookImageInGoole(String imagesWithinHTML) {
		ArrayList<String> list = new ArrayList<String>();
		// System.out.println(imagesWithinHTML);
		// Portadas
		String[] PP0 = imagesWithinHTML.split("\"pid\":\"PP0\",\"src\":\"");
		if (PP0.length == 2) {
			String[] PP = PP0[1].split("\"");

			list.add(PP[0].replaceAll("\\\\u0026", "&"));
		}
		String[] PP1 = imagesWithinHTML.split("\"pid\":\"PP1\",\"src\":\"");
		if (PP1.length == 2) {
			String[] PP = PP1[1].split("\"");

			list.add(PP[0].replaceAll("\\\\u0026", "&"));
		}
		// Hojas
		String[] PAI = imagesWithinHTML.split("\"pid\":\"PA1\",\"src\":\"");
		String[] PP = PAI[1].split("\"");

		list.add(PP[0].replaceAll("\\\\u0026", "&"));
		int cont = 2;
		while (PAI.length != 1) {
			PAI = imagesWithinHTML.split("\"pid\":\"PA" + cont
					+ "\",\"src\":\"");
			if (PAI.length != 1) {
				PP = PAI[1].split("\"");
				list.add(PP[0].replaceAll("\\\\u0026", "&"));
			}
			cont++;
		}
		return list;
	}

	// private List<Annotation> annotationsSwapAux(List<Annotation>
	// tempAnnotations) {
	//
	// List<Annotation> annotations = new ArrayList<Annotation>();
	//
	// for (int i = 0; i < tempAnnotations.size(); i++) {
	// annotations.add(tempAnnotations.get(i));
	// }
	// for (int i = 0; i < tempAnnotations.size(); i++) {
	// annotations.get(i).setCreatedDate(
	// tempAnnotations.get(i).getCreatedDate());
	// annotations.get(i).setEditable(tempAnnotations.get(i).isEditable());
	//
	// annotations.get(i).setTextSelector(
	// tempAnnotations.get(i).getTextSelector());
	// annotations.get(i).setPageNumber(
	// tempAnnotations.get(i).getPageNumber());
	// annotations.get(i).setComment(tempAnnotations.get(i).getComment());
	// annotations.get(i).setBookId(tempAnnotations.get(i).getBookId());
	// annotations.get(i).setFileId(tempAnnotations.get(i).getFileId());
	// annotations.get(i).setUserId(tempAnnotations.get(i).getUserId());
	// annotations.get(i).setVisibility(
	// tempAnnotations.get(i).getVisibility());
	// annotations.get(i).setUpdatability(
	// tempAnnotations.get(i).getUpdatability());
	// annotations.get(i).setVisibilityGroupIds(
	// tempAnnotations.get(i).getVisibilityGroupIds());
	// annotations.get(i).setUpdatableGroupIds(
	// tempAnnotations.get(i).getUpdatableGroupIds());
	// annotations.get(i).setIsPersisted(
	// tempAnnotations.get(i).isPersisted());
	// }
	// return annotations;
	// }

	public void moveFile(Long fatherFromId, Long fileId, Long fToId)
			throws GeneralException {
		if (!fatherFromId.equals(fToId) && !(fileId.equals(fToId))) {
			FileDB file = loadFileById2(fileId);
			deleteFileFromParent(file, fatherFromId);
			deleteFatherFromFile(fileId, fatherFromId);

			try {
				addFather(fileId, fToId);
			} catch (FileException fe) {

				throw new GeneralException(
						"Internal error in addFather method: "
								+ fe.getMessage());
			}

		}

		// savePlainFile(file);
	}

	public void moveFolder(Long fatherFromId, Long fFromId, Long fToId)
			throws GeneralException, DecendanceException {
		if (!fFromId.equals(fToId)) {
			FolderDB folderTo = loadFolderById(fToId);
			if (!isFolderDestinationDecendant(fFromId, folderTo)) {
				FolderDB fFrom = loadFolderById(fFromId);
				deleteFolderFromParent(fFrom, fatherFromId);
				deleteFatherFromFolder(fFromId, fatherFromId);
				try {
					addFather(fFromId, fToId);

				} catch (FileException fe) {
					throw new GeneralException(
							"Internal error in addFather method: "
									+ fe.getMessage());
				}
			} else {
				throw new DecendanceException(
						"The file you are trying to move is decentant in its herarchy, the action will not take place ");

			}
			// updateFolder(fFrom);
		}

	}

	private void deleteFolderFromParent(FolderDB folder, Long fatherId) {
		if (!fatherId.equals(Constants.CATALOGID)) {
			FolderDB folderAux = loadFolderById(fatherId);
			folderAux.getEntryIds().remove(folder.getId());
			updateFolder(folderAux);

		} else {
			Catalogo catalogo = loadCatalogById2(folder.getCatalogId());
			catalogo.getEntryIds().remove(folder.getId());
			saveCatalog(catalogo);
		}
	}

	private void deleteFatherFromFolder(Long folderId, Long fatherId) {
		if (fatherId != null) {
			FolderDB folderAux = loadFolderById(folderId);
			folderAux.getFathers().remove(fatherId);
			updateFolder(folderAux);
		}
	}

	private void deletePlainFolder(FolderDB folder) {
		EntityManager entityManager;
		EntityTransaction entityTransaction;
		List<FolderDB> folders;
		entityManager = EMF.get().createEntityManager();
		entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		String sql = "SELECT a FROM FolderDB a WHERE a.id=" + folder.getId();
		folders = entityManager.createQuery(sql).getResultList();
		if (!folders.isEmpty())
			entityManager.remove(folders.get(0));
		entityTransaction.commit();
	}

	// TODO: NO CONSIDERA CUANDO EL FILE SE MUEVE AL ROOT

	private void setNewFatherToFiles(ArrayList<FileDB> fileChildren,
			Long oldFather, Long newFather) throws GeneralException {
		for (int i = 0; i < fileChildren.size(); i++) {
			try {
				FileDB file = fileChildren.get(i);
				file.getFathers().remove(oldFather);
				savePlainFile(file);
				addFather(fileChildren.get(i).getId(), newFather);
			} catch (FileException fe) {
				throw new GeneralException(
						"Internal error in addFather method: "
								+ fe.getMessage());
			}
		}
	}

	private ArrayList<FileDB> getFileChildren(FolderDB folder) {
		EntityManager entityManager;
		List<FileDB> files;
		ArrayList<FileDB> fileList;
		entityManager = EMF.get().createEntityManager();
		String sql = "SELECT a FROM FileDB a WHERE a.fathers=" + folder.getId();
		files = entityManager.createQuery(sql).getResultList();
		fileList = new ArrayList<FileDB>(files);
		if (entityManager.isOpen())
			entityManager.close();
		return fileList;
	}

	private void setNewFatherToFolders(ArrayList<FolderDB> folderChildren,
			Long oldFather, Long newFather) throws GeneralException {
		for (int i = 0; i < folderChildren.size(); i++) {
			try {
				folderChildren.get(i).getFathers().remove(oldFather);
				updateFolder(folderChildren.get(i));
				addFather(folderChildren.get(i).getId(), newFather);
			} catch (FileException fe) {
				throw new GeneralException(
						"Internal error in addFather method: "
								+ fe.getMessage());
			}
		}
	}

	private FileDB swapFile(FileDB file) {
		FileDB fileAux = new FileDB();
		fileAux.setId(file.getId());
		fileAux.setFathers(file.getFathers());
		fileAux.setName(file.getName());
		return fileAux;
	}

	private FolderDB swapFolder(FolderDB folder) {
		FolderDB folderAux = new FolderDB();
		folderAux.setId(folder.getId());
		folderAux.setFathers(folder.getFathers());
		folderAux.setName(folder.getName());
		for (Long entryId : folder.getEntryIds()) {
			folderAux.getEntryIds().add(entryId);
		}
		return folderAux;
	}

	private ArrayList<FolderDB> getFolderChildren(FolderDB folder) {
		EntityManager entityManager;
		List<FolderDB> folders;
		ArrayList<FolderDB> folderList;
		entityManager = EMF.get().createEntityManager();
		String sql = "SELECT a FROM FolderDB a WHERE a.fathers="
				+ folder.getId();
		folders = entityManager.createQuery(sql).getResultList();
		folderList = new ArrayList<FolderDB>(folders);

		if (entityManager.isOpen())
			entityManager.close();

		return folderList;
	}

	// todo: CAMBIADO EL 25-12
	public void deleteFile(Long fileId, Long fatherId) {
		FileDB fileDB = loadFileById2(fileId);
		deleteFileFromParent(fileDB, fatherId);
		fileDB.getFathers().remove(fatherId);
		if (fileDB.getFathers().isEmpty()) {
			deleteFilesInAnnotations(fileId);
			deletePlainFile(fileDB);
		} else {
			savePlainFile(fileDB);
		}

	}

	private int deleteFilesInAnnotations(Long fileId) {
		int total = 0;
		List<Annotation> annotations;
		EntityManager entityManager = EMF.get().createEntityManager();
		try {

			String sql = "SELECT a FROM Annotation a where a.fileIds=" + fileId;
			annotations = entityManager.createQuery(sql).getResultList();
			total = annotations.size();
			for (int i = 0; i < annotations.size(); i++) {
				if (annotations.get(i).getFileIds().contains(fileId)) {
					annotations.get(i).getFileIds().remove(fileId);

					Annotation annotationAux = swapAnnotation(annotations
							.get(i));
					if (annotationAux.getFileIds().size() > 0) {
						saveAnnotation(annotationAux);
					} else {
						deleteAnnotation(annotationAux);
					}

				}
			}

		} catch (Exception e) {
		} finally {
			if (entityManager.isOpen()) {
				entityManager.close();
			}
		}
		return total;
	}

	private void deleteAnnotationInFiles(Long annotationId) {
		int total = 0;
		List<FileDB> fileDBs;
		EntityManager entityManager = EMF.get().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		String sql = "SELECT a FROM FileDB a where a.annotationsIds="
				+ annotationId;
		fileDBs = entityManager.createQuery(sql).getResultList();
		for (int i = 0; i < fileDBs.size(); i++) {
			if (fileDBs.get(i).getAnnotationsIds().contains(annotationId)) {
				fileDBs.get(i).getAnnotationsIds().remove(annotationId);
				total++;
				FileDB fileDBAux = swapFileDB(fileDBs.get(i));
				if (fileDBAux.getAnnotationsIds().size() > 0) {
					savePlainFile(fileDBAux);
				}

			}
		}
	}

	// private void removeAnnotationFromFile(Annotation annotation) {
	// EntityManager entityManager;
	// EntityTransaction entityTransaction;
	// FileDB file = loadFileById2(annotation.getFileId());
	// if (file.getAnnotationsIds().contains(annotation.getId())) {
	// file.getAnnotationsIds().remove(annotation.getId());
	// entityManager = EMF.get().createEntityManager();
	// entityTransaction = entityManager.getTransaction();
	// entityTransaction.begin();
	// entityManager.merge(file);
	// entityTransaction.commit();
	// }
	// }
	/* modified on graph. */
	private void deleteFileFromParent(FileDB file, Long father) {
		if (father.equals(Constants.CATALOGID)) {
			Catalogo catalogo = loadCatalogById2(file.getCatalogId());
			catalogo.getEntryIds().remove(file.getId());
			saveCatalog(catalogo);
		} else {
			FolderDB folder = loadFolderById(father);
			folder.getEntryIds().remove(file.getId());
			updateFolder(folder);
		}
	}

	private void deleteFatherFromFile(Long fileId, Long fatherId) {
		if (fatherId != null) {
			FileDB file = loadFileById2(fileId);
			file.getFathers().remove(fatherId);
			savePlainFile(file);
		}
	}

	private void deletePlainFile(FileDB file) {
		EntityManager entityManager;
		EntityTransaction entityTransaction;
		List<FileDB> files;
		entityManager = EMF.get().createEntityManager();
		entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		String sql = "SELECT a FROM FileDB a WHERE a.id=" + file.getId();
		files = entityManager.createQuery(sql).getResultList();
		entityManager.remove(files.get(0));
		entityTransaction.commit();
		if (entityManager.isOpen()) {
			entityManager.close();
		}
	}

	// NUEVO JULIO Busca el id del catalogo dado el padre.
	// todo: cambiado al 25-12.
	private Long loadCatalogIdByFatherId(Long id) {
		FolderDB fatherFolder = loadFolderById(id);
		while (!fatherFolder.getFathers().isEmpty()) {
			fatherFolder = loadFolderById(fatherFolder.getFathers().get(0));
		}
		return fatherFolder.getCatalogId();
	}

	public Long saveFile(File filesys, Long fatherId) throws FileException {
		Long catalogId;
		catalogId = filesys.getCatalogId();

		if (isFileNameInDB(filesys.getName(), catalogId)
				&& filesys.getID() == null) {
			throw new FileException(
					"The type you are trying to save already exist in the Database, please check the name or reuse it otherwise");
		}
		FileDB file = cloneFile(filesys);
		if (!(file.getFathers().contains(fatherId))) {
			file.getFathers().add(fatherId);
		}
		savePlainFile(file);

		if (fatherId != Constants.CATALOGID) {
			FolderDB fatherFolder = loadFolderById(fatherId);
			if (fatherFolder != null) {
				if (!(fatherFolder.getEntryIds().contains(file.getId()))) {
					fatherFolder.getEntryIds().add(file.getId());
					updateFolder(fatherFolder);
				}
			}

		} else {
			Catalogo catalog = loadCatalogById2(file.getCatalogId());
			if (catalog != null) {
				if (!(catalog.getEntryIds().contains(file.getId()))) {
					catalog.getEntryIds().add(file.getId());
					saveCatalog(catalog);

				}

			}
		}

		return file.getId();
	}

	private boolean isFileNameInDB(String fileName, Long catalogId) {
		EntityManager entityManager = EMF.get().createEntityManager();
		List<FileDB> list;
		boolean flag = true;
		String sql = "SELECT a FROM FileDB a WHERE a.name='" + fileName
				+ "' AND a.catalogId=" + catalogId;
		list = entityManager.createQuery(sql).getResultList();

		if (list.isEmpty()) {
			flag = false;
		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return flag;
	}

	private boolean isFolderBrotherNameInDB(String fileName, Long fatherId,
			Long catalogId) {
		EntityManager entityManager = EMF.get().createEntityManager();
		List<FileDB> list;
		boolean flag = true;
		String sql = "SELECT a FROM FolderDB a WHERE a.name='" + fileName
				+ "' AND a.catalogId=" + catalogId + " AND a.fathers="
				+ fatherId;
		list = entityManager.createQuery(sql).getResultList();

		if (list.isEmpty()) {
			flag = false;
		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return flag;
	}

	private void cloneFileSys(FileDB fileDB, File file) {
		// if (file.getFather() != null) {
		// file.getFather().setID(fileDB.getFatherId());
		// }
		// file.setID(fileDB.getId());

	}

	private void saveFile(FileDB file, Long fatherId) {
		savePlainFile(file);
		if (fatherId != null) {
			FolderDB fatherFolder = loadFolderById(fatherId);
			if (!(fatherFolder.getEntryIds().contains(file.getId()))) {
				fatherFolder.getEntryIds().add(file.getId());
				updateFolder(fatherFolder);
			}
		} else {
			Catalogo catalog = loadCatalogById2(file.getCatalogId());
			if (!(catalog.getEntryIds().contains(file.getId()))) {
				catalog.getEntryIds().add(file.getId());
				saveCatalog(catalog);
			}
		}

	}

	private FileDB cloneFile(File f) {
		FileDB fileDB = new FileDB();
		fileDB.setId(f.getID());
		ArrayList<Long> fileDbs = new ArrayList<Long>();
		if (f.getFathers() != null) {
			for (Entity e : f.getFathers()) {
				fileDbs.add(e.getID());
			}
			fileDB.setFathers(fileDbs);

		}
		fileDB.setCatalogId(f.getCatalogId());
		fileDB.setName(f.getName());
		return fileDB;
	}

	private void updateFolder(FolderDB folder) {
		EntityManager entityManager;
		EntityTransaction entityTransaction;
		entityManager = EMF.get().createEntityManager();
		entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.merge(folder);
		entityManager.flush();
		entityTransaction.commit();
		if (entityManager.isOpen()) {
			entityManager.close();
		}
	}

	private void savePlainFile(FileDB file) {

		EntityManager entityManager;
		EntityTransaction entityTransaction;
		entityManager = EMF.get().createEntityManager();
		entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		if (file.getId() == null) {
			entityManager.persist(file);
		} else {

			entityManager.merge(file);
		}
		entityManager.flush();
		entityTransaction.commit();
		entityManager.close();

	}

	public Long saveFolder(Folder folderSys, Long fatherId)
			throws FolderException {
		Long folderId = 0l;

		if (!fatherId.equals(Constants.CATALOGID)) {
			if (hasTwinBrother(folderSys.getName(), fatherId, false)) {
				throw new FolderException(
						"They Category you are trying to save has a 'twin brother'. Please rename the Category");
			}
		} else {
			if (hasTwinBrother(folderSys.getName(), folderSys.getCatalogId(),
					true)) {
				throw new FolderException(
						"They Category you are trying to save has a 'twin brother'. Please rename the Category");
			}
		}
		try {

			FolderDB folder = cloneFolder(folderSys);
			if (!(folder.getFathers().contains(fatherId))) {
				folder.getFathers().add(fatherId);
			}
			savePlainFolder(folder);
			folderId = folder.getId();
			if (!fatherId.equals(Constants.CATALOGID)) {
				FolderDB fatherFolder = loadFolderById(fatherId);
				if (fatherFolder != null) {
					if (!(fatherFolder.getEntryIds().contains(folder.getId()))) {
						fatherFolder.getEntryIds().add(folder.getId());
						updateFolder(fatherFolder);
					}
				}
			} else {
				Catalogo catalog = loadCatalogById2(folder.getCatalogId());
				if (catalog != null) {
					if (!(catalog.getEntryIds().contains(folder.getId()))) {
						catalog.getEntryIds().add(folder.getId());
						saveCatalog(catalog);
					}
				}
			}
			// cloneFolderSys(folder, folderSys);
		} catch (FileException fe) {
		}
		return folderId;
	}

	private boolean hasTwinBrother(String name, Long fatherId,
			boolean isFatherCatalog) {
		EntityManager entityManager = EMF.get().createEntityManager();
		List<FolderDB> list;
		boolean flag = true;
		String sql = "SELECT a FROM FolderDB a WHERE a.name='" + name + "'";
		list = entityManager.createQuery(sql).getResultList();

		if (list.isEmpty()) {
			flag = false;
		} else if (isFatherCatalog) {
			if (!(list.get(0).getCatalogId().equals(fatherId))) {
				flag = false;
			}
		} else {
			if (!(list.get(0).getFathers().contains(fatherId))) {
				flag = false;
			}
		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}
		return flag;
	}

	private void cloneFolderSys(FolderDB folderDB, Folder folder) {
		// if (folder.getFather() != null) {
		// folder.getFather().setID(folderDB.getFatherId());
		// }
		// folder.setID(folderDB.getId());
	}

	private void saveFolder(FolderDB folder, Long fatherId) {
		savePlainFolder(folder);
		if (fatherId == null) {
			Catalogo catalog = loadCatalogById2(folder.getCatalogId());
			if (!(catalog.getEntryIds().contains(folder.getId()))) {
				catalog.getEntryIds().add(folder.getId());
				saveCatalog(catalog);
			}
		} else {
			FolderDB fatherFolder = loadFolderById(fatherId);
			if (!(fatherFolder.getEntryIds().contains(folder.getId()))) {
				fatherFolder.getEntryIds().add(folder.getId());
				updateFolder(fatherFolder);
			}
		}
	}

	private FolderDB cloneFolder(Folder f) throws FileException {
		FolderDB folderDB = new FolderDB();
		folderDB.setId(f.getID());
		ArrayList<Long> folderDbs = new ArrayList<Long>();
		if (f.getFathers() != null) {
			for (Entity e : f.getFathers()) {
				folderDbs.add(e.getID());
			}
			folderDB.setFathers(folderDbs);
		}
		folderDB.setCatalogId(f.getCatalogId());
		folderDB.setName(f.getName());
		for (int i = 0; i < f.getSons().size(); i++) {
			folderDB.getEntryIds().add(f.getSons().get(i).getID());
		}
		return folderDB;
	}

	private void savePlainFolder(FolderDB folder) {
		EntityManager entityManager;
		EntityTransaction entityTransaction;
		entityManager = EMF.get().createEntityManager();
		entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		if (folder.getId() == null) {
			entityManager.persist(folder);
		} else {
			entityManager.merge(folder);
		}
		entityManager.flush();
		entityTransaction.commit();
	}

	private FolderDB loadFolderById(Long id) {
		EntityManager entityManager;
		entityManager = EMF.get().createEntityManager();
		List<FolderDB> list;
		ArrayList<FolderDB> folders;
		String sql = "SELECT r FROM FolderDB r WHERE r.id=" + id;
		if (id == null)
			return null;
		list = entityManager.createQuery(sql).getResultList();
		folders = new ArrayList<FolderDB>(list);

		if (list.isEmpty()) {
			folders.add(null);
		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return folders.get(0);
	}

	public FileDB loadFileById(Long id) {
		EntityManager entityManager;
		entityManager = EMF.get().createEntityManager();
		List<FileDB> list;
		ArrayList<FileDB> files;
		String sql = "SELECT r FROM FileDB r WHERE r.id=" + id;
		list = entityManager.createQuery(sql).getResultList();
		files = new ArrayList<FileDB>(list);

		FileDB fileDB = null;
		if (!list.isEmpty()) {
			fileDB = files.get(0);
			java.util.ArrayList<Long> annotationsIds = new java.util.ArrayList<Long>(
					(java.util.ArrayList<Long>) fileDB.getAnnotationsIds());
			fileDB.getAnnotationsIds().clear();
			fileDB.setAnnotationsIds(annotationsIds);

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return fileDB;
	}

	private FileDB loadFileById2(Long id) {
		EntityManager entityManager;
		entityManager = EMF.get().createEntityManager();
		List<FileDB> list;
		ArrayList<FileDB> files;
		String sql = "SELECT r FROM FileDB r WHERE r.id=" + id;
		list = entityManager.createQuery(sql).getResultList();
		files = new ArrayList<FileDB>(list);

		if (entityManager.isOpen()) {
			entityManager.close();
		}
		if (list.isEmpty()) {
			return null;
		}
		FileDB fileDB = files.get(0);
		java.util.ArrayList<Long> annotationsIds = new java.util.ArrayList<Long>(
				(java.util.ArrayList<Long>) fileDB.getAnnotationsIds());
		fileDB.getAnnotationsIds().clear();
		fileDB.setAnnotationsIds(annotationsIds);
		return fileDB;
	}

	public FileDB loadFileByNameAndCatalogId(String fileName, Long catalogId) {
		EntityManager entityManager;
		entityManager = EMF.get().createEntityManager();
		List<FileDB> list;
		ArrayList<FileDB> files;
		String sql = "SELECT r FROM FileDB r WHERE r.uppercaseName='"
				+ fileName + "' AND r.catalogId=" + catalogId;
		list = entityManager.createQuery(sql).getResultList();
		files = new ArrayList<FileDB>(list);

		if (list.isEmpty()) {
			return null;
		}

		FileDB fileDB = files.get(0);
		java.util.ArrayList<Long> annotationsIds = new java.util.ArrayList<Long>(
				(java.util.ArrayList<Long>) fileDB.getAnnotationsIds());
		fileDB.getAnnotationsIds().clear();
		fileDB.setAnnotationsIds(annotationsIds);

		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return fileDB;
	}

	public Catalogo loadCatalogById(Long catalogId) {
		EntityManager entityManager;
		List<Catalogo> list;
		ArrayList<Catalogo> listCatalogs;
		entityManager = EMF.get().createEntityManager();
		String sql = "SELECT c FROM Catalogo c WHERE c.id=" + catalogId;
		list = entityManager.createQuery(sql).getResultList();
		listCatalogs = new ArrayList<Catalogo>(list);
		Catalogo catalogo = listCatalogs.get(0);

		ArrayList<Long> entries = new ArrayList<Long>(
				(java.util.ArrayList<Long>) listCatalogs.get(0).getEntryIds());
		catalogo.getEntryIds().clear();
		catalogo.setEntryIds(entries);

		if (entityManager.isOpen()) {
			entityManager.close();
		}
		if (list.isEmpty()) {
			listCatalogs.add(null);
		}
		return catalogo;
	}

	private Catalogo loadCatalogById2(Long catalogId) {
		EntityManager entityManager;
		List<Catalogo> list;
		ArrayList<Catalogo> listCatalogs;
		entityManager = EMF.get().createEntityManager();
		String sql = "SELECT c FROM Catalogo c WHERE c.id=" + catalogId;
		list = entityManager.createQuery(sql).getResultList();
		listCatalogs = new ArrayList<Catalogo>(list);
		Catalogo catalogo = listCatalogs.get(0);

		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return catalogo;
	}

	public void deleteCatalog(Long catalogId) throws GeneralException,
			NullParameterException {
		EntityManager entityManager;
		EntityTransaction entityTransaction;
		entityManager = EMF.get().createEntityManager();
		Catalogo catalogoDeleted = entityManager
				.find(Catalogo.class, catalogId);
		deleteCatalogChildren(catalogoDeleted.getEntryIds());
		int total = removeCatalogFromReadingActivities(catalogId);

		entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.remove(catalogoDeleted);
		entityTransaction.commit();
		if (entityManager.isOpen()) {
			entityManager.close();
		}
	}

	public void saveCatalog(Catalogo catalog) {
		EntityManager entityManager;
		EntityTransaction entityTransaction;
		entityManager = EMF.get().createEntityManager();
		entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		if (catalog.getId() != null) {
			entityManager.merge(catalog);
		} else {
			entityManager.persist(catalog);
		}
		entityManager.flush();
		entityTransaction.commit();
		if (entityManager.isOpen()) {
			entityManager.close();
		}
	}

	public ArrayList<Catalog> getCatalogs() {

		EntityManager entityManager = EMF.get().createEntityManager();
		List<Catalogo> list;
		ArrayList<Catalog> listClientCatalogs = new ArrayList<Catalog>();
		String sql = "SELECT a FROM Catalogo a";
		list = entityManager.createQuery(sql).getResultList();
		buildClientCatalogs(list, listClientCatalogs);

		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return listClientCatalogs;

	}

	private void buildClientCatalogs(List<Catalogo> catalogs,
			ArrayList<Catalog> clientCatalog) {
		for (int i = 0; i < catalogs.size(); i++) {
			clientCatalog.add(new Catalog());
		}
		for (int i = 0; i < catalogs.size(); i++) {
			clientCatalog.get(i).setId(catalogs.get(i).getId());
			clientCatalog.get(i).setPrivate(catalogs.get(i).isIsPrivate());
			clientCatalog.get(i).setProfessorId(
					catalogs.get(i).getProfessorId());
			clientCatalog.get(i).setCatalogName(
					catalogs.get(i).getCatalogName());
		}
	}

	public ArrayList<Entity> getSons(Long fatherId, Long catalogId) {
		EntityManager entityManager;
		List<Entry> list;
		ArrayList<Entry> listEntries;
		entityManager = EMF.get().createEntityManager();
		String sql;
		if (fatherId.equals(Constants.CATALOGID)) {
			sql = "SELECT f FROM FolderDB f WHERE f.catalogId=" + catalogId
					+ " AND f.fathers=" + Constants.CATALOGID;
		} else {
			sql = "SELECT f FROM FolderDB f WHERE f.fathers=" + fatherId;
		}
		list = entityManager.createQuery(sql).getResultList();
		listEntries = new ArrayList<Entry>(list);
		entityManager = EMF.get().createEntityManager();

		if (fatherId.equals(Constants.CATALOGID)) {
			sql = "SELECT f FROM FileDB f WHERE f.catalogId=" + catalogId
					+ " AND f.fathers=" + Constants.CATALOGID;
		} else {
			sql = "SELECT f FROM FileDB f WHERE f.fathers=" + fatherId;
		}

		list = entityManager.createQuery(sql).getResultList();
		listEntries.addAll(list);
		return buildEntities(listEntries);

	}

	/*
	 * chequear, esta comentado el cloneFolderSys, esperando modificación de
	 * FolderSys
	 */
	private ArrayList<Entity> buildEntities(ArrayList<Entry> listEntitys) {
		ArrayList<Entity> sons = new ArrayList<Entity>();
		for (int i = 0; i < listEntitys.size(); i++) {
			Entry son = listEntitys.get(i);
			if (son instanceof FolderDB) {
				Folder sonF = new Folder(son.getName(), son.getId(),
						son.getCatalogId());
				// cloneFolderSys((FolderDB) son, sonF);
				sons.add(sonF);
			} else {
				File sonf = new File(son.getName(), son.getId(),
						son.getCatalogId());
				// cloneFileSys((FileDB) son, sonf);
				sons.add(sonf);
			}
		}
		return sons;
	}

	public boolean isRecentToSave(Annotation annotation) {
		Annotation annotationInDB = loadAnnotationById(annotation.getId());
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		now = calendar.getTime();
		annotation.setCreatedDate(now);
		if (annotation.getCreatedDate().before(annotationInDB.getCreatedDate())) {
			return false;
		} else {
			saveAnnotation(annotation);
			return true;
		}
	}

	/* Modified graph */
	public void deleteFolder(Long folderId, Long fatherId)
			throws GeneralException {
		ids = new ArrayList<Long>();
		FolderDB folder = loadFolderById(folderId);
		folder.getFathers().remove(fatherId);
		deleteFolderFromParent(folder, fatherId);
		if (folder.getFathers().isEmpty()) {
			deepFolderDeletion(folderId);
			deleteEntries(ids, folderId);
			deletePlainFolder(folder);

		} else {

			updateFolder(folder);
		}

	}

	/* tiene que cambiar por deleteEntries2 */
	private void deleteEntries(ArrayList<Long> entriesIds, Long fatherId)
			throws GeneralException {
		FolderDB folder;
		for (int i = 0; i < entriesIds.size(); i++) {
			folder = loadFolderById(entriesIds.get(i));
			if (folder != null) {
				deleteFolder(folder.getId(), fatherId);
				// deleteFolder(folder.getId());
				deletePlainFolder(folder);
			} else {
				deleteFile(entriesIds.get(i), fatherId);
			}
		}
	}

	private void deleteCatalogChildren(ArrayList<Long> catalogChildren)
			throws GeneralException {
		FolderDB folder;
		for (int i = 0; i < catalogChildren.size(); i++) {
			folder = loadFolderById(catalogChildren.get(i));
			if (folder != null) {
				deleteFolder(catalogChildren.get(i), Constants.CATALOGID);
			} else {
				deleteFile(catalogChildren.get(i), Constants.CATALOGID);
			}
		}

	}

	private void deepFolderDeletion(Long folderId) {
		FolderDB folder = loadFolderById(folderId);
		if (folder != null) {
			ArrayList<FolderDB> foldersChildren = getFolderChildren(folder);
			for (int i = 0; i < foldersChildren.size(); i++) {
				deepFolderDeletion(foldersChildren.get(i).getId());

			}
			ArrayList<FileDB> filesChildren = getFileChildren(folder);
			for (int i = 0; i < filesChildren.size(); i++) {
				ids.add(filesChildren.get(i).getId());
			}
		}
		// ids.add(folderId);
	}

	// servicios de usuarios.
	public UserApp loginAuxiliar(String userEmail) throws UserNotFoundException {

		UserApp userApp = new UserApp();
		userApp = loadUserByEmail(userEmail);
		if (userApp == null) { // PROBAR AQUI LANZANDO UNA EXCEPCION CON EL URL
			// COMO MENSAJE, PERO NO FUNCIONA, VER QUE PASA
			userApp = new UserApp();
			userApp.setIsAuthenticated(false);
			return userApp;
		}
		makeAUtilArrayListLong(userApp);
		makeAUtilArrayListString(userApp);
		userApp.setLoggedIn(true);
		userApp.setIsAuthenticated(true);
		return userApp;
	}

	public UserApp login(String requestUri) throws UserNotFoundException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		UserApp userApp = new UserApp();
		if (user != null) {

			userApp = loadUserByEmail(user.getEmail());
			if (userApp == null) { // PROBAR AQUI LANZANDO UNA EXCEPCION CON EL
				// URL COMO MENSAJE, PERO NO FUNCIONA, VER
				// QUE PASA
				/*
				 * Logger.getLogger(GWTServiceImpl.class.getName()).log(Level.INFO
				 * , null, user.getEmail());
				 */
				userApp = new UserApp();
				userApp.setLoggedIn(false);
				userApp.setLoginUrl(userService.createLoginURL(requestUri));
				userApp.setLogoutUrl(userService.createLogoutURL(requestUri));
				userApp.setEmail(user.getEmail());
				userApp.setIsAuthenticated(false);
				return userApp;
			}

			makeAUtilArrayListLong(userApp);
			makeAUtilArrayListString(userApp);
			userApp.setLoggedIn(true);
			userApp.setLogoutUrl(userService.createLogoutURL(requestUri));
			userApp.setLoginUrl(userService.createLoginURL(requestUri));
		} else {

			userApp.setLoggedIn(false);
			userApp.setLoginUrl(userService.createLoginURL(requestUri));
			userApp.setLogoutUrl(userService.createLogoutURL(requestUri));
		}

		return userApp;
	}

	private void makeAUtilArrayListLong(UserApp userApp) {
		ArrayList<Long> groupIds = new ArrayList<Long>();
		for (int i = 0; i < userApp.getGroupIds().size(); i++) {
			Long groupIdAux = userApp.getGroupIds().get(i);
			groupIds.add(groupIdAux);
		}
		userApp.getGroupIds().clear();
		userApp.setGroupIds((ArrayList<Long>) groupIds);
	}

	private void makeAUtilArrayListString(UserApp userApp) {
		ArrayList<String> bookIds = new ArrayList<String>();
		for (int i = 0; i < userApp.getBookIds().size(); i++) {
			String bookIdAux = userApp.getBookIds().get(i);
			bookIds.add(bookIdAux);
		}
		userApp.getBookIds().clear();
		userApp.setBookIds((ArrayList<String>) bookIds);

	}

	public UserApp loadUserById(Long userId) {
		EntityManager entityManager;
		entityManager = EMF.get().createEntityManager();
		List<UserApp> list;
		ArrayList<UserApp> usersApp;
		String sql = "SELECT r FROM UserApp r WHERE r.id=" + userId;
		list = entityManager.createQuery(sql).getResultList();
		usersApp = new ArrayList<UserApp>(list);
		if (entityManager.isOpen()) {
			entityManager.close();
		}
		if (list.isEmpty()) {
			usersApp.add(null);
		}

		UserApp userReturned = usersApp.get(0);
		if (userReturned != null) {
			userReturned.setBookIds(new ArrayList<String>(userReturned
					.getBookIds()));
			userReturned.setGroupIds(new ArrayList<Long>(userReturned
					.getGroupIds()));

		}

		return usersApp.get(0);
	}

	public boolean saveUser(UserApp user) {
		EntityManager entityManager = EMF.get().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		boolean out = false;
		if (user.getId() == null) {
			if (loadUserByEmail(user.getEmail()) == null) {
				entityManager.persist(user);
				out = true;
			}
		} else {
			entityManager.merge(user);
			out = true;
		}
		entityManager.flush();
		entityTransaction.commit();
		if (entityManager.isOpen()) {
			entityManager.close();
		}
		return out;
	}

	public ArrayList<UserApp> getUsersApp() throws UserNotFoundException {
		EntityManager entityManager = EMF.get().createEntityManager();
		List<UserApp> list;
		ArrayList<UserApp> listUserApps;
		String sql = "SELECT a FROM UserApp a WHERE a.profile='Student'";
		list = entityManager.createQuery(sql).getResultList();
		listUserApps = new ArrayList<UserApp>(list);
		if (entityManager.isOpen()) {
			entityManager.close();
		}

		for (int i = 0; i < listUserApps.size(); i++) {
			listUserApps.get(i).setBookIds(
					new ArrayList<String>(listUserApps.get(i).getBookIds()));
			listUserApps.get(i).setGroupIds(
					new ArrayList<Long>(listUserApps.get(i).getGroupIds()));
		}
		return listUserApps;
	}

	public ArrayList<UserApp> getProfessor() throws UserNotFoundException {
		EntityManager entityManager = EMF.get().createEntityManager();
		List<UserApp> list;
		ArrayList<UserApp> listUserApps;
		String sql = "SELECT a FROM UserApp a WHERE a.profile='Professor'";
		list = entityManager.createQuery(sql).getResultList();
		listUserApps = new ArrayList<UserApp>(list);
		if (entityManager.isOpen()) {
			entityManager.close();
		}
		for (int i = 0; i < listUserApps.size(); i++) {
			listUserApps.get(i).setBookIds(
					new ArrayList<String>(listUserApps.get(i).getBookIds()));
			listUserApps.get(i).setGroupIds(
					new ArrayList<Long>(listUserApps.get(i).getGroupIds()));
		}
		return listUserApps;
	}

	// NO PROBADO
	public int deleteUserApp(Long userId) throws GeneralException,
			NullParameterException {
		int total = 0;
		EntityManager entityManager = EMF.get().createEntityManager();
		UserApp userAppDeleted = entityManager.find(UserApp.class, userId);
		total = userAppDeleted.getGroupIds().size();
		if (total > 0) {
			for (int i = 0; i < userAppDeleted.getGroupIds().size(); i++) {
				removeUserFromGroup(userId, userAppDeleted.getGroupIds().get(i));
			}
		}
		deletePrivateAnnotationsOfUser(userId);
		if (getAnnotationThreadsByUserId(userId) != null) {
			deleteAnnotationThreads(getAnnotationThreadsByUserId(userId));
		}

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.remove(userAppDeleted);
		entityTransaction.commit();

		if (entityManager.isOpen()) {
			entityManager.close();
		}
		return total;
	}

	private void deletePrivateAnnotationsOfUser(Long userId) {
		EntityManager entityManager;
		EntityTransaction entityTransaction;
		ArrayList<Annotation> annotationsToBeDeleted = getPrivateAnnotationsByUserId(userId);
		if (!annotationsToBeDeleted.isEmpty()) {

			for (int i = 0; i < annotationsToBeDeleted.size(); i++) {
				deleteAnnotationInFiles(annotationsToBeDeleted.get(i).getId());
				entityManager = EMF.get().createEntityManager();
				entityTransaction = entityManager.getTransaction();
				Annotation annotationToBeDeleted = entityManager
						.find(Annotation.class, annotationsToBeDeleted.get(i)
								.getId());
				entityTransaction.begin();
				entityManager.remove(annotationToBeDeleted);
				entityTransaction.commit();
			}
		}
	}

	private ArrayList<Annotation> getPrivateAnnotationsByUserId(Long userId) {
		EntityManager entityManager = EMF.get().createEntityManager();
		List<Annotation> list;
		ArrayList<Annotation> listUserApps;
		String sql = "SELECT a FROM Annotation a WHERE a.userId=" + userId
				+ "AND a.visibility=false";
		list = entityManager.createQuery(sql).getResultList();
		listUserApps = new ArrayList(list);
		entityManager.close();
		return listUserApps;
	}

	public void removeUserAndGroupRelation(Long userId, Long groupId) {
		EntityManager entityManager;
		EntityTransaction entityTransaction;
		UserApp userApp = loadUserById(userId);
		if (userApp.getGroupIds().contains(groupId)) {
			userApp.getGroupIds().remove(groupId);
			entityManager = EMF.get().createEntityManager();
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			entityManager.merge(userApp);
			entityTransaction.commit();
			GroupApp group = loadGroupById2(groupId);
			if (group.getUsersIds().contains(userId)) {
				group.getUsersIds().remove(userId);
				entityManager = EMF.get().createEntityManager();
				entityTransaction = entityManager.getTransaction();
				entityTransaction.begin();
				entityManager.merge(group);
				entityTransaction.commit();
			}
		}
	}

	public GroupApp loadGroupById(Long groupId) {
		EntityManager entityManager;
		entityManager = EMF.get().createEntityManager();
		List<GroupApp> list;
		ArrayList<GroupApp> groups;
		String sql = "SELECT r FROM GroupApp r WHERE r.id=" + groupId;
		list = entityManager.createQuery(sql).getResultList();
		groups = new ArrayList<GroupApp>(list);
		GroupApp group = groups.get(0);

		ArrayList<Long> userIds = new ArrayList<Long>(
				(java.util.ArrayList<Long>) group.getUsersIds());
		group.getUsersIds().clear();
		group.setUsersIds(userIds);

		if (entityManager.isOpen()) {
			entityManager.close();
		}
		if (list.isEmpty()) {
			groups.add(null);
		}
		return group;
	}

	public GroupApp loadGroupById2(Long groupId) {
		EntityManager entityManager;
		entityManager = EMF.get().createEntityManager();
		List<GroupApp> list;
		ArrayList<GroupApp> groups;
		String sql = "SELECT r FROM GroupApp r WHERE r.id=" + groupId;
		list = entityManager.createQuery(sql).getResultList();
		groups = new ArrayList<GroupApp>(list);
		GroupApp group = groups.get(0);

		if (entityManager.isOpen()) {
			entityManager.close();
		}
		if (list.isEmpty()) {
			groups.add(null);
		}
		return group;
	}

	public GroupApp loadGroupByName(String groupName) {
		EntityManager entityManager;
		entityManager = EMF.get().createEntityManager();
		List<GroupApp> list;
		ArrayList<GroupApp> groups;
		String sql = "SELECT r FROM GroupApp r WHERE r.name='" + groupName
				+ "'";
		list = entityManager.createQuery(sql).getResultList();
		groups = new ArrayList<GroupApp>(list);
		if (entityManager.isOpen()) {
			entityManager.close();
		}
		if (list.isEmpty()) {
			groups.add(null);
		}
		GroupApp groupReturn = groups.get(0);
		if (groupReturn != null) {
			groupReturn.setUsersIds(new ArrayList<Long>(groupReturn
					.getUsersIds()));
			groupReturn.setBookIds(new ArrayList<String>(groupReturn
					.getBookIds()));

		}

		return groupReturn;
	}

	public boolean saveNewGroup(GroupApp groupApp)
			throws GroupNotFoundException {
		ArrayList<GroupApp> groupApps = getGroups();
		boolean found = false;
		for (int i = 0; i < groupApps.size(); i++) {
			if (groupApp.getName().equals(groupApps.get(i).getName())) {
				found = true;
				break;
			}
		}
		if (!found) {
			saveGroup(groupApp);
		}
		return found;
	}

	public void saveGroup(GroupApp groupApp) {
		ArrayList<Long> usersIdsBeforeUpdate = null;
		boolean isNewGroup = false;
		EntityManager entityManager;
		EntityTransaction entityTransaction;
		entityManager = EMF.get().createEntityManager();
		entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		if (groupApp.getId() == null) {
			entityManager.persist(groupApp);
			isNewGroup = true;

		} else {
			usersIdsBeforeUpdate = getUsersIdsByGroupId(groupApp.getId());

			entityManager.merge(groupApp);
		}
		entityManager.flush();
		entityTransaction.commit();
		entityManager.close();
		if (isNewGroup) {
			usersIdsBeforeUpdate = groupApp.getUsersIds();
			if (usersIdsBeforeUpdate != null) {
				saveGroupIdInUsers(groupApp.getId(), usersIdsBeforeUpdate);
			}
		}
		ArrayList<Long> usersIdsAfterUpdate = groupApp.getUsersIds();
		ArrayList<Long> newUsersIds = newUsersinGroup(usersIdsBeforeUpdate,
				usersIdsAfterUpdate);
		if (newUsersIds != null) {
			saveGroupIdInUsers(groupApp.getId(), newUsersIds);
		}
	}

	private void saveGroupIdInUsers(Long groupId, ArrayList<Long> usersIds) {
		EntityManager entityManager;
		EntityTransaction entityTransaction;
		for (int i = 0; i < usersIds.size(); i++) {
			entityManager = EMF.get().createEntityManager();
			entityTransaction = entityManager.getTransaction();
			UserApp userAppChanged = entityManager.find(UserApp.class,
					usersIds.get(i));
			userAppChanged.getGroupIds().add(groupId);
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			entityManager.merge(userAppChanged);
			entityTransaction.commit();
		}
	}

	private ArrayList<Long> newUsersinGroup(ArrayList<Long> oldUsers,
			ArrayList<Long> oldUsersUpdated) {
		ArrayList<Long> newUsersIds = null;
		if (oldUsers != null) {
			if (oldUsers.size() != oldUsersUpdated.size()) {
				newUsersIds = new ArrayList<Long>();
				for (int i = 0; i < oldUsersUpdated.size(); i++) {
					if (!(oldUsers.contains(oldUsersUpdated.get(i)))) {
						newUsersIds.add(oldUsersUpdated.get(i));
					}
				}
			}
		}

		return newUsersIds;
	}

	private ArrayList<Long> getUsersIdsByGroupId(Long groupId) {
		EntityManager entityManager = EMF.get().createEntityManager();
		List<List<Long>> list;
		ArrayList<List<Long>> listcopy;
		ArrayList<Long> listUserApps;
		String sql = "SELECT DISTINCT g.usersIds FROM GroupApp g WHERE g.id="
				+ groupId;
		list = entityManager.createQuery(sql).getResultList();

		listcopy = new ArrayList<List<Long>>(list);

		listUserApps = new ArrayList<Long>(listcopy.get(0));
		entityManager.close();
		return listUserApps;
	}

	// NO PROBADO
	public ArrayList<UserApp> getUsersByGroupId(Long groupId) {
		ArrayList<Long> listUserApps;
		listUserApps = getUsersIdsByGroupId(groupId);
		ArrayList<UserApp> usersByGroup = new ArrayList<UserApp>();
		for (int i = 0; i < listUserApps.size(); i++) {
			UserApp user = loadUserById(listUserApps.get(i));
			usersByGroup.add(user);
		}
		return usersByGroup;
	}

	private void removeUserFromGroup(Long userId, Long groupId) {
		EntityManager entityManager;
		EntityTransaction entityTransaction;
		GroupApp group = loadGroupById(groupId);
		if (group.getUsersIds().contains(userId)) {
			group.getUsersIds().remove(userId);
			entityManager = EMF.get().createEntityManager();
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			entityManager.merge(group);
			entityTransaction.commit();
		}
	}

	public UserApp loadUserByEmail(String email) {
		EntityManager entityManager;
		entityManager = EMF.get().createEntityManager();
		List<UserApp> list;
		ArrayList<UserApp> usersApp;
		String sql = "SELECT r FROM UserApp r WHERE r.email='" + email + "'";
		list = entityManager.createQuery(sql).getResultList();
		usersApp = new ArrayList<UserApp>(list);
		if (entityManager.isOpen()) {
			entityManager.close();

		}
		if (list.isEmpty()) {
			usersApp.add(null);
		}

		UserApp userReturned = usersApp.get(0);
		if (userReturned != null) {
			userReturned.setBookIds(new ArrayList<String>(userReturned
					.getBookIds()));
			userReturned.setGroupIds(new ArrayList<Long>(userReturned
					.getGroupIds()));

		}

		return usersApp.get(0);
	}

	// NO PROBADO
	public ArrayList<GroupApp> getGroups() throws GroupNotFoundException {
		EntityManager entityManager = EMF.get().createEntityManager();
		List<GroupApp> list;
		ArrayList<GroupApp> listGroupApps;
		String sql = "SELECT a FROM GroupApp a";
		list = entityManager.createQuery(sql).getResultList();
		listGroupApps = new ArrayList<GroupApp>(list);
		if (entityManager.isOpen()) {
			entityManager.close();
		}
		for (int i = 0; i < listGroupApps.size(); i++) {
			listGroupApps.get(i).setUsersIds(
					new ArrayList<Long>(listGroupApps.get(i).getUsersIds()));
			listGroupApps.get(i).setBookIds(
					new ArrayList<String>(listGroupApps.get(i).getBookIds()));
		}

		return listGroupApps;
	}

	public int deleteGroup(Long groupId) {
		int total = 0;
		EntityManager entityManager = EMF.get().createEntityManager();
		GroupApp groupToBeDeleted = entityManager.find(GroupApp.class, groupId);
		if (groupToBeDeleted.getUsersIds() != null) {
			total = groupToBeDeleted.getUsersIds().size();
			for (int i = 0; i < total; i++) {
				removeGroupFromUser(groupId, groupToBeDeleted.getUsersIds()
						.get(i));
			}
		}
		removeGroupFromReadingActivity(groupId);
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.remove(groupToBeDeleted);
		entityTransaction.commit();

		if (entityManager.isOpen()) {
			entityManager.close();
		}
		return total;
	}

	public int deleteProfessor(Long professorId) throws GeneralException,
			NullParameterException {
		EntityManager entityManager = EMF.get().createEntityManager();
		UserApp professorDeleted = entityManager.find(UserApp.class,
				professorId);
		int total = removeReadingActivitiesOfProfessor(professorId);
		deletePrivateCatalogsByProfessorId(professorId);
		deletePrivateAnnotationsOfUser(professorId);
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.remove(professorDeleted);
		entityTransaction.commit();
		if (entityManager.isOpen()) {
			entityManager.close();
		}
		return total;
	}

	private void removeGroupFromUser(Long groupId, Long userId) {
		EntityManager entityManager;
		EntityTransaction entityTransaction;
		UserApp userApp = loadUserById(userId);

		if (userApp.getGroupIds().contains(groupId)) {
			userApp.getGroupIds().remove(groupId);
			entityManager = EMF.get().createEntityManager();
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			entityManager.merge(userApp);
			entityTransaction.commit();
		}
	}

	public ArrayList<Annotation> getAnnotationsByPageNumbertByStudentId(
			Integer pageNumber, String bookId, Long studentId,
			Long readingActivityId) {
		EntityManager entityManager = EMF.get().createEntityManager();
		List<Annotation> list;
		java.util.ArrayList<Annotation> listAnnotations2 = new ArrayList<Annotation>();
		String sql = "SELECT a FROM Annotation a WHERE a.pageNumber="
				+ pageNumber + " AND a.bookId='" + bookId
				+ "' AND a.readingActivity=" + readingActivityId;
		list = entityManager.createQuery(sql).getResultList();
		listAnnotations2 = new ArrayList(list);
		ArrayList<Annotation> annotationsSorted = filterAnnotationsByStudent(
				listAnnotations2, studentId);

		if (entityManager.isOpen()) {
			entityManager.close();

		}

		for (int i = 0; i < annotationsSorted.size(); i++) {

			annotationsSorted.get(i).setFileIds(
					new ArrayList<Long>(annotationsSorted.get(i).getFileIds()));
			annotationsSorted.get(i).setTextSelectors(
					new ArrayList<TextSelector>(annotationsSorted.get(i)
							.getTextSelectors()));
		}

		return annotationsSorted;

	}

	private ArrayList<Annotation> filterAnnotationsByStudent(
			ArrayList<Annotation> annotations, Long studentId) {
		ArrayList<Annotation> annotationsSorted = new ArrayList<Annotation>();
		for (int i = 0; i < annotations.size(); i++) {
			Annotation annotation = annotations.get(i);
			if (!(annotation.getUpdatability())
					|| !(annotation.getUserId().equals(studentId))) {

				if (annotation.getVisibility()) {
					if (!annotation.getUpdatability()
							&& annotation.getUserId().equals(studentId)) {
						annotation.setIsEditable(true);

					}
					annotationsSorted.add(annotation);

				} else if (!annotation.getVisibility()
						&& annotation.getUserId().equals(studentId)) {
					annotation.setIsEditable(true);
					annotationsSorted.add(annotation);

				}
			} else {
				annotation.setIsEditable(true);
				annotationsSorted.add(annotation);
			}
		}
		return annotationsSorted;

	}

	// NO PROBADO
	public ArrayList<GroupApp> getGroupsByUserId(Long userId) {
		ArrayList<GroupApp> groups = new ArrayList<GroupApp>();
		UserApp userApp = loadUserById(userId);

		if (!userApp.getGroupIds().isEmpty()) {
			for (int i = 0; i < userApp.getGroupIds().size(); i++) {
				GroupApp groupApp = loadGroupById(userApp.getGroupIds().get(i));
				groups.add(groupApp);
			}
		}
		for (int i = 0; i < groups.size(); i++) {
			groups.get(i).setUsersIds(
					new ArrayList<Long>(groups.get(i).getUsersIds()));
			groups.get(i).setBookIds(
					new ArrayList<String>(groups.get(i).getBookIds()));
		}
		return groups;
	}

	// LANGUAGES

	public ArrayList<String> getLanguagesNames() throws GeneralException,
			LanguageNotFoundException, NullParameterException {
		EntityManager entityManager = EMF.get().createEntityManager();
		List<String> list;
		ArrayList<String> listLanguages;
		try {
			String sql = "SELECT DISTINCT at.nameId FROM Language at";
			list = entityManager.createQuery(sql).getResultList();
			listLanguages = new ArrayList<String>(list);
		} catch (Exception e) {
			throw new GeneralException("Exception in method loadLanguageById: "
					+ e.getMessage());
		} finally {
			if (entityManager.isOpen()) {
				entityManager.close();
			}
		}

		return listLanguages;
	}

	public ArrayList<Language> getLanguages() throws GeneralException,
			LanguageNotFoundException, NullParameterException {
		EntityManager entityManager = EMF.get().createEntityManager();
		List<Language> list;
		ArrayList<Language> listLanguages;
		try {
			String sql = "SELECT at FROM Language at";
			list = entityManager.createQuery(sql).getResultList();
			listLanguages = new ArrayList<Language>(list);
		} catch (Exception e) {
			throw new GeneralException("Exception in method loadLanguageById: "
					+ e.getMessage());
		} finally {
			if (entityManager.isOpen()) {
				entityManager.close();
			}
		}
		return listLanguages;
	}

	public Language loadLanguageByName(String nameId) {
		EntityManager entityManager;
		entityManager = EMF.get().createEntityManager();
		List<Language> list;
		ArrayList<Language> languages;
		String sql = "SELECT r FROM Language r WHERE r.nameId='" + nameId + "'";
		list = entityManager.createQuery(sql).getResultList();
		languages = new ArrayList<Language>(list);
		if (entityManager.isOpen()) {
			entityManager.close();
		}
		if (list.isEmpty()) {
			languages.add(null);
		}
		return languages.get(0);
	}

	public int deleteLanguage(String languageName) {
		EntityManager entityManager = EMF.get().createEntityManager();
		Language languageToBeDeleted = entityManager.find(Language.class,
				languageName);
		int total = removeLanguageFromReadingActivity(languageName);
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.remove(languageToBeDeleted);
		entityTransaction.commit();
		if (entityManager.isOpen()) {
			entityManager.close();
		}
		return total;
	}

	public void saveLanguage(Language language) {
		EntityManager entityManager;
		EntityTransaction entityTransaction;
		entityManager = EMF.get().createEntityManager();
		entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		if (language.getNameId() == null) {
			entityManager.persist(language);
		} else {
			entityManager.merge(language);
		}
		entityManager.flush();
		entityTransaction.commit();
		if (entityManager.isOpen()) {
			entityManager.close();
		}
	}

	// RECENT ACTIVITY

	public void saveReadingActivity(ReadingActivity readingActivity) {
		EntityManager entityManager;
		EntityTransaction entityTransaction;
		entityManager = EMF.get().createEntityManager();
		entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		if (readingActivity.getId() == null) {
			entityManager.persist(readingActivity);
		} else {
			entityManager.merge(readingActivity);
		}
		entityManager.flush();
		entityTransaction.commit();
		if (entityManager.isOpen()) {
			entityManager.close();
		}
	}

	public ReadingActivity loadReadingActivityById(Long id) {
		EntityManager entityManager;
		entityManager = EMF.get().createEntityManager();
		List<ReadingActivity> list;
		ArrayList<ReadingActivity> readingActivitys;
		String sql = "SELECT r FROM ReadingActivity r WHERE r.id=" + id;
		list = entityManager.createQuery(sql).getResultList();
		readingActivitys = new ArrayList<ReadingActivity>(list);
		if (entityManager.isOpen()) {
			entityManager.close();
		}
		if (list.isEmpty()) {
			readingActivitys.add(null);
		}
		return readingActivitys.get(0);
	}

	public void deleteReadingActivity(Long readingActivityId)
			throws GeneralException, NullParameterException,
			AnnotationNotFoundException {
		EntityManager entityManager;
		EntityTransaction entityTransaction;
		int total = removeReadingActivityFromAnnotations(readingActivityId);
		entityManager = EMF.get().createEntityManager();
		ReadingActivity readingActivityDeleted = entityManager.find(
				ReadingActivity.class, readingActivityId);
		entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.remove(readingActivityDeleted);
		entityTransaction.commit();
		if (entityManager.isOpen()) {
			entityManager.close();
		}
	}

	public int removeReadingActivityFromAnnotations(Long readingActivity)
			throws GeneralException, NullParameterException,
			AnnotationNotFoundException {
		int total = 0;
		EntityManager entityManager;
		EntityTransaction entityTransaction;
		ArrayList<Annotation> annotations = getAnnotationsByReadingActivityId(readingActivity);
		if (!annotations.isEmpty()) {
			for (Annotation annotation : annotations) {
				deleteAnnotation(annotation);
			}
			total = annotations.size();
		}
		return total;
	}

	private ArrayList<Annotation> getAnnotationsByReadingActivityId(
			Long readingActivity) {
		EntityManager entityManager;
		entityManager = EMF.get().createEntityManager();
		List<Annotation> list;
		ArrayList<Annotation> readingActivitys;
		String sql = "SELECT r FROM Annotation r WHERE r.readingActivity="
				+ readingActivity;
		list = entityManager.createQuery(sql).getResultList();
		readingActivitys = new ArrayList<Annotation>(list);
		if (entityManager.isOpen()) {
			entityManager.close();
		}
		return readingActivitys;
	}

	public ArrayList<ReadingActivity> getReadingActivityByUserId(Long userId) {
		EntityManager entityManager;
		entityManager = EMF.get().createEntityManager();
		UserApp user = entityManager.find(UserApp.class, userId);
		ArrayList<ReadingActivity> readingActivities = new ArrayList<ReadingActivity>();
		for (Long groupId : user.getGroupIds()) {
			ArrayList<ReadingActivity> readingActivitiesByGroupId = getReadingActivityByGroupId(groupId);
			if (!readingActivitiesByGroupId.isEmpty()) {
				readingActivities.addAll(readingActivitiesByGroupId);
			}
		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return readingActivities;
	}

	private ArrayList<ReadingActivity> getReadingActivityByGroupId(Long groupId) {
		EntityManager entityManager;
		entityManager = EMF.get().createEntityManager();
		List<ReadingActivity> list;
		ArrayList<ReadingActivity> readingActivitys;
		String sql = "SELECT r FROM ReadingActivity r WHERE r.groupId="
				+ groupId;
		list = entityManager.createQuery(sql).getResultList();
		readingActivitys = new ArrayList<ReadingActivity>(list);

		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return readingActivitys;
	}

	private void deletePrivateCatalogsByProfessorId(Long professorId)
			throws GeneralException, NullParameterException {
		ArrayList<Catalogo> catalogos = getPrivateCatalogsByProfessorId(professorId);
		for (int i = 0; i < catalogos.size(); i++) {
			deleteCatalog(catalogos.get(i).getId());
		}

	}

	public ArrayList<Catalogo> getPrivateCatalogsByProfessorId(Long professorId) {

		EntityManager entityManager = EMF.get().createEntityManager();
		List<Catalogo> list;
		ArrayList<Catalogo> listClientCatalogs = new ArrayList<Catalogo>();
		String sql = "SELECT a FROM Catalogo a WHERE a.professorId="
				+ professorId + "AND a.isPrivate=true";
		list = entityManager.createQuery(sql).getResultList();
		listClientCatalogs = new ArrayList<Catalogo>(list);
		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return listClientCatalogs;
	}

	private ArrayList<Catalogo> getCatalogsByProfessorId(Long professorId) {

		EntityManager entityManager = EMF.get().createEntityManager();
		List<Catalogo> list;
		ArrayList<Catalogo> listClientCatalogs = new ArrayList<Catalogo>();
		String sql = "SELECT a FROM Catalogo a WHERE a.professorId="
				+ professorId;
		list = entityManager.createQuery(sql).getResultList();
		listClientCatalogs = new ArrayList<Catalogo>(list);
		// backed.ArrayList magia para tranformar a util.java.ArrayList
		for (int i = 0; i < listClientCatalogs.size(); i++) {
			Catalogo catalogo = listClientCatalogs.get(i);
			ArrayList<Long> entries = new ArrayList<Long>(
					(java.util.ArrayList<Long>) catalogo.getEntryIds());
			catalogo.getEntryIds().clear();
			catalogo.setEntryIds(entries);
		}

		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return listClientCatalogs;
	}

	public ArrayList<Catalogo> getVisbibleCatalogsByProfessorId(Long professorId) {

		EntityManager entityManager = EMF.get().createEntityManager();
		List<Catalogo> list;
		ArrayList<Catalogo> listClientCatalogs = getPrivateCatalogsByProfessorId(professorId);
		ArrayList<Catalogo> listQueryCatalogs = new ArrayList<Catalogo>();
		String sql = "SELECT a FROM Catalogo a WHERE a.isPrivate=false";
		list = entityManager.createQuery(sql).getResultList();
		listQueryCatalogs = new ArrayList<Catalogo>(list);
		listClientCatalogs.addAll(listQueryCatalogs);
		// backed.ArrayList magia para tranformar a util.java.ArrayList
		for (int i = 0; i < listClientCatalogs.size(); i++) {
			Catalogo catalogo = listClientCatalogs.get(i);
			ArrayList<Long> entries = new ArrayList<Long>(
					(java.util.ArrayList<Long>) catalogo.getEntryIds());
			catalogo.getEntryIds().clear();
			catalogo.setEntryIds(entries);
		}

		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return listClientCatalogs;
	}

	private void removeGroupFromReadingActivity(Long groupId) {
		EntityManager entityManager;
		EntityTransaction entityTransaction;
		ArrayList<ReadingActivity> readingActivities = getReadingActivityByGroupId(groupId);
		if (!readingActivities.isEmpty()) {
			for (ReadingActivity readingActivity : readingActivities) {
				readingActivity.setGroupId(null);
				entityManager = EMF.get().createEntityManager();
				entityTransaction = entityManager.getTransaction();
				entityTransaction.begin();
				entityManager.merge(readingActivity);
				entityTransaction.commit();
			}
		}

	}

	private int removeLanguageFromReadingActivity(String languageName) {
		int total = 0;
		EntityManager entityManager;
		EntityTransaction entityTransaction;
		ArrayList<ReadingActivity> readingActivities = getReadingActivityByLanguageName(languageName);
		if (!readingActivities.isEmpty()) {
			for (ReadingActivity readingActivity : readingActivities) {
				readingActivity.setLanguageName("");
				entityManager = EMF.get().createEntityManager();
				entityTransaction = entityManager.getTransaction();
				entityTransaction.begin();
				entityManager.merge(readingActivity);
				entityTransaction.commit();
			}
			total = readingActivities.size();
		}
		return total;
	}

	private int removeCatalogFromReadingActivities(Long catalogId) {
		int total = 0;
		EntityManager entityManager;
		EntityTransaction entityTransaction;
		ArrayList<ReadingActivity> readingActivities = getReadingActivitiesByCatalogId(catalogId);
		if (!readingActivities.isEmpty()) {
			for (ReadingActivity readingActivity : readingActivities) {

				if (readingActivity.getCatalogId() != null
						&& readingActivity.getCatalogId().equals(catalogId)) {
					readingActivity.setCatalogId(null);
				} else {
					readingActivity.setOpenCatalogId(null);
				}

				entityManager = EMF.get().createEntityManager();
				entityTransaction = entityManager.getTransaction();
				entityTransaction.begin();
				entityManager.merge(readingActivity);
				entityTransaction.commit();
			}
			total = readingActivities.size();
		}
		return total;
	}

	private ArrayList<ReadingActivity> getReadingActivitiesByCatalogId(
			Long catalogId) {
		EntityManager entityManager;
		entityManager = EMF.get().createEntityManager();
		List<ReadingActivity> list;
		List<ReadingActivity> list2;
		ArrayList<ReadingActivity> readingActivitys;
		String sql = "SELECT r FROM ReadingActivity r WHERE r.catalogId="
				+ catalogId;
		list = entityManager.createQuery(sql).getResultList();
		sql = "SELECT r FROM ReadingActivity r WHERE r.openCatalogId="
				+ catalogId;
		list2 = entityManager.createQuery(sql).getResultList();
		readingActivitys = new ArrayList<ReadingActivity>(list);
		readingActivitys.addAll(list2);
		if (entityManager.isOpen()) {
			entityManager.close();
		}
		return readingActivitys;
	}

	private int removeReadingActivitiesOfProfessor(Long professorId) {
		int total = 0;
		EntityManager entityManager;
		EntityTransaction entityTransaction;
		ArrayList<ReadingActivity> readingActivities = getReadingActivityByProfessorId(professorId);
		if (!readingActivities.isEmpty()) {
			for (ReadingActivity readingActivity : readingActivities) {
				entityManager = EMF.get().createEntityManager();
				entityTransaction = entityManager.getTransaction();
				ReadingActivity readingActivityToBeRemoved = entityManager
						.find(ReadingActivity.class, readingActivity.getId());
				entityTransaction.begin();
				entityManager.remove(readingActivityToBeRemoved);
				entityTransaction.commit();
				if (entityManager.isOpen()) {
					entityManager.close();
				}
			}
			total = readingActivities.size();
		}
		return total;
	}

	public ArrayList<ReadingActivity> getReadingActivityByProfessorId(
			Long professorId) {
		EntityManager entityManager;
		entityManager = EMF.get().createEntityManager();
		List<ReadingActivity> list;
		ArrayList<ReadingActivity> readingActivitys;
		String sql = "SELECT r FROM ReadingActivity r WHERE r.professorId="
				+ professorId;
		list = entityManager.createQuery(sql).getResultList();
		readingActivitys = new ArrayList<ReadingActivity>(list);

		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return readingActivitys;
	}

	private ArrayList<ReadingActivity> getReadingActivityByLanguageName(
			String languageName) {
		EntityManager entityManager;
		entityManager = EMF.get().createEntityManager();
		List<ReadingActivity> list;
		ArrayList<ReadingActivity> readingActivitys;
		String sql = "SELECT r FROM ReadingActivity r WHERE r.languageName='"
				+ languageName + "'";
		list = entityManager.createQuery(sql).getResultList();
		readingActivitys = new ArrayList<ReadingActivity>(list);
		if (entityManager.isOpen()) {
			entityManager.close();
		}
		return readingActivitys;
	}

	private ArrayList<ReadingActivity> getReadingActivities() {
		EntityManager entityManager;
		entityManager = EMF.get().createEntityManager();
		List<ReadingActivity> list;
		ArrayList<ReadingActivity> readingActivitys;
		String sql = "SELECT r FROM ReadingActivity r";
		list = entityManager.createQuery(sql).getResultList();
		readingActivitys = new ArrayList<ReadingActivity>(list);
		if (entityManager.isOpen()) {
			entityManager.close();
		}
		return readingActivitys;
	}
	public void removeFileFromAnnotation(Long annotationId, Long fileId) {
		EntityManager entityManager = EMF.get().createEntityManager();
		EntityTransaction entityTransaction;
		Annotation annotation = entityManager.find(Annotation.class,
				annotationId);
		if (annotation.getFileIds().contains(fileId)) {
			annotation.getFileIds().remove(fileId);
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			entityManager.merge(annotation);
			entityTransaction.commit();
			entityManager = EMF.get().createEntityManager();
			entityTransaction = entityManager.getTransaction();
			FileDB fileDB = entityManager.find(FileDB.class, fileId);
			if (fileDB.getAnnotationsIds().contains(annotationId)) {
				fileDB.getAnnotationsIds().remove(annotationId);
				// entityManager = EMF.get().createEntityManager();
				entityTransaction.begin();
				entityManager.merge(fileDB);
				entityTransaction.commit();
			}
		}

	}

	public ArrayList<FileDB> getFilesByIds(ArrayList<Long> ids) {
		ArrayList<FileDB> fileDBs = new ArrayList<FileDB>();
		for (int i = 0; i < ids.size(); i++) {
			FileDB FB = loadFileById(ids.get(i));
			fileDBs.add(FB);
		}

		return fileDBs;
	}

	public ArrayList<String> getFileNamesByIds(ArrayList<Long> ids){
		ArrayList<String> fileNames = new ArrayList<String>();
		for (int i = 0; i < ids.size(); i++) {
			String fileName = loadFileNameById(ids.get(i));
			fileNames.add(fileName);
		}

		return fileNames;
		
	}

	private String loadFileNameById(Long id) {
		EntityManager entityManager = EMF.get().createEntityManager();
		List<String> list;
		ArrayList<String> finalList;
		String sql = "SELECT DISTINCT g.name FROM FileDB g WHERE g.id=" + id;
		list = entityManager.createQuery(sql).getResultList();
		finalList = new ArrayList<String>(list);
		String fileName = null;
		if (!finalList.isEmpty()) {
			fileName = finalList.get(0);
		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}
		return fileName;

	}

	private FileDB loadFileDBByNameAndCatalogId(String name, Long id) {
		EntityManager entityManager;
		entityManager = EMF.get().createEntityManager();
		List<FileDB> list;
		ArrayList<FileDB> files;
		String sql = "SELECT f FROM FileDB f WHERE f.name='" + name
				+ "' AND f.catalogId=" + id;
		list = entityManager.createQuery(sql).getResultList();
		files = new ArrayList<FileDB>(list);

		FileDB fileDB = null;
		if (!list.isEmpty()) {
			fileDB = files.get(0);
			java.util.ArrayList<Long> annotationsIds = new java.util.ArrayList<Long>(
					(java.util.ArrayList<Long>) fileDB.getAnnotationsIds());
			fileDB.getAnnotationsIds().clear();

			fileDB.setAnnotationsIds(annotationsIds);

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return fileDB;
	}

	public ArrayList<FileDB> getFilesByNameAndCatalogId(
			ArrayList<String> names, Long catalogId) {
		ArrayList<FileDB> fileDBs = new ArrayList<FileDB>();
		for (int i = 0; i < names.size(); i++) {
			fileDBs.add(loadFileDBByNameAndCatalogId(names.get(i), catalogId));
		}

		return fileDBs;
	}

	public ArrayList<Long> getEntriesIdsByNames(ArrayList<String> names,
			Long catalogTeacher, Long catalogOpen) {
		/*
		 * buscar tipos con los nombres/* buscar las carpetas con names añadir a
		 * la lista los tipos obtenidos añadir a la lista funcion-recursiva
		 * {lista de carpetas}
		 */
		EntityManager entityManager;
		ArrayList<Long> listFiles = new ArrayList<Long>();
		ArrayList<Long> listFolder = new ArrayList<Long>();
		entityManager = EMF.get().createEntityManager();
		String sql;
		for (int i = 0; i < names.size(); i++) {
			Entry inputFile = loadFileByNameAndCatalogId(names.get(i)
					.toUpperCase(), catalogTeacher);
			Entry inputFolder = loadFolderByNameAndCatalogId(names.get(i)
					.toUpperCase(), catalogTeacher);
			if (inputFile != null) {
				listFiles.add(inputFile.getId());
			}
			if (inputFolder != null) {
				listFolder.add(inputFolder.getId());

			}
			inputFile = loadFileByNameAndCatalogId(names.get(i).toUpperCase(),
					catalogOpen);
			inputFolder = loadFolderByNameAndCatalogId(names.get(i)
					.toUpperCase(), catalogOpen);
			if (inputFile != null) {
				listFiles.add(inputFile.getId());
			}

			if (inputFolder != null) {
				listFolder.add(inputFolder.getId());

			}
		}

		listFiles.addAll(getChildrenFilesIdsByFolders(listFolder));

		return listFiles;

	}

	private FolderDB loadFolderByNameAndCatalogId(String folderName,
			Long catalogId) {
		EntityManager entityManager;
		entityManager = EMF.get().createEntityManager();
		List<FolderDB> list;
		ArrayList<FolderDB> folders;
		String sql = "SELECT r FROM FolderDB r WHERE r.uppercaseName='"
				+ folderName + "' AND r.catalogId=" + catalogId;
		list = entityManager.createQuery(sql).getResultList();
		folders = new ArrayList<FolderDB>(list);

		if (list.isEmpty()) {
			return null;
		}

		FolderDB folderDB = folders.get(0);
		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return folderDB;
	}

	private ArrayList<Long> getChildrenFilesIdsByFolders(
			ArrayList<Long> folderIds) {
		ArrayList<Entry> entryChildren = new ArrayList<Entry>();
		for (Long fatherId : folderIds) {
			entryChildren.addAll(getSons(fatherId));
		}

		ArrayList<Long> fileSons = new ArrayList<Long>();
		ArrayList<Long> folderSons = new ArrayList<Long>();

		for (Entry entry : entryChildren) {
			if (entry instanceof FileDB) {
				fileSons.add(entry.getId());
			} else {

				folderSons.add(entry.getId());
			}
		}
		if (!folderSons.isEmpty()) {
			ArrayList<Long> recursiveChildren = getChildrenFilesIdsByFolders(folderSons);
			fileSons.addAll(recursiveChildren);
		}

		return fileSons;
	}

	public ArrayList<Entry> getSons(Long fatherId) {
		EntityManager entityManager;
		List<Entry> list;
		ArrayList<Entry> listEntries;
		entityManager = EMF.get().createEntityManager();
		String sql;
		sql = "SELECT f FROM FolderDB f WHERE f.fathers=" + fatherId;
		list = entityManager.createQuery(sql).getResultList();
		listEntries = new ArrayList<Entry>(list);
		entityManager = EMF.get().createEntityManager();
		sql = "SELECT f FROM FileDB f WHERE f.fathers=" + fatherId;
		list = entityManager.createQuery(sql).getResultList();
		listEntries.addAll(list);
		return listEntries;

	}

	public ArrayList<Annotation> getAnnotationsByIds(ArrayList<Long> ids) {
		ArrayList<Annotation> annotations = new ArrayList<Annotation>();
		for (int i = 0; i < ids.size(); i++) {
			Annotation annotation = loadAnnotationById(ids.get(i));
			if (annotation != null) {
				annotations.add(annotation);
			}

		}
		return annotations;
	}

	public ArrayList<Annotation> getAnnotationsByIdsStudent( // anotaciones
																// restringidas
																// por usuario y
																// actividad
			ArrayList<Long> ids, Long Student, Long readingActivityId) {
		ArrayList<Annotation> annotations = new ArrayList<Annotation>();
		for (int i = 0; i < ids.size(); i++) {
			Annotation annotation = loadAnnotationByIdAndReadingActivity(
					ids.get(i), readingActivityId);
			if (annotation != null) {
				annotations.add(annotation);
			}

		}
		ArrayList<Annotation> annotationsSorted = filterAnnotationsByStudent(
				annotations, Student);
		for (int i = 0; i < annotations.size(); i++) {

			annotations.get(i).setFileIds(
					new ArrayList<Long>(annotations.get(i).getFileIds()));
			annotations.get(i).setTextSelectors(
					new ArrayList<TextSelector>(annotations.get(i)
							.getTextSelectors()));
		}
		return annotations;
	}

	public ArrayList<Annotation> getAnnotationsByAuthors(
			ArrayList<Long> authorsIds) {
		ArrayList<Annotation> Annotations = new ArrayList<Annotation>();
		for (int i = 0; i < authorsIds.size(); i++) {
			ArrayList<Annotation> Annotation = getAnnotationsByAuthorId(authorsIds
					.get(i));
			if (Annotation != null) {
				Annotations.addAll(Annotation);
			}

		}
		return Annotations;
	}

	public ArrayList<Annotation> getAnnotationsByAuthorId(Long id) {
		EntityManager entityManager;
		entityManager = EMF.get().createEntityManager();
		List<Annotation> list;
		ArrayList<Annotation> annotations;
		String sql = "SELECT r FROM Annotation r WHERE r.userId=" + id;
		list = entityManager.createQuery(sql).getResultList();
		annotations = new ArrayList<Annotation>(list);

		if (entityManager.isOpen()) {
			entityManager.close();
		}
		ArrayList<Annotation> annotationReturnArra = new ArrayList<Annotation>();
		for (Annotation annotation : annotations) {

			Annotation annotationReturn = annotation;
			if (annotationReturn != null) {
				annotationReturn.setFileIds(new ArrayList<Long>(
						annotationReturn.getFileIds()));
				annotationReturn.setTextSelectors(new ArrayList<TextSelector>(
						annotationReturn.getTextSelectors()));
			}
			annotationReturnArra.add(annotationReturn);
		}
		return annotationReturnArra;

	}

	public ArrayList<Annotation> getAnnotationsByIdsAndAuthorsTeacher(
			ArrayList<Long> ids, ArrayList<Long> authorIds, Long activity) {
		ArrayList<Annotation> annotationsAux = getAnnotationsByIdsTeacher(ids,
				activity);
		ArrayList<Annotation> annotations = new ArrayList<Annotation>();
		if (!annotationsAux.isEmpty() && annotationsAux != null) {
			annotations.addAll(annotationsAux);
		}
		if (ids.isEmpty()) {
			annotationsAux = getAnnotationsByAuthors(authorIds);
			if (!annotationsAux.isEmpty() && annotationsAux != null) {
				for (int i = 0; i < annotationsAux.size(); i++) {
					if (!ids.contains(annotationsAux.get(i).getId())) {
						annotations.add(annotationsAux.get(i));
					}
				}
			}
		} else {
			if (!authorIds.isEmpty()) {
				annotationsAux = new ArrayList<Annotation>();
				for (Annotation annotation : annotations) {
					if (IsIn(annotation, authorIds))
						annotationsAux.add(annotation);
				}
				annotations = annotationsAux;
			}

		}
		return annotations;
	}

	public ArrayList<Annotation> getAnnotationsByIdsAndAuthorsStudent(
			ArrayList<Long> ids, ArrayList<Long> authorIds, Long activity,
			Long Student) {
		ArrayList<Annotation> annotationsAux = getAnnotationsByIdsStudent(ids,
				Student, activity);
		ArrayList<Annotation> annotations = new ArrayList<Annotation>();
		if (!annotationsAux.isEmpty() && annotationsAux != null) {
			annotations.addAll(annotationsAux);
		}
		if (ids.isEmpty()) {
			annotationsAux = getAnnotationsByAuthors(authorIds);
			if (!annotationsAux.isEmpty() && annotationsAux != null) {
				for (int i = 0; i < annotationsAux.size(); i++) {
					if (!ids.contains(annotationsAux.get(i).getId())) {
						annotations.add(annotationsAux.get(i));
					}
				}
			}
		} else {
			if (!authorIds.isEmpty()) {
				annotationsAux = new ArrayList<Annotation>();
				for (Annotation annotation : annotations) {
					if (IsIn(annotation, authorIds))
						annotationsAux.add(annotation);
				}
				annotations = annotationsAux;
			}

		}
		return annotations;
	}

	public ArrayList<Annotation> getAnnotationsByIdsTeacher(
			ArrayList<Long> ids, Long readingActivityId) {
		ArrayList<Annotation> annotations = new ArrayList<Annotation>();
		for (int i = 0; i < ids.size(); i++) {
			Annotation annotation = loadAnnotationByIdAndReadingActivity(
					ids.get(i), readingActivityId);
			if (annotation != null) {
				annotations.add(annotation);
			}

		}
		for (int i = 0; i < annotations.size(); i++) {

			annotations.get(i).setFileIds(
					new ArrayList<Long>(annotations.get(i).getFileIds()));
			annotations.get(i).setTextSelectors(
					new ArrayList<TextSelector>(annotations.get(i)
							.getTextSelectors()));
		}
		return annotations;
	}

	private boolean IsIn(Annotation annotation, ArrayList<Long> authorIds) {
		for (Long long1 : authorIds) {
			if (annotation.getUserId().equals(long1))
				return true;
		}
		return false;
	}

	public ArrayList<FileDB> getEntriesIdsByIdsRec(ArrayList<Long> Ids) {
		/*
		 * buscar tipos con los nombres/* buscar las carpetas con names añadir a
		 * la lista los tipos obtenidos añadir a la lista funcion-recursiva
		 * {lista de carpetas}
		 */
		EntityManager entityManager;
		ArrayList<FileDB> listFiles = new ArrayList<FileDB>();
		ArrayList<FolderDB> listFolder = new ArrayList<FolderDB>();
		entityManager = EMF.get().createEntityManager();
		String sql;
		for (int i = 0; i < Ids.size(); i++) {
			Entry inputFile = loadFileById(Ids.get(i));
			Entry inputFolder = loadFolderById((Ids.get(i)));
			if (inputFile != null) {
				listFiles.add((FileDB) inputFile);
			}
			if (inputFolder != null) {
				listFolder.add((FolderDB) inputFolder);

			}

		}

		listFiles.addAll(getChildrenFilesIdsByFoldersRec(listFolder));

		ArrayList<FileDB> Salida = new ArrayList<FileDB>();
		for (FileDB fileDB : listFiles) {
			java.util.ArrayList<Long> annotationsIds = new java.util.ArrayList<Long>(
					(java.util.ArrayList<Long>) fileDB.getAnnotationsIds());
			fileDB.getAnnotationsIds().clear();
			java.util.ArrayList<Long> fatherIds = new java.util.ArrayList<Long>(
					(java.util.ArrayList<Long>) fileDB.getFathers());
			fileDB.getFathers().clear();
			FileDB A;
			if (annotationsIds.isEmpty()) {
				A = new FileDB();
				A.setAnnotationsIds(new java.util.ArrayList<Long>());
				A.setCatalogId(fileDB.getCatalogId());

				A.setId(fileDB.getId());
				A.setName(fileDB.getName());

			} else {
				fileDB.setAnnotationsIds(annotationsIds);
				A = fileDB;
			}
			if (fatherIds.isEmpty())
				A.setFathers(new java.util.ArrayList<Long>());
			else
				A.setFathers(fatherIds);

			Salida.add(A);
		}
		return Salida;

	}

	private ArrayList<FileDB> getChildrenFilesIdsByFoldersRec(
			ArrayList<FolderDB> folderIds) {
		ArrayList<Entry> entryChildren = new ArrayList<Entry>();
		for (FolderDB fatherId : folderIds) {
			entryChildren.addAll(getSons(fatherId.getId()));
		}

		ArrayList<FileDB> fileSons = new ArrayList<FileDB>();
		ArrayList<FolderDB> folderSons = new ArrayList<FolderDB>();

		for (Entry entry : entryChildren) {
			if (entry instanceof FileDB) {
				FileDB fileDB = (FileDB) entry;
				fileSons.add(fileDB);

			} else {

				folderSons.add((FolderDB) entry);
			}
		}
		if (!folderSons.isEmpty()) {
			ArrayList<FileDB> recursiveChildren = getChildrenFilesIdsByFoldersRec(folderSons);
			fileSons.addAll(recursiveChildren);
		}

		ArrayList<FileDB> Salida = new ArrayList<FileDB>();
		for (FileDB fileDB : fileSons) {
			java.util.ArrayList<Long> annotationsIds = new java.util.ArrayList<Long>(
					(java.util.ArrayList<Long>) fileDB.getAnnotationsIds());
			fileDB.getAnnotationsIds().clear();
			if (annotationsIds.isEmpty()) {
				annotationsIds = new java.util.ArrayList<Long>();
				FileDB A = new FileDB();
				A.setAnnotationsIds(annotationsIds);
				A.setCatalogId(fileDB.getCatalogId());
				A.setFathers(fileDB.getFathers());
				ArrayList<Long> Listapadres = new ArrayList<Long>();
				for (Long ff : fileDB.getFathers())
					Listapadres.add(ff);
				A.setFathers(Listapadres);
				A.setId(fileDB.getId());
				A.setName(fileDB.getName());
				Salida.add(A);
			} else {
				fileDB.setAnnotationsIds(annotationsIds);
				ArrayList<Long> Listapadres = new ArrayList<Long>();
				for (Long ff : fileDB.getFathers())
					Listapadres.add(ff);
				fileDB.getFathers().clear();
				fileDB.setFathers(Listapadres);
				Salida.add(fileDB);
			}
		}

		return fileSons;
	}

	private void addFatherToFile(Long fileId, Long fatherId)
			throws FileException {
		FileDB sonFile = loadFileById2(fileId);
		FolderDB folder = null;

		if (!fatherId.equals(Constants.CATALOGID)) {
			folder = loadFolderById(fatherId);
			if (!(folder.getEntryIds().contains(fileId))) {
				folder.getEntryIds().add(fileId);
				updateFolder(folder);
			}
		} else {
			Long catalogId = sonFile.getCatalogId();
			Catalogo catalog = loadCatalogById2(catalogId);
			if (!(catalog.getEntryIds().contains(fileId))) {
				catalog.getEntryIds().add(fileId);
				saveCatalog(catalog);
			}
		}
		if (!(sonFile.getFathers().contains(fatherId))) {
			sonFile.getFathers().add(fatherId);
			savePlainFile(sonFile);
		}
	}

	/* añade la relación padre - hijo */
	private void addFatherToFolder(Long folderId, Long fatherId)
			throws FileException {


		FolderDB folderFather = null;
		boolean isFatherCatalog = true;

		if (!fatherId.equals(Constants.CATALOGID)) {
			folderFather = loadFolderById(fatherId);
			isFatherCatalog = false;
		}
		if (!isFolderDestinationDecendant(folderId, folderFather)) {
			FolderDB sonFolder = loadFolderById(folderId);
			if (!isFatherCatalog) {
				if (!(folderFather.getEntryIds().contains(folderId))) {
					folderFather.getEntryIds().add(folderId);
					updateFolder(folderFather);
				}
			} else {
				Long catalogId = sonFolder.getCatalogId();
				Catalogo catalog = loadCatalogById2(catalogId);
				if (!(catalog.getEntryIds().contains(folderId))) {
					catalog.getEntryIds().add(folderId);
					saveCatalog(catalog);
				}
			}
			if (!(sonFolder.getFathers().contains(fatherId))) {
				sonFolder.getFathers().add(fatherId);
				updateFolder(sonFolder);
			}
		}
		else 
		{
			//TODO lanzar excepcion
			throw new FileException("Descendant Error");
		}
	}

	private int addAnnotationsFromFileToAnother(FileDB fFrom, FileDB fTo) {
		ArrayList<Long> annotationFromIds = fFrom.getAnnotationsIds();
		int total = 0;
		int annotationsToFileTo = 0;
		if (annotationFromIds != null) {
			total = annotationFromIds.size();
			for (int i = 0; i < total; i++) {
				if (!(fTo.getAnnotationsIds()
						.contains(annotationFromIds.get(i)))) {
					annotationsToFileTo++;
					fTo.getAnnotationsIds().add(annotationFromIds.get(i));
				}
			}

			if (annotationsToFileTo > 0) {
				savePlainFile(fTo);
			}

			for (int i = 0; i < total; i++) {
				int a = 0;
				Annotation annotation = loadAnnotationById(annotationFromIds
						.get(i));
				annotation.getFileIds().remove(fFrom.getId());
				if (!annotation.getFileIds().contains(fTo.getId())) {
					a++;
					annotation.getFileIds().add(fTo.getId());
				}
				if (a > 0) {
					saveAnnotation(annotation);
				}
			}
		}
		return total;
	}

	public int fusionFiles(Long fFromId, Long fToId) throws GeneralException,
			NullParameterException {
		EntityManager entityManager = EMF.get().createEntityManager();
		if (fFromId == null || fToId == null) {
			throw new NullParameterException(
					"Parameter cant be null in method deleteDnServices");
		}
		int total = 0;
		FileDB fileFrom = loadFileById(fFromId);
		try {

			FileDB fileTo = loadFileById2(fToId);
			total = addAnnotationsFromFileToAnother(fileFrom, fileTo);
			for (int i = 0; i < fileFrom.getFathers().size(); i++) {
				deleteFileFromParent(fileFrom, fileFrom.getFathers().get(i));
				addFather(fToId, fileFrom.getFathers().get(i));
			}

			deletePlainFile(fileFrom);
		} catch (Exception e) {
			entityTransaction.rollback();
			throw new GeneralException("Exception in method saveFileName: "
					+ e.getMessage());
		} finally {
			if (entityManager.isOpen()) {
				entityManager.close();
			}
		}
		return total;
	}

	public void fusionFolder(Long fFromId, Long fToId)
			throws IlegalFolderFusionException, GeneralException {
		FolderDB fto = loadFolderById(fToId);
		if (isFolderDestinationDecendant(fFromId, fto)) {
			throw new IlegalFolderFusionException(
					"Sorry, no merge is possible from a parent to a child category");
		}
		FolderDB fFrom = loadFolderById(fFromId);
		try {
			ArrayList<FolderDB> foldersChildren = getFolderChildren(fFrom);
			if (!foldersChildren.isEmpty()) {
				setNewFatherToFolders(foldersChildren, fFromId, fToId);
			}
			ArrayList<FileDB> fileChildren = getFileChildren(fFrom);
			if (!fileChildren.isEmpty()) {

				setNewFatherToFiles(fileChildren, fFromId, fToId);
			}

			for (int i = 0; i < fFrom.getFathers().size(); i++) {
				deleteFolderFromParent(fFrom, fFrom.getFathers().get(i));
				// addFather(fToId, fFrom.getFathers().get(i));
			}
			deletePlainFolder(fFrom);
		} catch (Exception e) {
			entityTransaction.rollback();
			throw new GeneralException("Exception in method saveFileName: "
					+ e.getMessage());
		}
	}

	private boolean isFolderDestinationDecendant(Long folderFromId,
			FolderDB folderTo) {

		if (folderTo == null) {
			return false;
		}
		if (folderTo.getId().equals(folderFromId))
			return true;
		boolean isDecendant = false;
		ArrayList<FolderDB> parentsList = new ArrayList<FolderDB>();
		ArrayList<Long> parentsListIDs = folderTo.getFathers();
		for (Long long1 : parentsListIDs) {
			if (!long1.equals(Constants.CATALOGID)) {
				parentsList.add(loadFolderById(long1));
			}

		}
		int i = 0;
		while (!isDecendant && i < parentsList.size()) {
			if (parentsList.get(i).getId().equals(folderFromId)) {
				isDecendant = true;
			} else {

				isDecendant = isFolderDestinationDecendant(folderFromId,
						parentsList.get(i));
			}

			i++;
		}
		return isDecendant;
	}

	public void renameFile(Long fileId, String newName) throws FileException {
		FileDB fileDB = loadFileById(fileId);
		if (isFileNameInDB(newName, fileDB.getCatalogId())) {
			throw new FileException(
					"The type you are trying to save already exist in the Database, please check the name or reuse it otherwise");
		}
		fileDB.setName(newName);
		savePlainFile(fileDB);

	}

	public void renameFolder(Long folderId, String newName)
			throws FolderException {
		FolderDB folderDB = loadFolderById(folderId);
		for (int i = 0; i < folderDB.getFathers().size(); i++) {
			if (isFolderBrotherNameInDB(newName, folderDB.getFathers().get(i),
					folderDB.getCatalogId())) {
				throw new FolderException(
						"They Category you are trying to save has a 'twin brother'. Please rename the Category");
			}
		}
		folderDB.setName(newName);
		updateFolder(folderDB);

	}

	private AnnotationThread loadAnnotationThread(Long id) {
		EntityManager entityManager;
		entityManager = EMF.get().createEntityManager();
		List<AnnotationThread> list;
		ArrayList<AnnotationThread> annotationThreads;
		String sql = "SELECT r FROM AnnotationThread r WHERE r.id=" + id;
		list = entityManager.createQuery(sql).getResultList();
		annotationThreads = new ArrayList<AnnotationThread>(list);

		if (entityManager.isOpen()) {
			entityManager.close();
		}
		if (list.isEmpty()) {
			annotationThreads.add(null);
		}
		AnnotationThread annotationThread = annotationThreads.get(0);
		return annotationThread;
	}

	public Long saveAnnotationThread(AnnotationThread annotationThread) {
		boolean isUpdate = false;
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		now = calendar.getTime();
		annotationThread.setCreatedDate(now);
		EntityManager entityManager = EMF.get().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		if (annotationThread.getId() == null) {
			entityManager.persist(annotationThread);

		} else {
			isUpdate = true;
			entityManager.merge(annotationThread);

		}
		entityManager.flush();
		entityTransaction.commit();
		Long annotationThreadId = annotationThread.getId();
		if (entityManager.isOpen()) {
			entityManager.close();
		}

		if (!isUpdate
				&& !annotationThread.getThreadFatherId().equals(
						Constants.THREADFATHERNULLID)) {
			addSonToAnnotationThread(annotationThread.getThreadFatherId(),
					annotationThreadId);

		}

		return annotationThreadId;
	}

	private void addSonToAnnotationThread(Long annotationThreadFather,
			Long annotationThreadSon) {

		EntityManager entityManager = EMF.get().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		AnnotationThread father = entityManager.find(AnnotationThread.class,
				annotationThreadFather);
		if (!father.getThreadIds().contains(annotationThreadSon)) {
			father.getThreadIds().add(annotationThreadSon);
		}

		entityManager.merge(father);
		entityManager.flush();
		entityTransaction.commit();
		entityManager.close();

	}

	public void deleteAnnotationThread(Long annotationThreadId)
			throws GeneralException {
		removeThreadFromFather(annotationThreadId);
		annotationThreadIds = new ArrayList<Long>();
		getAllDeepThreadIds(annotationThreadId);
		deleteThreads(annotationThreadIds);

	}

	private void removeThreadFromFather(Long annotationThreadId) {
		AnnotationThread thread = loadAnnotationThread(annotationThreadId);
		if (!thread.getThreadFatherId().equals(Constants.THREADFATHERNULLID)) {

			EntityManager entityManager = EMF.get().createEntityManager();
			EntityTransaction entityTransaction = entityManager
					.getTransaction();
			entityTransaction.begin();
			Long fatherId = thread.getThreadFatherId();
			AnnotationThread father = entityManager.find(
					AnnotationThread.class, fatherId);
			if (father.getThreadIds().contains(annotationThreadId)) {
				father.getThreadIds().remove(annotationThreadId);
			}
			entityManager.merge(father);
			entityManager.flush();
			entityTransaction.commit();
			entityManager.close();

		}
	}

	private void getAllDeepThreadIds(Long annotationThreadId) {
		AnnotationThread annotationThread = loadAnnotationThread(annotationThreadId);

		for (int i = 0; i < annotationThread.getThreadIds().size(); i++) {
			getAllDeepThreadIds(annotationThread.getThreadIds().get(i));
		}
		annotationThreadIds.add(annotationThreadId);
	}

	private void deletePlainAnnotationThread(Long annotationThreadId) {

		EntityManager entityManager = EMF.get().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		AnnotationThread annotationThread = entityManager.find(
				AnnotationThread.class, annotationThreadId);
		entityManager.remove(annotationThread);
		entityTransaction.commit();
	}

	private void deleteThreads(ArrayList<Long> annotationThreadIds) {
		if (annotationThreadIds != null) {
			for (int i = 0; i < annotationThreadIds.size(); i++) {
				deletePlainAnnotationThread(annotationThreadIds.get(i));
			}
		}
	}

	public ArrayList<AnnotationThread> getAnnotationThreadsByItsFather(
			Long annotationId, Long threadFatherId) throws GeneralException {
		entityManager = EMF.get().createEntityManager();
		List<AnnotationThread> list;
		ArrayList<AnnotationThread> listAnnotationThreads;

		try {
			String sql;

			if (threadFatherId.equals(Constants.THREADFATHERNULLID)) {
				sql = "SELECT a FROM AnnotationThread a WHERE a.annotationId="
						+ annotationId + "AND a.threadFatherId="
						+ Constants.THREADFATHERNULLID;

			} else {
				sql = "SELECT a FROM AnnotationThread a WHERE a.threadFatherId="
						+ threadFatherId;
			}

			list = entityManager.createQuery(sql).getResultList();
			listAnnotationThreads = new ArrayList<AnnotationThread>(list);
		} catch (Exception e) {
			throw new GeneralException(
					"Exception in method loadAnnotationThreadById: "
							+ e.getMessage());
		} finally {
			if (entityManager.isOpen()) {
				entityManager.close();

			}
		}
		if (list.isEmpty()) {
			return new ArrayList<AnnotationThread>();
		}

		for (int i = 0; i < listAnnotationThreads.size(); i++) {
			ArrayList<Long> provisionalLongs = new ArrayList<Long>();
			for (int j = 0; j < listAnnotationThreads.get(i).getThreadIds()
					.size(); j++) {
				provisionalLongs.add(listAnnotationThreads.get(i)
						.getThreadIds().get(j));
			}
			listAnnotationThreads.get(i).setThreadIds(provisionalLongs);
		}

		return listAnnotationThreads;
	}

	private ArrayList<AnnotationThread> getAnnotationThreadsByUserId(Long userId)
			throws GeneralException, NullParameterException {
		entityManager = EMF.get().createEntityManager();
		List<AnnotationThread> list;
		ArrayList<AnnotationThread> listAnnotationThreads;
		if (userId == null) {
			throw new NullParameterException(
					"Parameter aniId cant be null in method loadAnnotationThreadById");
		}
		try {
			String sql = "SELECT a FROM AnnotationThread a WHERE a.userId="
					+ userId;
			list = entityManager.createQuery(sql).getResultList();
			listAnnotationThreads = new ArrayList<AnnotationThread>(list);
		} catch (Exception e) {
			throw new GeneralException(
					"Exception in method loadAnnotationThreadById: "
							+ e.getMessage());
		} finally {
			if (entityManager.isOpen()) {
				entityManager.close();

			}
		}
		if (list.isEmpty()) {
			return null;
		}
		return listAnnotationThreads;
	}

	private void deleteAnnotationThreads(ArrayList<AnnotationThread> threadIds)
			throws GeneralException {
		for (int i = 0; i < threadIds.size(); i++) {
			try {
				deleteAnnotationThread(threadIds.get(i).getId());
			} catch (GeneralException e) {
				throw new GeneralException(
						"There was an error while trying to remove the treads");
			}
		}
	}

	public String getJSONServiceTODrawGraph(String query, String data) {
		URL url;
		URLConnection connection;
		String line;
		StringBuilder builder = new StringBuilder();
		BufferedReader reader;
		try {
			url = new URL(query);
			connection = url.openConnection();
			connection.addRequestProperty("Referer",
					"http://a-note.appspot.com/");
			connection.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(
					connection.getOutputStream());
			wr.write(data);
			wr.flush();
			reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			wr.close();
			reader.close();
		} catch (MalformedURLException ex) {
			Logger.getLogger(GWTServiceImpl.class.getName()).log(Level.SEVERE,
					null, ex);
		} catch (IOException ex) {
			Logger.getLogger(GWTServiceImpl.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		return builder.toString();
	}

	public ArrayList<AnnotationSchema> getSchemaByCatalogId(Long catalogId) {
		ArrayList<AnnotationSchema> schema = new ArrayList<AnnotationSchema>();
		sonIds = new ArrayList<Long>();
		Catalogo catalogo = loadCatalogById(catalogId);
		AnnotationSchema annotationSchema = new AnnotationSchema(catalogId,
				catalogo.getCatalogName(), catalogo.getEntryIds(), true);
		schema.add(annotationSchema);
		for (int j = 0; j < catalogo.getEntryIds().size(); j++) {
			deepingRoot(catalogo.getEntryIds().get(j));
		}

		schema.addAll(buildCatalogue(sonIds));
		return schema;
	}

	private void deepingRoot(Long folderId) {
		FolderDB folder = loadFolderById(folderId);
		if (folder != null) {
			List<FolderDB> foldersChildren = getFolderChildren(folder);
			for (int i = 0; i < foldersChildren.size(); i++) {
				deepingRoot(foldersChildren.get(i).getId());
			}
			List<FileDB> filesChildren = getFileChildren(folder);
			for (int i = 0; i < filesChildren.size(); i++) {
				sonIds.add(filesChildren.get(i).getId());
			}
		}
		sonIds.add(folderId);
	}

	private ArrayList<AnnotationSchema> buildCatalogue(List<Long> idsDuplicate) { // pueden
		// venir
		// duplicados
		HashSet<Long> hashSet = new HashSet<Long>(idsDuplicate);
		List<Long> ids = new ArrayList<Long>(hashSet);
		ArrayList<AnnotationSchema> schema = new ArrayList<AnnotationSchema>();

		for (Long id : ids) {
			FolderDB folder = loadFolderById(id);
			if (folder != null) {
				ArrayList<Long> Al = new ArrayList<Long>();
				for (Long long1 : folder.getEntryIds()) {
					Al.add(long1);
				}
				AnnotationSchema son = new AnnotationSchema(id,
						folder.getName(), Al, true);
				schema.add(son);
			}
		}

		for (Long id : ids) {
			FileDB folder = loadFileById(id);
			if (folder != null) {
				AnnotationSchema son = new AnnotationSchema(id,
						folder.getName(), new ArrayList<Long>(), false);
				schema.add(son);
			}
		}

		return schema;
	}

	public void updateRenameOfUser(Long userId) {
		UserApp user = loadUserById(userId);
		String userName = user.getName();
		ArrayList<Annotation> userAnnotations = getAnnotationsByAuthorId(userId);
		for (Annotation annotation : userAnnotations) {
			updateAnnotation(annotation.getId(), userName);
		}
	}

	private void updateAnnotation(Long annotationId, String userName) {
		EntityManager entityManager = EMF.get().createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		Annotation a = entityManager.find(Annotation.class, annotationId);
		a.setUserName(userName);
		entityTransaction.begin();
		entityManager.merge(a);
		entityManager.flush();
		entityTransaction.commit();
		if (entityManager.isOpen()) {
			entityManager.close();
		}
	}

	// TODO comprobar si el cliente esta usando esta función.
	public void addFather(Long sonId, Long fatherId) throws FileException {
		FileDB file = loadFileById2(sonId);
		if (file != null) {
			addFatherToFile(sonId, fatherId);
		} else {
			addFatherToFolder(sonId, fatherId);
		}

		//TODO lanzar excepcion
	}

	// private UserApp loadUserBybookId(String bookId) {
	// EntityManager entityManager;
	// List<UserApp> users;
	// ArrayList<UserApp> userList;
	// entityManager = EMF.get().createEntityManager();
	// String sql = "SELECT a FROM UserApp a WHERE a.bookIds='" + bookId + "'";
	// users = entityManager.createQuery(sql).getResultList();
	// if (users == null) {
	// return null;
	// }
	// userList = new ArrayList<UserApp>(users);
	// if (entityManager.isOpen())
	// entityManager.close();
	// return userList.get(0);
	// }

	private void deletePlainBookBlob(Long bookId) {
		EntityManager entityManager;
		EntityTransaction entityTransaction;
		List<BookBlob> books;
		entityManager = EMF.get().createEntityManager();
		entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		String sql = "SELECT a FROM BookBlob a WHERE a.id=" + bookId;
		books = entityManager.createQuery(sql).getResultList();
		entityManager.remove(books.get(0));
		entityTransaction.commit();
		if (entityManager.isOpen()) {
			entityManager.close();
		}
	}

	private ReadingActivity loadReadingActivityByBookId(String bookId) {
		EntityManager entityManager;
		entityManager = EMF.get().createEntityManager();
		List<ReadingActivity> list;
		ArrayList<ReadingActivity> readingActivitys;
		String sql = "SELECT r FROM ReadingActivity r WHERE r.bookId='"
				+ bookId + "'";
		list = entityManager.createQuery(sql).getResultList();
		readingActivitys = new ArrayList<ReadingActivity>(list);
		if (entityManager.isOpen()) {
			entityManager.close();
		}
		if (list.isEmpty()) {
			readingActivitys.add(null);
		}
		return readingActivitys.get(0);
	}

	private void removeBookFromReadingActivity(String bookId) {
		ReadingActivity readingActivity = loadReadingActivityByBookId(bookId);

		if (readingActivity != null) {
			readingActivity.setBookId(null);
			saveReadingActivity(readingActivity);
		}

	}

	public void deleteBook(String id, Long userId) {

		// String[] splitIDyCut=id.split("-");
		// String id2=splitIDyCut[splitIDyCut.length-1];
		String[] splitId = id.split("##");
		Long bookId = null;
		if (splitId.length > 1) {
			String id2 = splitId[splitId.length - 1];
			bookId = Long.parseLong(id2);
			deletePlainBookBlob(bookId);
		}
		UserApp user = loadUserById(userId);
		if (user.getBookIds().contains(id)) {
			user.getBookIds().remove(id);
			saveUser(user);
		}
		removeBookFromReadingActivity(id);

	}

	public void updateReadingActivities() {
		ArrayList<ReadingActivity> readingActivities = getReadingActivities();
		for (ReadingActivity readingActivity : readingActivities) {
			readingActivity.setVisualizacion(Constants.VISUAL_KEY);
			readingActivity.setTemplateId(null);
			readingActivity.setTemplateLibre(true);
			saveReadingActivity(readingActivity);
		}
	}

}
