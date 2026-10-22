package service;

/**
 * Контракт сервиса уведомлений. Billing знает только об этом интерфейсе,
 * но не о конкретных реализациях (слабая связанность).
 */
public interface NotificationService {
    void sendNotification(String message, String recipient);
}
