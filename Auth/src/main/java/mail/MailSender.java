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
    private Properties properties;
    private Session mailSession;
    private MimeMessage message;

    public MailSender(){}

    public MailSender(Properties properties,
                      Session mailSession){
        this.properties = properties;
        this.mailSession = mailSession;
    }

    public void mailClient(MimeMessage message) throws MessagingException {
        Transport transport = mailSession.getTransport();
        transport.connect(null, "testusersmcompany123");
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    public MimeMessage sendMessageWithoutAttach() throws MessagingException {
        message = getMessage(mailSession);
        message.setFrom(new InternetAddress(properties.getProperty("mail.smtps.user")));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("smcompanytest@gmail.com"));
        message.setSubject("Fetching 25/08/2021");
        message.setText("Hi, it's test message. 4:39 pm");
        return message;
    }
    public MimeMessage sendMessageWithAttach() throws MessagingException, IOException {

        message = getMessage(mailSession);

        message.setFrom(new InternetAddress(properties.getProperty("mail.smtps.user")));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("smcompanytest@gmail.com"));
        message.setSubject("Fetching - attach file");

        Multipart emailContent = new MimeMultipart();

        MimeBodyPart textBodyPart = new MimeBodyPart();
        textBodyPart.setText("Hi, it's test message. 6:15 pm");

        MimeBodyPart attachment = new MimeBodyPart();

        attachment.attachFile(properties.getProperty("path"));
        emailContent.addBodyPart(textBodyPart);
        emailContent.addBodyPart(attachment);

        message.setContent(emailContent);

        return message;
    }

    public MimeMessage sendMessageWithInlineImage() throws MessagingException, IOException {
        message = getMessage(mailSession);

        message.setFrom(new InternetAddress(properties.getProperty("mail.smtps.user")));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("smcompanytest@gmail.com"));
        message.setSubject("Fetching - inline image 7");

        MimeMultipart multipart = new MimeMultipart("related");

        BodyPart messageBodyPart = new MimeBodyPart();

        Document html = getHtmlPage(properties);

        messageBodyPart.setContent(html.toString(), "text/html");
        messageBodyPart = new MimeBodyPart();

        DataSource fds = new FileDataSource(
                properties.getProperty("path"));

        messageBodyPart.setDataHandler(new DataHandler(fds));
        messageBodyPart.setHeader("Content-ID", "test");
        messageBodyPart.setHeader("Content-Type", "image/png");

        multipart.addBodyPart(messageBodyPart);

        message.setContent(multipart);

        return message;
    }
    //Properties
    private MimeMessage getMessage(Session mailSession) {
        return new MimeMessage(mailSession);
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Session getMailSession() {
        return mailSession;
    }

    public void setMailSession(Session mailSession) {
        this.mailSession = mailSession;
    }

    public MimeMessage getMessage() {
        return message;
    }

    public void setMessage(MimeMessage message) {
        this.message = message;
    }
}