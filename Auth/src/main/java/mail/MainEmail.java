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
        Session session = Session.getDefaultInstance(properties);

        MessageUtil messageUtil = new MessageUtil(properties, session);
        MailSender mailSender = new MailSender(session);

        mailSender.mailClient(messageUtil.generateMessageWithoutAttach(
                "smcompanytest@gmail.com",
                "Fetching 25/08/2021",
                "Hi, it's test message. 4:39 pm")
        );
        mailSender.mailClient(messageUtil.generateMessageWithAttach(
                "smcompanytest@gmail.com",
                "Fetching - attach file",
                "Hi, it's test message. 6:15 pm"
        ));
        mailSender.mailClient(messageUtil.generateMessageWithInlineImage(
                "smcompanytest@gmail.com",
                "Fetching - inline image 7"
        ));
    }
}
