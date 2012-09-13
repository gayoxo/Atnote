package lector.client.book.reader;

import java.util.ArrayList;

import lector.share.model.BookBlob;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoggerServiceAsync {

	void severe(String className, String text, AsyncCallback<Void> callback);

	void warning(String className, String text, AsyncCallback<Void> callback);

	void info(String className, String text, AsyncCallback<Void> callback);

	void finest(String className, String text, AsyncCallback<Void> callback);

}
