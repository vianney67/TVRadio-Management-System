package service.util;

public interface NotificationService {
    void sendEmail(String toAddress, String subject, String message);
    void sendSMS(String phoneNumber, String message);
}
