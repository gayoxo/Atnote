/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lector.client.book.reader;

import com.google.gwt.user.client.rpc.AsyncCallback;
import lector.client.service.AnnotationSchema;

import java.util.ArrayList;

import lector.client.catalogo.client.Catalog;
import lector.client.catalogo.client.Entity;
import lector.client.catalogo.client.File;
import lector.client.catalogo.client.Folder;
import lector.client.reader.Book;
import lector.share.model.Annotation;
import lector.share.model.AnnotationThread;
import lector.share.model.Catalogo;
import lector.share.model.FileDB;
import lector.share.model.GroupApp;
import lector.share.model.Language;
import lector.share.model.ReadingActivity;
import lector.share.model.UserApp;

/**
 * 
 * @author Cesar y Gayoso.
 */
public interface GWTServiceAsync {

	public void getAnnotationsByBookId(String bookId,
			AsyncCallback<ArrayList<Annotation>> asyncCallback);

	public void getBooks(String query,
			AsyncCallback<ArrayList<Book>> asyncCallback);

	public void getBooks(String query, int start,
			AsyncCallback<ArrayList<Book>> asyncCallback);

	public void deleteAnnotation(Annotation annotation,
			AsyncCallback<java.lang.Integer> asyncCallback);

	public void getAnnotationsByAnnotationTypeAndBook(String annotationType,
			String bookId, Integer pageNumber,
			AsyncCallback<ArrayList<Annotation>> asyncCallback);

	public void loadFullBookInGoogle(String query,
			AsyncCallback<Book> asyncCallback);

	public void saveAnnotation(Annotation annotation,
			AsyncCallback<Long> asyncCallback);

	public void moveFile(Long fatherFromId, Long fileId, Long fToId,
			AsyncCallback<Void> asyncCallback);

	public void deleteFolder(Long folderId, Long fatherId,
			AsyncCallback<Void> asyncCallback);

	public void isRecentToSave(Annotation annotation,
			AsyncCallback<java.lang.Boolean> asyncCallback);

	public void getAnnotationsNumberByFileName(String annotationTypeName,
			AsyncCallback<java.lang.Integer> asyncCallback);

	public void fusionFiles(Long fFromId, Long fToId,
			AsyncCallback<java.lang.Integer> asyncCallback);

	public void saveFile(File filesys, Long fatherId,
			AsyncCallback<Long> asyncCallback);

	void saveCatalog(Catalogo catalog, AsyncCallback<Void> callback);

	public void login(String requestUri, AsyncCallback<UserApp> asyncCallback);

	public void saveUser(UserApp user, AsyncCallback<Boolean> asyncCallback);

	public void loadUserById(Long userId, AsyncCallback<UserApp> asyncCallback);

	public void getUsersApp(AsyncCallback<ArrayList<UserApp>> asyncCallback);

	public void deleteUserApp(Long userId,
			AsyncCallback<java.lang.Integer> asyncCallback);

	public void removeUserAndGroupRelation(Long userId, Long groupId,
			AsyncCallback<Void> asyncCallback);

	public void loadGroupById(Long groupId,
			AsyncCallback<GroupApp> asyncCallback);

	public void saveGroup(GroupApp groupApp, AsyncCallback<Void> asyncCallback);

	public void getGroups(AsyncCallback<ArrayList<GroupApp>> asyncCallback);

	public void deleteGroup(Long groupId,
			AsyncCallback<java.lang.Integer> asyncCallback);

	public void getGroupsByUserId(Long userId,
			AsyncCallback<ArrayList<GroupApp>> asyncCallback);

	void loginAuxiliar(String userEmail, AsyncCallback<UserApp> callback);

	void loadUserByEmail(String email, AsyncCallback<UserApp> callback);

	void getProfessor(AsyncCallback<ArrayList<UserApp>> callback);

	void loadGroupByName(String groupName, AsyncCallback<GroupApp> callback);

	void getUsersByGroupId(Long groupId,
			AsyncCallback<ArrayList<UserApp>> callback);

	void saveNewGroup(GroupApp groupApp, AsyncCallback<Boolean> callback);

	public void getSons(Long fatherId, Long catalogId,
			AsyncCallback<ArrayList<Entity>> asyncCallback);

	public void getCatalogs(AsyncCallback<ArrayList<Catalog>> asyncCallback);

	void loadFileById(Long id, AsyncCallback<FileDB> callback);

	void loadFileByNameAndCatalogId(String fileName, Long catalogId,
			AsyncCallback<FileDB> callback);

	void deleteCatalog(Long catalogId, AsyncCallback<Void> callback);

	void deleteReadingActivity(Long readingActivityId,
			AsyncCallback<Void> callback);

	void saveLanguage(Language language, AsyncCallback<Void> callback);

	void deleteLanguage(String nameId, AsyncCallback<Integer> callback);

	void getLanguagesNames(AsyncCallback<ArrayList<String>> callback);

	void getLanguages(AsyncCallback<ArrayList<Language>> callback);

	void getReadingActivityByUserId(Long userId,
			AsyncCallback<ArrayList<ReadingActivity>> callback);

	void deleteProfessor(Long professorId, AsyncCallback<Integer> callback);

	void getReadingActivityByProfessorId(Long professorId,
			AsyncCallback<ArrayList<ReadingActivity>> callback);

	void saveReadingActivity(ReadingActivity readingActivity,
			AsyncCallback<Void> callback);

	void loadCatalogById(Long catalogId, AsyncCallback<Catalogo> callback);

	void loadLanguageByName(String nameId, AsyncCallback<Language> callback);

	void getVisbibleCatalogsByProfessorId(Long professorId,
			AsyncCallback<ArrayList<Catalogo>> callback);

	void getAnnotationsByPageNumbertByStudentId(Integer pageNumber,
			String bookId, Long studentId, Long readingActivityId,
			AsyncCallback<ArrayList<Annotation>> callback);

	void getAnnotationsByPageNumber(Integer pageNumber, String bookId,
			Long readingActivityId,
			AsyncCallback<ArrayList<Annotation>> callback);

	void removeReadingActivityFromAnnotations(Long readingActivity,
			AsyncCallback<Integer> callback);

	void loadReadingActivityById(Long id,
			AsyncCallback<ReadingActivity> callback);

	void removeFileFromAnnotation(Long annotationId, Long fileId,
			AsyncCallback<Void> callback);

	void getFilesByNameAndCatalogId(ArrayList<String> names, Long catalogId,
			AsyncCallback<ArrayList<FileDB>> callback);

	void getFilesByIds(ArrayList<Long> ids,
			AsyncCallback<ArrayList<FileDB>> callback);

	void getEntriesIdsByNames(ArrayList<String> names, Long catalogTeacher,
			Long catalogOpen, AsyncCallback<ArrayList<Long>> callback);

	void getAnnotationsByIds(ArrayList<Long> ids,
			AsyncCallback<ArrayList<Annotation>> callback);

	void addFather(Long sonId, Long fatherId, AsyncCallback<Void> callback);

	void deleteFile(Long fileId, Long fatherId, AsyncCallback<Void> callback);

	void saveFolder(Folder folderSys, Long fatherId,
			AsyncCallback<Long> callback);

	void moveFolder(Long fatherFromId, Long fFromId, Long fToId,
			AsyncCallback<Void> callback);

	void getAnnotationsByIdsAndAuthorsTeacher(ArrayList<Long> ids,
			ArrayList<Long> authorIds, Long Activity,
			AsyncCallback<ArrayList<Annotation>> callback);

	void getEntriesIdsByIdsRec(ArrayList<Long> Ids,
			AsyncCallback<ArrayList<FileDB>> callback);

	void getAnnotationsByIdsTeacher(ArrayList<Long> ids,
			Long readingActivityId,
			AsyncCallback<ArrayList<Annotation>> callback);

	void getAnnotationsByIdsStudent(ArrayList<Long> ids, Long Student,
			Long readingActivityId,
			AsyncCallback<ArrayList<Annotation>> callback);

	void getAnnotationsByIdsAndAuthorsStudent(ArrayList<Long> ids,
			ArrayList<Long> authorIds, Long Activity, Long Student,
			AsyncCallback<ArrayList<Annotation>> callback);

	void fusionFolder(Long fFromId, Long fToId, AsyncCallback<Void> callback);

	void renameFile(Long fileId, String newName, AsyncCallback<Void> callback);

	void renameFolder(Long folderId, String newName,
			AsyncCallback<Void> callback);

	void saveAnnotationThread(AnnotationThread annotationThread,
			AsyncCallback<Long> callback);

	void deleteAnnotationThread(Long annotationThread,
			AsyncCallback<Void> callback);

	void getAnnotationThreadsByItsFather(Long annotationId,
			Long threadFatherId,
			AsyncCallback<ArrayList<AnnotationThread>> callback);

	void getJSONServiceTODrawGraph(String url,String body, AsyncCallback<String> callback);

	void getSchemaByCatalogId(Long catalogId,
			AsyncCallback<ArrayList<AnnotationSchema>> callback);

	void updateRenameOfUser(Long userId, AsyncCallback<Void> callback);

	void deleteBook(String bookId, Long userId, AsyncCallback<Void> callback);

	void getFileNamesByIds(ArrayList<Long> ids,
			AsyncCallback<ArrayList<String>> callback);

	void updateReadingActivities(AsyncCallback<Void> callback);

}
