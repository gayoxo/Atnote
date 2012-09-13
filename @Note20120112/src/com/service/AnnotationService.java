package com.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import java.util.List;
import java.util.StringTokenizer;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import javax.xml.bind.annotation.XmlRootElement;

import java.util.logging.Logger;
import java.util.logging.Level;

import lector.client.book.reader.ImageService;

import lector.client.login.UserNotFoundException;
import lector.client.reader.Book;
import lector.client.service.AnnotationSchema;
import lector.server.EMF;
import lector.server.GWTServiceImpl;
import lector.server.ImageServiceImpl;
import lector.server.JSONArray;
import lector.server.JSONException;
import lector.server.JSONObject;
import lector.share.model.Annotation;
import lector.share.model.BookBlob;
import lector.share.model.Catalogo;
import lector.share.model.FileDB;
import lector.share.model.FolderDB;
import lector.share.model.UserApp;

/**
 * 
 * @author cesar
 */
// curl http://127.0.0.1:8888/rs/AtNote/annotations/
@Path("/AtNote")
@Produces("application/json")
@XmlRootElement
public class AnnotationService {

	@PersistenceContext(unitName = "System")
	EntityManager em;
	private static List<Long> sonIds;

	ImageService imageService = new ImageServiceImpl();

	// TESTED
	@GET
	@Path("annotations/creators/")
	public List<AnnotationToExport> getAllAnnotations() throws IOException {
		em = EMF.get().createEntityManager();
		Query query = em.createQuery("select a from Annotation a");
		List<Annotation> annotations = query.getResultList();
		List<AnnotationToExport> annotationsToExport = new ArrayList<AnnotationToExport>();
		for (Annotation annotation : annotations) {
			annotationsToExport.add(buildAnnotationToExport(annotation));
		}

		return annotationsToExport;
	}

	// TESTED with one user
	@GET
	@Path("annotations/creators/{ids}")
	public List<AnnotationToExport> getAnnotationsByCreatorsIds(
			@PathParam("ids") String ids) throws IOException {
		List<Long> usersIds = getLongArrayFromString(ids);
		List<Annotation> annotations = new ArrayList<Annotation>();
		for (int i = 0; i < usersIds.size(); i++) {
			annotations.addAll(getAnnotationsByUserIds(usersIds.get(i)));
		}
		List<AnnotationToExport> annotationsToExport = new ArrayList<AnnotationToExport>();
		for (Annotation annotation : annotations) {
			annotationsToExport.add(buildAnnotationToExport(annotation));
		}

		return annotationsToExport;
	}

	@POST
	@Path("html/produce")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String getHTML(@FormParam("html") String html)
			throws IOException {
		return html;
	}

//	@GET
//	@Path("html/produce/image")
//	@Produces(MediaType.TEXT_HTML)
//	public String getHTML() throws IOException {
//		return imageService.imageTransformed("hola");
//
//	}

	// TESTED
	@GET
	@Path("annotations/books/")
	public List<AnnotationToExport> getAllAnnotationsOfUsers()
			throws IOException {
		em = EMF.get().createEntityManager();
		Query query = em.createQuery("select a from Annotation a");
		List<Annotation> annotations = query.getResultList();
		List<AnnotationToExport> annotationsToExport = new ArrayList<AnnotationToExport>();
		for (Annotation annotation : annotations) {
			annotationsToExport.add(buildAnnotationToExport(annotation));
		}

		return annotationsToExport;
	}

	// TESTED
	@GET
	@Path("annotations/books/{ids}")
	public List<AnnotationToExport> getAnnotationsByIds(
			@PathParam("ids") String ids) throws IOException {
		String idsUpperCased = ids.toUpperCase();
		List<String> booksIds = getStringArrayFromString(idsUpperCased);
		List<Annotation> annotations = new ArrayList<Annotation>();

		for (int i = 0; i < booksIds.size(); i++) {
			String cleanBookId = removeSpaces(booksIds.get(i));
			if (cleanBookId.indexOf("(") != -1) {
				Integer[] range = getAnnotationRange(cleanBookId);
				String cleanedBookId = booksIds.get(i).substring(0,
						booksIds.get(i).indexOf("("));
				annotations.addAll(getAnnotationRangeByBookId(cleanedBookId,
						range[0], range[1]));
			} else if (cleanBookId.indexOf("{") != -1) {
				List<Integer> listOfPages = getAnnotationInIndividualPages(booksIds
						.get(i));
				for (int j = 0; j < listOfPages.size(); j++) {
					String cleanedBookId = booksIds.get(i).substring(0,
							booksIds.get(i).indexOf("{"));
					annotations.addAll(getAnnotationByBookIdAndPageNumber(
							cleanedBookId, listOfPages.get(j)));
				}

			} else {
				annotations.addAll(getAnnotationByBookId(booksIds.get(i))); // TESTED
			}

		}
		List<AnnotationToExport> annotationsToExport = new ArrayList<AnnotationToExport>();
		for (Annotation annotation : annotations) {
			annotationsToExport.add(buildAnnotationToExport(annotation));
		}

		return annotationsToExport;
	}

	// TESTED
	@GET
	@Path("annotations/schema/{creator}")
	public ListOfList stringlist(@PathParam("creator") String creator)
			throws IOException {
		List<Long> creators = getLongArrayFromString(creator);
		List<Long> annotationSchemas = new ArrayList<Long>();
		// String schema = "";
		// List<AnnotationSchema> schema = new ArrayList<AnnotationSchema>();
		ListOfSchema schema = new ListOfSchema();
		ListOfList schemaList = new ListOfList();
		// List<String> schemaList = new ArrayList<String>();
		// List<List<AnnotationSchema>> schemaList = new
		// ArrayList<List<AnnotationSchema>>();
		for (int i = 0; i < creators.size(); i++) {
			annotationSchemas.addAll(getCatalogsIdsByUserId(creators.get(i)));
		}
		for (int i = 0; i < annotationSchemas.size(); i++) {
			schema = new ListOfSchema(
					getAnnotationSchemaByCatalogId(annotationSchemas.get(i)));
			// schema =
			// (getAnnotationSchemaByCatalogId(annotationSchemas.get(i)));
			schemaList.getListOfList().add(schema);
			// schemaList.add(schema.getAnnotationSchemas());

		}
		return schemaList;

		// return schemaList;
	}

	@GET
	@Path("annotations/schema/")
	public ListOfList getAllSchemas() throws IOException {

		List<Long> creators = getUsersIds();
		List<Long> annotationSchemas = new ArrayList<Long>();
		// String schema = "";
		// List<AnnotationSchema> schema = new ArrayList<AnnotationSchema>();
		ListOfSchema schema = new ListOfSchema();
		ListOfList schemaList = new ListOfList();
		// List<String> schemaList = new ArrayList<String>();
		// List<List<AnnotationSchema>> schemaList = new
		// ArrayList<List<AnnotationSchema>>();
		for (int i = 0; i < creators.size(); i++) {
			annotationSchemas.addAll(getCatalogsIdsByUserId(creators.get(i)));
		}
		for (int i = 0; i < annotationSchemas.size(); i++) {
			schema = new ListOfSchema(
					getAnnotationSchemaByCatalogId(annotationSchemas.get(i)));
			// schema =
			// (getAnnotationSchemaByCatalogId(annotationSchemas.get(i)));
			schemaList.getListOfList().add(schema);
			// schemaList.add(schema.getAnnotationSchemas());

		}
		return schemaList;

		// return schemaList;
	}

	public List<Long> getUsersIds() {
		EntityManager entityManager = EMF.get().createEntityManager();
		List<UserApp> list;
		ArrayList<UserApp> listUserApps;
		String sql = "SELECT a FROM UserApp a";
		list = entityManager.createQuery(sql).getResultList();
		listUserApps = new ArrayList<UserApp>(list);
		if (entityManager.isOpen()) {
			entityManager.close();
		}
		List<Long> userIds = new ArrayList<Long>();
		for (int i = 0; i < listUserApps.size(); i++) {
			userIds.add(listUserApps.get(i).getId());
		}
		return userIds;
	}

	public List<AnnotationSchema> getAnnotationSchemaByCatalogId(Long catalogId)
			throws IOException {

		List<AnnotationSchema> schema = new ArrayList<AnnotationSchema>();
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

	private List<Integer> getAnnotationInIndividualPages(String st1) {
		int begings = st1.indexOf("{") + 1;
		int ends = st1.indexOf("}");
		String interval = st1.substring(begings, ends);
		String[] range = interval.split("&");
		List<Integer> rangeList = new ArrayList<Integer>();
		for (int i = 0; i < range.length; i++) {
			rangeList.add(Integer.parseInt(range[i]));
		}

		return rangeList;
	}

	private Integer[] getAnnotationRange(String st1) {
		int begings = st1.indexOf("(") + 1;
		int ends = st1.indexOf(")");
		String interval = st1.substring(begings, ends);
		String[] range = interval.split("-");
		Integer[] rangeInteger = { Integer.parseInt(range[0]),
				Integer.parseInt(range[1]) };
		return rangeInteger;
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
				AnnotationSchema son = new AnnotationSchema(id,
						folder.getName(), folder.getEntryIds(), true);
				schema.add(son);
			}
		}

		return schema;
	}

	private void deepingRoot(Long folderId) {
		FolderDB folder = loadFolderById(folderId);
		if (folder != null) {
			List<FolderDB> foldersChildren = getFolderChildren(folderId);
			for (int i = 0; i < foldersChildren.size(); i++) {
				deepingRoot(foldersChildren.get(i).getId());
			}
			List<FileDB> filesChildren = getFileChildren(folderId);
			for (int i = 0; i < filesChildren.size(); i++) {
				sonIds.add(filesChildren.get(i).getId());
			}
		}
		sonIds.add(folderId);
	}

	private List<FolderDB> getFolderChildren(Long folderId) {
		EntityManager entityManager;
		List<FolderDB> folders;
		List<FolderDB> folderList;
		entityManager = EMF.get().createEntityManager();
		String sql = "SELECT a FROM FolderDB a WHERE a.fathers=" + folderId;
		folders = entityManager.createQuery(sql).getResultList();
		folderList = new ArrayList<FolderDB>(folders);
		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return folderList;
	}

	private List<FileDB> getFileChildren(Long folderId) {
		EntityManager entityManager;
		List<FileDB> files;
		List<FileDB> fileList;
		entityManager = EMF.get().createEntityManager();
		String sql = "SELECT a FROM FileDB a WHERE a.fathers=" + folderId;
		files = entityManager.createQuery(sql).getResultList();
		fileList = new ArrayList<FileDB>(files);
		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return fileList;
	}

	private FileDB loadFileById(Long id) {
		EntityManager entityManager;
		entityManager = EMF.get().createEntityManager();
		FileDB fileDB = entityManager.find(FileDB.class, id);
		if (entityManager.isOpen()) {
			entityManager.close();
		}
		return fileDB;
	}

	private Catalogo loadCatalogById(Long id) {
		EntityManager entityManager;
		List<Catalogo> list;
		ArrayList<Catalogo> listCatalogs;
		entityManager = EMF.get().createEntityManager();
		String sql = "SELECT c FROM Catalogo c WHERE c.id=" + id;
		list = entityManager.createQuery(sql).getResultList();
		listCatalogs = new ArrayList<Catalogo>(list);
		Catalogo catalogo = listCatalogs.get(0);

		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return catalogo;
	}

	private FolderDB loadFolderById(Long id) {
		EntityManager entityManager;
		entityManager = EMF.get().createEntityManager();
		FolderDB folderDB = entityManager.find(FolderDB.class, id);
		if (entityManager.isOpen()) {
			entityManager.close();
		}
		return folderDB;
	}

	public UserApp loadUserById(Long userId) {
		EntityManager entityManager;
		entityManager = EMF.get().createEntityManager();
		UserApp user = entityManager.find(UserApp.class, userId);
		return user;
	}

	private List<Annotation> getAnnotationsByUserIds(Long userId) {
		EntityManager entityManager;
		entityManager = EMF.get().createEntityManager();
		List<Annotation> list;
		String sql = "SELECT r FROM Annotation r WHERE r.userId=" + userId;
		list = entityManager.createQuery(sql).getResultList();
		List<Annotation> annots = new ArrayList<Annotation>(list);
		if (entityManager.isOpen()) {
			entityManager.close();
		}
		return annots;
	}

	private List<Long> getCatalogsIdsByUserId(Long userId) {
		EntityManager entityManager;
		entityManager = EMF.get().createEntityManager();
		List<Catalogo> list;
		List<Long> listLongs = new ArrayList<Long>();
		String sql = "SELECT r FROM Catalogo r WHERE r.professorId=" + userId;
		list = entityManager.createQuery(sql).getResultList();
		for (int i = 0; i < list.size(); i++) {
			listLongs.add(list.get(i).getId());
		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}
		return listLongs;
	}

	private List<Annotation> getAnnotationRangeByBookId(String bookId,
			Integer startPage, Integer endPage) {
		EntityManager entityManager;
		entityManager = EMF.get().createEntityManager();
		List<Annotation> list; // column_1 BETWEEN lower_range AND upper_range
								// ORDER BY buyPrice DESC
		String sql = "SELECT r FROM Annotation r WHERE r.bookId='" + bookId
				+ "' AND r.pageNumber BETWEEN " + startPage + " AND " + endPage;
		list = entityManager.createQuery(sql).getResultList();
		List<Annotation> annots = new ArrayList<Annotation>(list);
		if (entityManager.isOpen()) {
			entityManager.close();
		}
		return annots;
	}

	private List<Annotation> getAnnotationByBookIdAndPageNumber(String bookId,
			Integer pageNumber) {
		EntityManager entityManager;
		entityManager = EMF.get().createEntityManager();
		List<Annotation> list;
		String sql = "SELECT r FROM Annotation r WHERE r.bookId='" + bookId
				+ "' AND r.pageNumber=" + pageNumber;
		list = entityManager.createQuery(sql).getResultList();
		List<Annotation> annots = new ArrayList<Annotation>(list);
		if (entityManager.isOpen()) {
			entityManager.close();
		}
		return annots;
	}

	private List<Annotation> getAnnotationByBookId(String bookId) {
		EntityManager entityManager;
		entityManager = EMF.get().createEntityManager();
		List<Annotation> list;
		String sql = "SELECT r FROM Annotation r WHERE r.bookId='" + bookId
				+ "'";
		list = entityManager.createQuery(sql).getResultList();
		List<Annotation> annots = new ArrayList<Annotation>(list);
		if (entityManager.isOpen()) {
			entityManager.close();
		}
		return annots;
	}

	private Book getBookInGoogleByISBN(String query) throws IOException {
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

	private String removeSpaces(String s) {
		StringTokenizer st = new StringTokenizer(s, " ", false);
		String t = "";
		while (st.hasMoreElements()) {
			t += st.nextElement();
		}
		return t;
	}

	private List<Long> getLongArrayFromString(String ids) {
		String[] idsSplitted = ids.split(",");
		List<Long> idsLong = new ArrayList<Long>();
		for (int i = 0; i < idsSplitted.length; i++) {
			Long l = Long.parseLong(idsSplitted[i]);
			idsLong.add(l);
		}
		return idsLong;
	}

	private List<String> getStringArrayFromString(String ids) {
		String[] idsSplitted = ids.split(",");
		List<String> stringArray = Arrays.asList(idsSplitted);

		return stringArray;
	}

	private AnnotationToExport buildAnnotationToExport(Annotation annotation)
			throws IOException {
		UserApp user = loadUserById(annotation.getUserId());
		UserAppSimp userSimp = new UserAppSimp(user.getId(), user.getEmail(),
				user.getProfile(), user.getName(), user.getLastName(),
				user.getDNI());

		Book book = getBookInGoogleByISBN(annotation.getBookId());
		BookSimp bookSimp = new BookSimp(book.getAuthor(), book.getId(),
				book.getPagesCount(), book.getPublishedYear(), book.getTitle(),
				book.getTbURL(), book.getUrl());

		List<FileToExport> files = new ArrayList<FileToExport>();
		for (Long fileId : annotation.getFileIds()) {
			FileDB file = loadFileById(fileId);
			FileToExport fileToExport = new FileToExport(file.getId(),
					file.getName(), file.getCatalogId());
			files.add(fileToExport);
		}

		AnnotationSimp annotationSimp = new AnnotationSimp(annotation.getId(),
				annotation.getVisibility(), annotation.getPageNumber(),
				annotation.getTextSelectors(), annotation.getComment()
						.getValue(), files, annotation.getCreatedDate());

		AnnotationToExport ate = new AnnotationToExport(userSimp, bookSimp,
				annotationSimp);

		return ate;
	}

}
