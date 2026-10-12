package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigManager {

    private static final String DEFAULT_ENV = "qa";

    private static final Properties properties = new Properties();

    static {

        String environment = System.getProperty("env", DEFAULT_ENV).toLowerCase();

        String fileName = "config/" + environment + ".properties";

        try (InputStream inputStream = ConfigManager.class.getClassLoader().getResourceAsStream(fileName)) 
        {

            if (inputStream == null) 
            {
                throw new RuntimeException("Configuration file not found: " + fileName);
            }

            properties.load(inputStream);

        } 
        catch (IOException e) 
        {

            throw new RuntimeException("Unable to load configuration: " + fileName, e);
        }
    }

    private ConfigManager() {}

    public static String get(String key) {

        /* System properties override property files. Example: mvn clean test -Denv=qa -Demail=test@test.com  */

        String systemValue = System.getProperty(key);

        if (systemValue != null && !systemValue.isBlank()) 
        {

            return systemValue;
        }

        String value = properties.getProperty(key);

        if (value == null) {

            throw new RuntimeException("Configuration key not found: " + key);
        }

        return value;
    }

    public static String getEnvironment() {

        return System.getProperty("env", DEFAULT_ENV);
    }
}
