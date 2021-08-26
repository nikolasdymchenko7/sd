package mail;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class HTMLParse {
    private Document htmlFile = null;

    public Document getHtmlPage(Properties properties){
        try{
            htmlFile = Jsoup.parse(new File(properties.getProperty("pathHtmlFile")), "ISO-8859-1");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return htmlFile;
    }
}
