package mail;

import groovy.beans.PropertyReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Properties {
    public String getProperty(String property) throws IOException {
        InputStream input = PropertyReader.class.getClassLoader().getResourceAsStream("config.properties");
        java.util.Properties prop = new java.util.Properties();
        prop.load(input);
        return prop.getProperty(property);
    }
}
