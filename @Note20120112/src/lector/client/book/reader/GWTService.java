/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lector.client.book.reader;

import java.util.ArrayList;

import lector.client.admin.activity.ReadingActivity;
import lector.client.catalogo.client.Catalog;
import lector.client.catalogo.client.DecendanceException;
import lector.client.catalogo.client.Entity;
import lector.client.catalogo.client.File;
import lector.client.catalogo.client.FileException;
import lector.client.catalogo.client.Folder;
import lector.client.catalogo.client.FolderException;
import lector.client.catalogo.server.Catalogo;
import lector.client.catalogo.server.FileDB;
import lector.client.language.Language;
import lector.client.language.LanguageNotFoundException;
import lector.client.login.GroupApp;
import lector.client.login.GroupNotFoundException;
import lector.client.login.UserApp;
import lector.client.login.UserNotFoundException;
import lector.client.reader.Annotation;
import lector.client.reader.AnnotationNotFoundException;
import lector.client.reader.Book;
import lector.client.reader.BookNotFoundException;
import lector.client.reader.GeneralException;
import lector.client.reader.IlegalFolderFusionException;

import lector.client.reader.NullParameterException;
import lector.client.reader.annotthread.AnnotationThread;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import lector.client.service.AnnotationSchema;

/**
 * 
 * @author Cesar y Joaquin
 */
@RemoteServiceRelativePath("book.reader/gwtservice")
public interface GWTService extends RemoteService {

	/**
	 * 
	 * @gwt.typeArgs bookId <java.lang.String>
	 * @gwt.typeArgs <org.yournamehere.client.Annotation>
	 */
	public ArrayList<Annotation> getAnnotationsByBookId(String bookId)
			throws GeneralException, AnnotationNotFoundException,
			NullParameterException, BookNotFoundException;

	public Long saveAnnotation(Annotation annotation);

	public ArrayList<Annotation> getAnnotationsByPageNumber(Integer pageNumber,
			String bookId, Long readingActivityId) throws GeneralException,
			AnnotationNotFoundException, NullParameterException,
			BookNotFoundException;

	public int deleteAnnotation(Annotation annotation) throws GeneralException,
			NullParameterException, AnnotationNotFoundException;

	public ArrayList<Book> getBooks(String query);

	public ArrayList<Book> getBooks(String query, int start);

	public Book loadFullBookInGoogle(String query);

	public int getAnnotationsNumberByFileName(String annotationTypeName)
			throws GeneralException, NullParameterException,
			AnnotationNotFoundException;

	public int fusionFiles(Long fFromId, Long fToId) throws GeneralException,
			NullParameterException;

	public void fusionFolder(Long fFromId, Long fToId)
			throws IlegalFolderFusionException, GeneralException;

	public ArrayList<Annotation> getAnnotationsByAnnotationTypeAndBook(
			String annotationType, String bookId, Integer pageNumber)
			throws GeneralException, AnnotationNotFoundException,
			NullParameterException, BookNotFoundException;

	public ArrayList<Annotation> getAnnotationsByIdsAndAuthorsTeacher(
			ArrayList<Long> ids, ArrayList<Long> authorIds, Long Activity);

	public ArrayList<FileDB> getEntriesIdsByIdsRec(ArrayList<Long> Ids);

	public ArrayList<Annotation> getAnnotationsByIdsTeacher(ArrayList<Long> ids, Long readingActivityId);

	public ArrayList<Annotation> getAnnotationsByIdsStudent(
			ArrayList<Long> ids, Long Student, Long readingActivityId);

	public ArrayList<Annotation> getAnnotationsByIdsAndAuthorsStudent(
			ArrayList<Long> ids, ArrayList<Long> authorIds, Long Activity,
			Long Student);

	public void moveFile(Long fatherFromId, Long fileId, Long fToId)
			throws GeneralException;

	public void moveFolder(Long fatherFromId, Long fFromId, Long fToId)
			throws GeneralException, DecendanceException;

	public void deleteFolder(Long folderId, Long fatherId)
			throws GeneralException;

	public void deleteFile(Long fileId, Long fatherId) throws GeneralException,
			NullParameterException;

	public Long saveFile(File filesys, Long fatherId) throws FileException;

	public Long saveFolder(Folder folderSys, Long fatherId)
			throws FolderException;

	public ArrayList<Entity> getSons(Long fatherId, Long catalogId);

	public boolean isRecentToSave(Annotation annotation);

	public void saveCatalog(Catalogo catalog);

	public UserApp login(String requestUri) throws UserNotFoundException;

	public UserApp loadUserById(Long userId);

	public ArrayList<UserApp> getUsersApp() throws UserNotFoundException;

	public int deleteUserApp(Long userId)throws GeneralException, NullParameterException;

	public void removeUserAndGroupRelation(Long userId, Long groupId);

	public GroupApp loadGroupById(Long groupId);

	public void saveGroup(GroupApp groupApp);

	public ArrayList<GroupApp> getGroups() throws GroupNotFoundException;

	public int deleteGroup(Long groupId);

	public ArrayList<Annotation> getAnnotationsByPageNumbertByStudentId(
			Integer pageNumber, String bookId, Long studentId,
			Long readingActivityId);

	public ArrayList<GroupApp> getGroupsByUserId(Long userId);

	// TODO: RETIRAR CUANDO SE HAGA EL LOGIN CON GOOGLE
	public UserApp loginAuxiliar(String userEmail) throws UserNotFoundException;

	public UserApp loadUserByEmail(String email);

	public ArrayList<UserApp> getProfessor() throws UserNotFoundException;

	public GroupApp loadGroupByName(String groupName);

	public ArrayList<UserApp> getUsersByGroupId(Long groupId);

	public boolean saveNewGroup(GroupApp groupApp)
			throws GroupNotFoundException;

	public boolean saveUser(UserApp user);

	public ArrayList<Catalog> getCatalogs();

	public FileDB loadFileById(Long id);

	public FileDB loadFileByNameAndCatalogId(String fileName, Long catalogId);

	public void deleteCatalog(Long catalogId) throws GeneralException,
			NullParameterException;

	public void deleteReadingActivity(Long readingActivityId)
			throws GeneralException, NullParameterException,
			AnnotationNotFoundException;

	public ArrayList<Language> getLanguages() throws GeneralException,
			LanguageNotFoundException, NullParameterException;

	public ArrayList<String> getLanguagesNames() throws GeneralException,
			LanguageNotFoundException, NullParameterException;

	public void saveLanguage(Language language);

	public int deleteLanguage(String languageName);

	public ArrayList<Catalogo> getVisbibleCatalogsByProfessorId(Long professorId);

	public ArrayList<ReadingActivity> getReadingActivityByUserId(Long userId);

	public int deleteProfessor(Long professorId) throws GeneralException,
			NullParameterException;

	public ArrayList<ReadingActivity> getReadingActivityByProfessorId(
			Long professorId);

	public void saveReadingActivity(ReadingActivity readingActivity);

	public Catalogo loadCatalogById(Long catalogId);

	public Language loadLanguageByName(String nameId);

	public int removeReadingActivityFromAnnotations(Long readingActivity)
			throws GeneralException, NullParameterException,
			AnnotationNotFoundException;

	public ReadingActivity loadReadingActivityById(Long id);

	public void removeFileFromAnnotation(Long annotationId, Long fileId);

	public ArrayList<FileDB> getFilesByIds(ArrayList<Long> ids);

	public ArrayList<FileDB> getFilesByNameAndCatalogId(
			ArrayList<String> names, Long catalogId);

	public ArrayList<Long> getEntriesIdsByNames(ArrayList<String> names,
			Long catalogTeacher, Long catalogOpen);

	public ArrayList<Annotation> getAnnotationsByIds(ArrayList<Long> ids);

	public void addFather(Long sonId, Long fatherId) throws FileException;
	
	public void renameFile(Long fileId, String newName) throws FileException;
	
	public void renameFolder(Long folderId, String newName) throws FolderException;
	
	public Long saveAnnotationThread(AnnotationThread annotationThread);
	
	public void deleteAnnotationThread(Long annotationThread) throws GeneralException;

	public ArrayList<AnnotationThread> getAnnotationThreadsByItsFather(
			Long annotationId, Long threadFatherId) throws GeneralException;

	public String getJSONServiceTODrawGraph(String url, String body);
	
	public ArrayList<AnnotationSchema> getSchemaByCatalogId(Long catalogId);
	
	public void updateRenameOfUser(Long userId); 
	
	public void deleteBook(String bookId, Long userId);
	
	public ArrayList<String> getFileNamesByIds(ArrayList<Long> ids);
}
