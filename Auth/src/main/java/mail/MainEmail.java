package mail;

import groovy.beans.PropertyReader;

import javax.mail.MessagingException;
import javax.mail.Session;
import java.io.IOException;
import java.util.Properties;

public class MainEmail {
    public static void main(String[] args) throws IOException, MessagingException {
        Properties properties = new Properties();
        properties.load(PropertyReader.class.getClassLoader().getResourceAsStream("config.properties"));

        MailSender mailSender = new MailSender(properties, Session.getDefaultInstance(properties));

        mailSender.mailClient(mailSender.sendMessageWithoutAttach());
        mailSender.mailClient(mailSender.sendMessageWithAttach());
        mailSender.mailClient(mailSender.sendMessageWithInlineImage());

    }
}
