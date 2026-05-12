package service.util;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * Implementation using ActiveMQ for queuing and JavaMail for sending.
 * Requires activemq-all.jar and javax.mail.jar.
 */
public class ActiveMQNotificationService implements NotificationService {

    private String brokerUrl = "tcp://localhost:61616"; // Default ActiveMQ port
    private String queueName = "OTP_QUEUE";

    // Email Config
    private static final String EMAIL_FROM = "elienshimyumuremyi72@gmail.com";
    private static final String EMAIL_PASSWORD = "rhcaoxdwvwirwuzr";

    public ActiveMQNotificationService() {
    }

    @Override
    public void sendEmail(String toAddress, String subject, String messageText) {
        String payload = "EMAIL:" + toAddress + "|" + subject + "|" + messageText;
        processMessage(payload); // Direct processing
    }

    @Override
    public void sendSMS(String phoneNumber, String message) {
        System.out.println("[SMS STUB] Sending SMS to " + phoneNumber + ": " + message);
    }

    private void sendToQueue(String messageContent) {
        // JMS Code kept commented until ActiveMQ is fully set up
    }

    private void processMessage(String queueMessage) {
        try {
            if (queueMessage.startsWith("EMAIL:")) {
                String[] parts = queueMessage.substring(6).split("\\|", 3);
                if (parts.length == 3) {
                    performEmailSend(parts[0], parts[1], parts[2]);
                }
            }
        } catch (Exception e) {
            System.err.println("Error processing message: " + e.getMessage());
        }
    }

    private void performEmailSend(String to, String subject, String body) {
        System.out.println("Attempting to send real email to: " + to);

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_FROM, EMAIL_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_FROM));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("Real Email sent successfully to " + to);

        } catch (Exception e) {
            System.err.println("Email Send Failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
