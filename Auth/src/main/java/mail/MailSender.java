package mail;

import groovy.beans.PropertyReader;
import org.jsoup.nodes.Document;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Properties;

public class MailSender extends HTMLParse {
    private Session mailSession;

    public MailSender(){}

    public MailSender(Session mailSession){
        this.mailSession = mailSession;
    }

    public void mailClient(MimeMessage message) throws MessagingException {
        Transport transport = mailSession.getTransport();
        transport.connect(null, "testusersmcompany123");
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
}