package com.example.app_server.SubscriptionDetails;
import com.example.app_server.SubscriptionDetails.Subscription;
import com.example.app_server.UserAccountCreation.EmailService;
import com.example.app_server.UserAccountCreation.User;
import com.example.app_server.UserAccountCreation.UserRepository;
import com.example.app_server.notifications.FirebaseNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    @Autowired
    private FirebaseNotificationService firebaseNotificationService;

    @Autowired
    private EmailService mailService;


    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository, UserRepository userRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
    }

    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    public Optional<Subscription> getSubscriptionById(String id) {
        return subscriptionRepository.findById(id);
    }

    @Transactional
    public Subscription createSubscription(Subscription subscription) {
        User inputUser = subscription.getUser();

        if (inputUser == null || inputUser.getMrnId() == null) {
            throw new RuntimeException("User MRN ID is required.");
        }

        // Fetch managed User entity from DB using MRN ID
        User user = userRepository.findByMrnId(inputUser.getMrnId())
                .orElseThrow(() -> new RuntimeException("User not found with MRN ID: " + inputUser.getMrnId()));

        // Check if the user already has an approved subscription
        List<Subscription> existing = subscriptionRepository.findByUserAndStatus(user, SubscriptionStatus.APPROVED);
        if (!existing.isEmpty()) {
            throw new RuntimeException("User already has an active subscription.");
        }

        subscription.setUser(user); // Set the managed user entity
        subscription.setId(generateSubscriptionId());
        subscription.setStatus(SubscriptionStatus.PENDING);

        return subscriptionRepository.save(subscription);
    }



    public Subscription updateSubscription(String id, Subscription updatedSubscription) {
        return subscriptionRepository.findById(id).map(subscription -> {
            subscription.setProduct(updatedSubscription.getProduct());
            subscription.setUser(updatedSubscription.getUser());
            subscription.setDateOfPurchase(updatedSubscription.getDateOfPurchase());
            subscription.setPayment_method(updatedSubscription.getPayment_method());
            subscription.setAmount(updatedSubscription.getAmount());
            return subscriptionRepository.save(subscription);
        }).orElseThrow(() -> new RuntimeException("Subscription not found with id " + id));
    }

    @Transactional
    public Subscription updateSubscriptionStatus(String id, String statusStr) {
        SubscriptionStatus newStatus = SubscriptionStatus.valueOf(statusStr.toUpperCase());
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscription not found."));

        User user = subscription.getUser();

        if (newStatus == SubscriptionStatus.APPROVED) {
            List<Subscription> existing = subscriptionRepository.findByUserAndStatus(user, SubscriptionStatus.APPROVED);

            boolean alreadyActive = existing.stream()
                    .anyMatch(s -> !s.getId().equals(subscription.getId()));

            if (alreadyActive) {
                throw new RuntimeException("User already has an active subscription.");
            }

            user.setSubscriptionActive(true);
            userRepository.save(user);

            if (user.getEmail() != null) {
                String subject = "Subscription Approved";
                String body = "Dear " + user.getFirstName() + ",\n\n" +
                        "We’re pleased to inform you that your subscription has been successfully approved.\n\n" +
                        "You now have access to all features included in your plan.\n\n" +
                        "If you have any questions or need assistance, please reach out to our support team.\n\n" +
                        "Best regards,\n" +
                        "The Support Team\n" +
                        "[Your Company Name]";

                mailService.sendEmail(user.getEmail(), subject, body);
            }

        } else if (newStatus == SubscriptionStatus.REJECTED) {
            user.setSubscriptionActive(false);
            userRepository.save(user);

            if (user.getEmail() != null) {
                String subject = "Subscription Rejected";
                String body = "Dear " + user.getFirstName() + ",\n\n" +
                        "We regret to inform you that your subscription request has been rejected.\n\n" +
                        "This could be due to incomplete details or unmet criteria. For clarification, feel free to contact our support team.\n\n" +
                        "We encourage you to reapply after addressing the necessary requirements.\n\n" +
                        "Best regards,\n" +
                        "The Support Team\n" +
                        "[Your Company Name]";

                mailService.sendEmail(user.getEmail(), subject, body);
            }
        }

        subscription.setStatus(newStatus);
        return subscriptionRepository.save(subscription);
    }


    public void deleteSubscription(String id) {
        subscriptionRepository.deleteById(id);
    }

    // Helper method to generate a custom ID
    private String generateSubscriptionId() {
        // Find the last inserted ID
        String lastId = subscriptionRepository.findTopByOrderByIdDesc()
                .map(Subscription::getId)
                .orElse("SUB000"); // Default to "SUB000" if no record exists

        // Extract the numeric part, increment it, and format it back to SUB001 format
        int newIdNum = Integer.parseInt(lastId.substring(3)) + 1;
        return String.format("SUB%03d", newIdNum); // Formats the number to three digits
    }
}

