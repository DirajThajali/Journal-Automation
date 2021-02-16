package com.project.journalautomation.services;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import static com.project.journalautomation.services.Config.*;

@Service
public class EmailSender implements EmailService {

    @Override
    public void sendMail(String recipient) throws MessagingException {
        Properties prop = setProperties();

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(getUserName(), getPassword());
            }
        });

        Message message = draftEmail(session, getUserName(), recipient);

        Transport.send(message);
    }

    private Message draftEmail(Session session, String from, String to) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));

            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(to));

            message.setSubject("Beautifully Crafted Journal");
            // TODO: make the subject dynamic each time (have other options for adverb and adjective)

            String content = "Here will be my journal entry beautifully edited with html";
            message.setText(content);
            return  message;

            // TODO: be able to add files as well
//            MimeBodyPart mimeBodyPart = new MimeBodyPart();
//            mimeBodyPart.setContent(msg, "text/html");
//
//            Multipart multipart = new MimeMultipart();
//            multipart.addBodyPart(mimeBodyPart);
//
//            message.setContent(multipart);
        } catch (Exception e) {
            throw new IllegalStateException("There was an error: " + e.getMessage());
        }
    }
}
