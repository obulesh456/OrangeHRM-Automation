package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Utility class to read configuration properties
 */
public class ConfigReader {
    
    private static Properties properties;
    private static final String CONFIG_FILE_PATH = "src/test/resources/config/config.properties";
    
    /**
     * Load properties from config file
     */
    public static void loadProperties() {
        properties = new Properties();
        try {
            FileInputStream fis = new FileInputStream(CONFIG_FILE_PATH);
            properties.load(fis);
            fis.close();
            LoggerUtils.info("Configuration properties loaded successfully");
        } catch (IOException e) {
            LoggerUtils.error("Failed to load configuration properties: " + e.getMessage());
            throw new RuntimeException("Failed to load configuration file: " + CONFIG_FILE_PATH);
        }
    }
    
    /**
     * Get property value by key
     * @param key property key
     * @return property value
     */
    public static String getProperty(String key) {
        if (properties == null) {
            loadProperties();
        }
        
        // Check for system property first (allows runtime overrides)
        String systemValue = System.getProperty(key);
        if (systemValue != null) {
            return systemValue;
        }
        
        String value = properties.getProperty(key);
        if (value == null) {
            LoggerUtils.warn("Property not found: " + key);
        }
        return value;
    }
    
    /**
     * Get property with default value
     * @param key property key
     * @param defaultValue default value if key not found
     * @return property value or default
     */
    public static String getProperty(String key, String defaultValue) {
        String value = getProperty(key);
        return (value != null) ? value : defaultValue;
    }
}
