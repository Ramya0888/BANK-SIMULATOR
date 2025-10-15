package com.simulator.bank.service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {

    private static final String HOST = "smtp.gmail.com";
    private static final int PORT = 587;
    private static final String USERNAME = "rsbank968@gmail.com";  
    private static final String PASSWORD = "mqae rizj jhfc keuf"; 

    private Session createSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", PORT);

        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });
    }

    public void sendEmail(String to, String subject, String messageText) {
        try {
            Session session = createSession();
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME, "SimBank Notifications"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(messageText);

            Transport.send(message);
            System.out.println("Email sent successfully to " + to);
        } catch (Exception e) {
            System.err.println(" Failed to send email: " + e.getMessage());
        }
    }
}
