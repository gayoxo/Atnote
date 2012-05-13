package lector.client.logger;

import lector.client.book.reader.LoggerService;
import lector.client.book.reader.LoggerServiceAsync;
import lector.client.controler.ErrorConstants;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class Logger {

	private static AsyncCallback<Void> callback= new AsyncCallback<Void>() {

		public void onFailure(Throwable caught) {
			Window.alert(ErrorConstants.LOGS_ERROR);
			
		}

		public void onSuccess(Void result) {

			
		}
	};
	
	private static Logger Log;
	
	static LoggerServiceAsync loggerServiceHolder = GWT
	.create(LoggerService.class);
	
	
	public void severe(String className, String text) {
		loggerServiceHolder.severe(className, text, callback);

	}

	public void warning(String className, String text) {
		loggerServiceHolder.warning(className, text, callback);

	}

	public void info(String className, String text) {
		//Window.alert("info");
		loggerServiceHolder.info(className, text, callback);

	}

	public void finest(String className, String text) {
		loggerServiceHolder.finest(className, text, callback);

	}
	
	
	public static Logger GetLogger(){
		//Window.alert("GetLogger");
		if (Log==null) Log=new Logger();
		return Log;
	}
}
