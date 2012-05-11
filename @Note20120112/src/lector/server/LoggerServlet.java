package lector.server;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;

public class LoggerServlet extends HttpServlet {

	public void severe(String className, String text) {
		Logger L = Logger.getLogger(className);
		L.setLevel(Level.SEVERE);
		L.severe(text);

	}

	public void warning(String className, String text) {
		Logger L = Logger.getLogger(className);
		L.setLevel(Level.WARNING);
		L.warning(text);

	}

	public void info(String className, String text) {
		Logger L = Logger.getLogger(className);
		L.setLevel(Level.INFO);
		L.info(text);

	}

	public void finest(String className, String text) {
		Logger L = Logger.getLogger(className);
		L.setLevel(Level.FINEST);
		L.finest(text);

	}
}
