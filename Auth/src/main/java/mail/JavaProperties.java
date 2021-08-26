package mail;

import groovy.beans.PropertyReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JavaProperties {
    public String getProperty(String property) throws IOException {
        InputStream input = PropertyReader.class.getClassLoader().getResourceAsStream("config.properties");
        Properties prop = new Properties();
        prop.load(input);
        return prop.getProperty(property);
    }
}
