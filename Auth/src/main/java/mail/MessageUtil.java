package mail;

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

public class MessageUtil extends HTMLParse {
    private Properties properties;
    private Session mailSession;
    private MimeMessage message;

    public MessageUtil(){}

    public MessageUtil(Properties properties,
                       Session mailSession){
        this.properties = properties;
        this.mailSession = mailSession;
    }

    public MimeMessage generateMessageWithoutAttach(
            String recipient,
            String subject,
            String text) throws MessagingException {
        message = setMessage(mailSession);
        message.setFrom(new InternetAddress(properties.getProperty("mail.smtps.user")));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        message.setSubject(subject);
        message.setText(text);
        return message;
    }
    public MimeMessage generateMessageWithAttach(
            String recipient,
            String subject,
            String text) throws MessagingException, IOException {

        message = setMessage(mailSession);

        message.setFrom(new InternetAddress(properties.getProperty("mail.smtps.user")));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        message.setSubject(subject);

        Multipart emailContent = new MimeMultipart();

        MimeBodyPart textBodyPart = new MimeBodyPart();
        textBodyPart.setText(text);

        MimeBodyPart attachment = new MimeBodyPart();

        attachment.attachFile(properties.getProperty("path"));
        emailContent.addBodyPart(textBodyPart);
        emailContent.addBodyPart(attachment);

        message.setContent(emailContent);

        return message;
    }

    public MimeMessage generateMessageWithInlineImage(
            String recipient,
            String subject
    ) throws MessagingException, IOException {
        message = setMessage(mailSession);

        message.setFrom(new InternetAddress(properties.getProperty("mail.smtps.user")));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        message.setSubject(subject);

        MimeMultipart multipart = new MimeMultipart("related");

        BodyPart messageBodyPart = new MimeBodyPart();

        Document html = getHtmlPage(properties);

        messageBodyPart.setContent(html.toString(), "text/html");
        messageBodyPart = new MimeBodyPart();

        DataSource fds = new FileDataSource(
                properties.getProperty("path"));

        messageBodyPart.setDataHandler(new DataHandler(fds));
        messageBodyPart.setHeader("Content-ID", "test");

        multipart.addBodyPart(messageBodyPart);

        message.setContent(multipart);

        return message;
    }

    private MimeMessage setMessage(Session mailSession) {
        return new MimeMessage(mailSession);
    }
}