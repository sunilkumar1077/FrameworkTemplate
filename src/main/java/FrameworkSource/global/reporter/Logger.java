package FrameworkSource.global.reporter;

/**
 * The class consists of all the functions relates to Logger class of Log4J.
 */
public class Logger {
	public org.apache.log4j.Logger logger;
	
	/**
	 * The function logs debug level logs.
	 * @param category takes string value and is always the class name.
	 * @param message takes a string value and customized message that needs to be logged.
	 */
	public static void DEBUG(String category, String message)
	{
		org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(category);
	    logger.debug(message);
	}
	/**
	 * The function logs info level logs.
	 * @param category takes string value and is always the class name.
	 * @param message takes a string value and customized message that needs to be logged.
	 */
	public static void INFO(String category, String message)
	{
		org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(category);
	    logger.info(message);
	}
	/**
	 * The function logs error level logs.
	 * @param category takes string value and is always the class name.
	 * @param message takes a string value and customized message that needs to be logged.
	 */
	public static void ERROR(String category, Exception e)
	{
		org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(category);
	    logger.error(e);
	}
	/**
	 * The function logs warnings level logs.
	 * @param category takes string value and is always the class name.
	 * @param message takes a string value and customized message that needs to be logged.
	 */
	public static void WARNING(String category, String message)
	{
		org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(category);
	    logger.warn(message);
	}
	/**
	 * The function logs fatal level logs.
	 * @param category takes string value and is always the class name.
	 * @param message takes a string value and customized message that needs to be logged.
	 */
	public static void FATAL(String category, String message)
	{
		
		org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(category);
	    logger.fatal(message);
	}
	

}
