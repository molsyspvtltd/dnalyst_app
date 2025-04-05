package com.example.app_server.notifications;

import com.example.app_server.UserAccountCreation.User;
import com.example.app_server.UserAccountCreation.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationScheduler {

    private final UserRepository userRepository;
    private final FirebaseNotificationService firebaseService;

    public NotificationScheduler(UserRepository userRepository, FirebaseNotificationService firebaseService) {
        this.userRepository = userRepository;
        this.firebaseService = firebaseService;
    }

    private void sendNotificationToUsers(String title, String message) {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            String deviceToken = user.getDeviceToken();
            if (deviceToken != null && !deviceToken.isEmpty()) {
                String response = firebaseService.sendNotification(title, message, deviceToken);
                System.out.println(response);
            }
        }
    }

    @Scheduled(cron = "0 0 6 * * ?", zone = "Asia/Kolkata") // 6 AM IST
    public void sendMorningMotivation() {
        sendNotificationToUsers("Good Morning!", "Start your day with energy and positivity!");
    }

    @Scheduled(cron = "0 0 9 * * ?", zone = "Asia/Kolkata") // 9 AM IST
    public void sendHealthTip() {
        sendNotificationToUsers("Health Tip!", "Drink plenty of water and stay hydrated.");
    }

    @Scheduled(cron = "0 0 13 * * ?", zone = "Asia/Kolkata") // 1 PM IST
    public void sendMiddayReminder() {
        sendNotificationToUsers("Stay Active!", "Take a short walk or stretch to keep yourself refreshed.");
    }

    @Scheduled(cron = "0 0 16 * * ?", zone = "Asia/Kolkata") // 4 PM IST
    public void sendWorkoutReminder() {
        sendNotificationToUsers("Workout Time!", "Get moving! A little exercise goes a long way.");
    }

    @Scheduled(cron = "0 0 20 * * ?", zone = "Asia/Kolkata") // 8 PM IST
    public void sendRelaxationTip() {
        sendNotificationToUsers("Relax & Unwind!", "Take deep breaths and enjoy some quiet time.");
    }

    @Scheduled(cron = "0 0 22 * * ?", zone = "Asia/Kolkata") // 10 PM IST
    public void sendGoodnightMessage() {
        sendNotificationToUsers("Good Night!", "Rest well and recharge for a new day ahead.");
    }
}
