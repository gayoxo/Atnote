package lector.server;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;

public class LoggerServelet extends HttpServlet {

	private final static Logger LOGGER = Logger.getLogger(LoggerServelet.class
			.getName());
	private static LoggerServelet Server;
	
	public LoggerServelet() {
		LOGGER.setLevel(Level.INFO);
	}
	
	
	public void severe(String texto)
	{
		LOGGER.severe(texto);
	}
	
	
	public void warning(String texto){
		LOGGER.warning(texto);	
	}

	public void info(String texto){
		LOGGER.info(texto);
	}
	
	public void finest(String texto){
		LOGGER.finest(texto);
	}

	
	public static LoggerServelet getInstance()
	{
		if (Server==null)
			Server=new LoggerServelet();
		return Server;
	}
	
	
}
