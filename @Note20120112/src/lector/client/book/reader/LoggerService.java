/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lector.client.book.reader;

import java.util.ArrayList;

import lector.client.reader.BookBlob;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


/**
 * 
 * @author Cesar y Joaquin
 */
@RemoteServiceRelativePath("book.reader/loggerservice")
public interface LoggerService extends RemoteService {

	public void severe(String className, String text);
	
	public void warning(String className, String text);

	public void info(String className, String text);
	
	public void finest(String className, String text);
}
