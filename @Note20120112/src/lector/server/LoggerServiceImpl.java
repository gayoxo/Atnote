package lector.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lector.client.book.reader.ImageService;
import lector.client.book.reader.LoggerService;
import lector.client.reader.BookBlob;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class LoggerServiceImpl extends RemoteServiceServlet implements
		LoggerService {

	private static LoggerServlet Loger;
	
	public void severe(String className, String text) {
		getLogger().severe(className, text);
	}

	public void warning(String className, String text) {
		getLogger().warning(className, text);

	}

	public void info(String className, String text) {
		getLogger().info(className, text);
	}

	public void finest(String className, String text) {
		getLogger().finest(className, text);

	}
	
	private LoggerServlet getLogger()
	{
		if (Loger==null) Loger=new LoggerServlet();
		return Loger;
	}

}
