package ua.borovyk.wartruthbot.util;

import lombok.experimental.UtilityClass;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@UtilityClass
public class PropertyReader {

    private static final String resourcePath = "src/main/resources/static/text.properties";

    public static String readProperty(String propertyName) {
        var properties = readPropertiesFile();
        return properties.getProperty(propertyName);
    }

    private static Properties readPropertiesFile() {
        try (var fis = new FileInputStream(resourcePath);
             var isr = new InputStreamReader(fis, StandardCharsets.UTF_8)) {
            var properties = new Properties();
            properties.load(isr);
            return properties;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
