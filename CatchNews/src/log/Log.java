package log;

//import org.apache.log4j.LogManager;
//import org.apache.log4j.Logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {

	
	//slf4j
	private static Logger log = LoggerFactory.getLogger(Log.class);
	

	private static Logger errorLog = LoggerFactory.getLogger("errorLog");
	private static Logger runLog = LoggerFactory.getLogger("runLog");
	private static Logger debugLog = LoggerFactory.getLogger("debugLog");
	
	private static Logger looperAccessLog = LoggerFactory.getLogger("accesslog");
	private static Logger looperApiLog = LoggerFactory.getLogger("api");
	private static Logger looperComplexInfoLog = LoggerFactory.getLogger("complexInfo");
	
	public static  Logger getComplexInfoLogger() {
		return looperComplexInfoLog;
	}
	//get logger
	public static  Logger getRunLogger() {
		return runLog;
	}
	
	public static  Logger getErrorLogger() {
		return errorLog;
	}
	
	public static  Logger getDebugLogger() {
		return debugLog;
	}
	
	public static  Logger getLooperAccessLogger() {
		return looperAccessLog;
	}
	
	public static  Logger getLooperApiLogger() {
		return looperApiLog;
	}
	public static void  main(final String[] args) {
		
	}
}
