package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility class for logging
 */
public class LoggerUtils {
    
    private static final Logger logger = LogManager.getLogger(LoggerUtils.class);
    
    /**
     * Log info message
     * @param message
     */
    public static void info(String message) {
        logger.info(message);
    }
    
    /**
     * Log debug message
     * @param message
     */
    public static void debug(String message) {
        logger.debug(message);
    }
    
    /**
     * Log warning message
     * @param message
     */
    public static void warn(String message) {
        logger.warn(message);
    }
    
    /**
     * Log error message
     * @param message
     */
    public static void error(String message) {
        logger.error(message);
    }
    
    /**
     * Log error with exception
     * @param message
     * @param throwable
     */
    public static void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }
    
    /**
     * Log fatal message
     * @param message
     */
    public static void fatal(String message) {
        logger.fatal(message);
    }
}
