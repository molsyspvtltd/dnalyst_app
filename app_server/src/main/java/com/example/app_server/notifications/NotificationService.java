//package com.example.app_server.notifications;
//
//import com.google.firebase.messaging.*;
//import org.springframework.stereotype.Service;
//
//@Service
//public class NotificationService {
//
//    public String sendNotification(String title, String message, String token) {
//        Message notificationMessage = Message.builder()
//                .setToken(token)
//                .setNotification(Notification.builder()
//                        .setTitle(title)
//                        .setBody(message)
//                        .build())
//                .build();
//
//        try {
//            String response = FirebaseMessaging.getInstance().send(notificationMessage);
//            return "Notification sent successfully: " + response;
//        } catch (FirebaseMessagingException e) {
//            e.printStackTrace();
//            return "Error sending notification: " + e.getMessage();
//        }
//    }
//}
