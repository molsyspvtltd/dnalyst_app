package com.example.app_server.notifications;

import com.google.firebase.messaging.*;
import org.springframework.stereotype.Service;

@Service
public class FirebaseNotificationService {

    public String sendNotification(String title, String message, String token) {
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(message)
                .build();

        Message firebaseMessage = Message.builder()
                .setToken(token)
                .setNotification(notification)
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(firebaseMessage);
            return "Notification sent successfully: " + response;
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            return "Error sending notification: " + e.getMessage();
        }
    }
}
