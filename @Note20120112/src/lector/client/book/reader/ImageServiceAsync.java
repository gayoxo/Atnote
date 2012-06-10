/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lector.client.book.reader;

import java.util.ArrayList;

import lector.client.reader.BookBlob;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 
 * @author Cesar y Gayoso.
 */
public interface ImageServiceAsync {

	void getBlobstoreUploadUrl(AsyncCallback<String> callback);

	
	void getBookBlobsByUserId(Long userAppId, AsyncCallback<ArrayList<BookBlob>> callback);


	void loadBookBlobById(Long id, AsyncCallback<BookBlob> callback);


	void saveBookBlob(BookBlob bookBlob, AsyncCallback<Void> callback);

}
