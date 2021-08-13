package mail;

import groovy.beans.PropertyReader;

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

public class JavaMainSendEmail {

    public static void main(String[] args) throws IOException, MessagingException {
        final Properties properties = new Properties();
        properties.load(PropertyReader.class.getClassLoader().getResourceAsStream("config.properties"));
        Session mailSession = Session.getDefaultInstance(properties);
        MimeMessage message = getMimeMessageWithoutAttach(properties, mailSession);
        MimeMessage messageWithAttach = getMimeMessageWithAttach(properties, mailSession);
        MimeMessage messageInlineImage = getMimeMessageWithInlineImage(properties, mailSession);

        //sendEmail(mailSession, message);
        //sendEmail(mailSession, messageWithAttach);

        sendEmail(mailSession, messageInlineImage);
    }

    private static void sendEmail(Session mailSession, MimeMessage message) throws MessagingException {
        Transport transport = mailSession.getTransport();
        transport.connect(null, "testusersmcompany123");
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    private static MimeMessage getMimeMessageWithoutAttach(Properties properties, Session mailSession) throws MessagingException {
        MimeMessage message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress(properties.getProperty("mail.smtps.user")));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("smcompanytest@gmail.com"));
        message.setSubject("Fetching 11/08/2021");
        message.setText("Hi, it's test message. 4:39 pm");
        return message;
    }
    private static MimeMessage getMimeMessageWithAttach(Properties properties, Session mailSession) throws MessagingException, IOException {

        MimeMessage message = new MimeMessage(mailSession);

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
    public static MimeMessage getMimeMessageWithInlineImage(Properties properties, Session mailSession) throws MessagingException, IOException {
        MimeMessage message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress(properties.getProperty("mail.smtps.user")));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("smcompanytest@gmail.com"));
        message.setSubject("Fetching - inline image 3");

        MimeMultipart multipart = new MimeMultipart("related");

        BodyPart messageBodyPart = new MimeBodyPart();

//        String htmlText = "<h1>Inline image</h1><img src=\"cid:image\">";
//        messageBodyPart.setContent(htmlText, "text/html");
        String htmlMessage = "<html>Hi there,<br>";
        htmlMessage += "See this cool pic: <img src=\"cid:test\" />";
        htmlMessage += "</html>";

        messageBodyPart.setContent(htmlMessage, "text/html");
        messageBodyPart = new MimeBodyPart();

        DataSource fds = new FileDataSource(
                properties.getProperty("path"));

        messageBodyPart.setDataHandler(new DataHandler(fds));
        messageBodyPart.setHeader("Content-ID", "<image>");

        multipart.addBodyPart(messageBodyPart);

        message.setContent(multipart);

        return message;
    }
}