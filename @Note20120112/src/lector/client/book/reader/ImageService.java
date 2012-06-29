/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lector.client.book.reader;

import java.util.ArrayList;

import lector.client.reader.BookBlob;
import lector.client.reader.ExportObject;
import lector.client.reader.TextSelector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * 
 * @author Cesar y Joaquin
 */
@RemoteServiceRelativePath("book.reader/imageservice")
public interface ImageService extends RemoteService {

	public String getBlobstoreUploadUrl();

	public ArrayList<BookBlob> getBookBlobsByUserId(Long userAppId);

	public BookBlob loadBookBlobById(Long id);

	public void saveBookBlob(BookBlob bookBlob);

	public String loadHTMLStringForExport(ArrayList<ExportObject> exportObjects);
	
	public String loadHTMLStringForExportUni(ExportObject exportObject);
	
	public String loadRTFStringForExportUni(ExportObject exportObject);
}
