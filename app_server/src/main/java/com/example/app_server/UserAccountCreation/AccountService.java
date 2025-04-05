//package com.example.app_server.UserAccountCreation;
//
//import com.example.app_server.TwilioConfig;
//import com.twilio.rest.api.v2010.account.Message;
//import com.twilio.type.PhoneNumber;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Random;
//
//@Service
//public class AccountService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private LoginStatusRepository loginStatusRepository;
//
//    @Autowired
//    private TwilioConfig twilioConfig;
//
//    public User createAccount(String phoneNumber) {
//        User existingUser = userRepository.findBySimilarPhoneNumber(phoneNumber);
//        if (existingUser != null) {
//            throw new IllegalArgumentException("Phone number is already in use.");
//        }
//
//        User user = new User();
//        user.setPhoneNumber(phoneNumber);
//        user.setVerificationCode(generateVerificationCode());
//        user.setVerified(false);
//        sendSms(phoneNumber, user.getVerificationCode());
//        return userRepository.save(user);
//    }
//
//    public boolean verifyAccount(String phoneNumber, String verificationCode) {
//        User user = userRepository.findByPhoneNumberAndVerificationCode(phoneNumber, verificationCode);
//        if (user != null) {
//            user.setVerified(true);
//            userRepository.save(user);
//            return true;
//        }
//        return false;
//    }
//
//
//    public User completeProfile(String phoneNumber, String fullName, LocalDate dob, String gender, String email, String age) {
//        User user = userRepository.findByPhoneNumber(phoneNumber);
//        if (user != null) {
//            user.setFullName(fullName);
//            user.setDateOfBirth(dob);
//            user.setGender(gender);
//            user.setEmail(email);
//            user.setAge(age); // Changed to set age as String
//            user.setVerified(true); // or however you set the verified status
//            userRepository.save(user);
//        }
//        return user;
//    }
//
//    public boolean sendOtp(String phoneNumber) {
//        User user = userRepository.findByPhoneNumber(phoneNumber);
//        if (user != null) {
//            String otp = generateVerificationCode();
//            user.setVerificationCode(otp);
//            userRepository.save(user);
//            sendSms(phoneNumber, otp);
//            return true;
//        }
//        return false;
//    }
//
////    public boolean verifyOtp(String phoneNumber, String otp) {
////        User user = userRepository.findByPhoneNumberAndVerificationCode(phoneNumber, otp);
////        if (user != null) {
////            createLoginStatus(user, true);
////            return true;
////        }
////        return false;
////    }
//
//    public User verifyOtp(String phoneNumber, String otp) {
//        User user = userRepository.findByPhoneNumberAndVerificationCode(phoneNumber, otp);
//        if (user != null) {
//            createLoginStatus(user, true);
//            return user;
//        }
//        return null;
//    }
//
//    private void createLoginStatus(User user, boolean isLoggedIn) {
//        LoginStatus loginStatus = new LoginStatus();
//        loginStatus.setPhoneNumber(user.getPhoneNumber());
//        loginStatus.setLoggedIn(isLoggedIn);
//        loginStatusRepository.save(loginStatus);
//    }
//
//    private String generateVerificationCode() {
//        Random random = new Random();
//        return String.format("%06d", random.nextInt(1000000));
//    }
//
//    private void sendSms(String toPhoneNumber, String verificationCode) {
//        Message.creator(new PhoneNumber(toPhoneNumber), new PhoneNumber(twilioConfig.getFromPhoneNumber()),
//                "Your verification code is: " + verificationCode).create();
//    }
//    public boolean logout(String phoneNumber) {
//        List<LoginStatus> loginStatusList = loginStatusRepository.findByPhoneNumber(phoneNumber);
//        if (!loginStatusList.isEmpty()) {
//            for (LoginStatus loginStatus : loginStatusList) {
//                loginStatusRepository.delete(loginStatus);
//            }
//            return true;
//        }
//        return false;
//    }
//}
//
//


package com.example.app_server.UserAccountCreation;

import com.example.app_server.TwilioConfig;
import com.example.app_server.notifications.FirebaseNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class AccountService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginStatusRepository loginStatusRepository;

    @Autowired
    private TwilioConfig twilioConfig;

    @Autowired
    private FirebaseNotificationService firebaseNotificationService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);


    // Method to create a user account with custom MRN ID
//    public User createAccount(String phoneNumber) {
//        User existingUser = userRepository.findBySimilarPhoneNumber(phoneNumber);
//        if (existingUser != null) {
//            throw new IllegalArgumentException("Phone number is already in use.");
//        }
//
//        User user = new User();
//        user.setPhoneNumber(phoneNumber);
//        user.setVerificationCode(generateVerificationCode());
//        user.setVerified(false);
//        user.setMrnId(generatemrnId());  // Custom method to generate MRN ID
//        sendSms(phoneNumber, user.getVerificationCode());
//        userRepository.save(user);
//
//        firebaseNotificationService.sendNotification("Welcome! " +user.getFirstName() , " Thank you for registering.", user.getDeviceToken());
//
//        return user;
//    }


    public User createAccount(String email, String password) {
        if (userRepository.findByEmail(email) != null) {
            throw new IllegalArgumentException("Email is already in use.");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password)); // Secure password
        user.setVerificationCode(generateVerificationCode());
        user.setVerified(false);
        user.setMrnId(generatemrnId());
        userRepository.save(user);

        sendVerificationEmail(email, user.getVerificationCode(),"register");

        return user;
    }





//    public boolean verifyAccount(String phoneNumber, String verificationCode) {
//        User user = userRepository.findByPhoneNumberAndVerificationCode(phoneNumber, verificationCode);
//        if (user != null) {
//            user.setVerified(true);
//            userRepository.save(user);
//            return true;
//        }
//        return false;
//    }

    public boolean verifyAccount(String email, String verificationCode) {
        User user = userRepository.findByEmailAndVerificationCode(email, verificationCode);
        if (user != null) {
            user.setVerified(true);
            user.setVerificationCode(null);
            userRepository.save(user);
            return true;
        }
        return false;
    }


    public User completeProfile(String email, String firstName, String lastName, LocalDate dob, String gender, String phoneNumber, String age) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setDateOfBirth(dob);
            user.setGender(gender);
            user.setPhoneNumber(phoneNumber);
            user.setAge(age);
            user.setVerified(true);
            userRepository.save(user);
        }
        return user;
    }


    public boolean sendOtp(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            logger.warn("Attempted to send OTP to non-existent email: {}", email);
            return false;
        }

        String otp = generateVerificationCode();
        user.setVerificationCode(otp);
        userRepository.save(user);

        String subject = "OTP Verification - Action Required";
        String body = "Dear User,\n\n" +
                "To proceed with your verification, please use the following One-Time Password (OTP):\n\n" +
                otp + "\n\n" +
                "This OTP is valid for a limited time and should not be shared with anyone.\n\n" +
                "If you did not request this, please ignore this email.\n\n" +
                "Best regards,\n" +
                "The Support Team\n" +
                "[Your Company Name]";

        emailService.sendEmail(email, subject, body);
        return true;
    }



    // OTP verification for login
    public User verifyOtp(String email, String otp, String userAgent) {
        User user = userRepository.findByEmailAndVerificationCode(email, otp);
        if (user != null) {
            user.setVerified(true); // ✅ Mark the user as verified
            createLoginStatus(user, true, email);
            userRepository.save(user); // ✅ Save the updated user
            // ✅ Log login attempt with userAgent
            logger.info("User {} verified successfully. Device: {}", email, userAgent);
            return user;
        }
        return null;
    }



//    // Create login status for the user
//    private void createLoginStatus(User user, boolean isLoggedIn) {
//        LoginStatus loginStatus = new LoginStatus();
//        loginStatus.setPhoneNumber(user.getPhoneNumber());
//        loginStatus.setLoggedIn(isLoggedIn);
//        loginStatusRepository.save(loginStatus);
//    }
//    public User verifyOtp(String phoneNumber, String otp) {
//        User user = userRepository.findByPhoneNumberAndVerificationCode(phoneNumber, otp);
//        if (user != null) {
//            // Mark the user as verified (if not already)
//            user.setVerified(true);
//            userRepository.save(user); // Save the user to update the verification status
//
//            // Create login status for the user
//            createLoginStatus(user, true);
//
//            return user;
//        }
//        return null;
//    }
//
//    // Create login status for the user
//    private void createLoginStatus(User user, boolean isLoggedIn) {
//        LoginStatus loginStatus = new LoginStatus();
//        loginStatus.setUser(user);  // Associate the LoginStatus with the User
//        loginStatus.setLoggedIn(isLoggedIn);
//        loginStatusRepository.save(loginStatus);  // Save the login status to the repository
//    }


//    public User verifyOtp(String phoneNumber, String otp) {
//        // Find the user by phone number and OTP
//        User user = userRepository.findByPhoneNumberAndVerificationCode(phoneNumber, otp);
//
//        if (user != null) {
//            // Mark the user as verified (if not already)
//            user.setVerified(true);
//            userRepository.save(user); // Save the user to update the verification status
//
//            // Create login status for the user
//            createLoginStatus(user, true, phoneNumber);  // Pass the phone number to the method
//
//            return user;
//        }
//        return null;
//    }

//    public User verifyOtp(String phoneNumber, String otp, String userAgent) {
//        // Find the user by phone number and OTP
//        User user = userRepository.findByPhoneNumberAndVerificationCode(phoneNumber, otp);
//
//        if (user != null) {
//            if (!user.isVerified()) {
//                // Mark user as verified
//                user.setVerified(true);
//                userRepository.save(user); // Save user status update
//            }
//
//            // Save login status
//            createLoginStatus(user, true, phoneNumber);
//
//            // ✅ Now `userAgent` is correctly passed
//            saveUserDeviceInfo(user, userAgent);
//
//            logger.info("User {} verified successfully. Device: {}", phoneNumber, userAgent);
//
//            return user;
//        }
//
//        logger.warn("OTP verification failed for {}", phoneNumber);
//        return null;
//    }

    public User login(String email, String password, String userAgent) {
        User user = userRepository.findByEmail(email);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            if (!user.isVerified()) {
                throw new IllegalArgumentException("Account is not verified.");
            }

            createLoginStatus(user, true, email);
            saveUserDeviceInfo(user, userAgent);

            return user;
        }

        logger.warn("Login failed for {}", email);
        return null;
    }


    public boolean sendPasswordResetEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            String resetCode = generateVerificationCode();
            user.setVerificationCode(resetCode);
            userRepository.save(user);
            sendVerificationEmail(email, resetCode, "reset");
            return true;
        }
        return false;
    }

    public boolean resetPassword(String email, String resetCode, String newPassword) {
        User user = userRepository.findByEmailAndVerificationCode(email, resetCode);
        if (user != null) {
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setVerificationCode(null);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    // Create login status for the user
    private void createLoginStatus(User user, boolean isLoggedIn, String email) {
        LoginStatus loginStatus = new LoginStatus();
        loginStatus.setUser(user);  // Associate the LoginStatus with the User
        loginStatus.setLoggedIn(isLoggedIn);
        loginStatus.setEmail(email);  // Save the phone number in the LoginStatus
        loginStatus.setLoginTime(LocalDateTime.now());  // Optionally, save login time
        loginStatusRepository.save(loginStatus);  // Save the login status to the repository
    }



    // Generate a 6-digit verification code
    private String generateVerificationCode() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }

    // Send SMS with the verification code
//    private void sendSms(String toPhoneNumber, String verificationCode) {
//        Message.creator(new PhoneNumber(toPhoneNumber), new PhoneNumber(twilioConfig.getFromPhoneNumber()),
//                "Your verification code is: " + verificationCode).create();
//    }


    // Logout the user by deleting login status records
//    public boolean logout(String phoneNumber) {
//        List<LoginStatus> loginStatusList = loginStatusRepository.findByPhoneNumber(phoneNumber);
//        if (!loginStatusList.isEmpty()) {
//            for (LoginStatus loginStatus : loginStatusList) {
//                loginStatusRepository.delete(loginStatus);
//            }
//            return true;
//        }
//        return false;
//    }

    public boolean logout(String email) {
        List<LoginStatus> loginStatusList = loginStatusRepository.findByEmail(email);
        if (!loginStatusList.isEmpty()) {
            for (LoginStatus loginStatus : loginStatusList) {
                loginStatusRepository.delete(loginStatus);
            }
            return true;
        }
        return false;
    }

    // Custom method to generate the MRN ID
    private String generatemrnId() {
        long mrnIdNumber = userRepository.count() + 10000001;  // Assuming count starts from 10000001
        return "MRN" + mrnIdNumber;
    }

    private void sendVerificationEmail(String toEmail, String verificationCode, String purpose) {
        String subject;
        String body;

        if ("register".equalsIgnoreCase(purpose)) {
            subject = "Account Verification - Action Required";
            body = "Dear User,\n\n" +
                    "Thank you for registering with us.\n\n" +
                    "To complete your registration, please use the following verification code:\n\n" +
                    verificationCode + "\n\n" +
                    "If you did not initiate this request, please ignore this email.\n\n" +
                    "Best regards,\n" +
                    "The Support Team\n" +
                    "[Your Company Name]";
        } else if ("reset".equalsIgnoreCase(purpose)) {
            subject = "Password Reset Request";
            body = "Dear User,\n\n" +
                    "We received a request to reset your account password.\n\n" +
                    "Please use the following verification code to reset your password:\n\n" +
                    verificationCode + "\n\n" +
                    "If you did not request a password reset, please ignore this email.\n\n" +
                    "Best regards,\n" +
                    "The Support Team\n" +
                    "[Your Company Name]";
        } else {
            subject = "Verification Code";
            body = "Dear User,\n\n" +
                    "Your verification code is:\n\n" +
                    verificationCode + "\n\n" +
                    "Best regards,\n" +
                    "The Support Team\n" +
                    "[Your Company Name]";
        }

        emailService.sendEmail(toEmail, subject, body);
    }



    public void saveUserDeviceInfo(User user, String userAgent) {
        if (userAgent != null && !userAgent.isEmpty()) {
            user.setDeviceInfo(userAgent); // Assuming `User` entity has a `deviceInfo` field
            userRepository.save(user);
            logger.info("Device info updated for user {}: {}", user.getEmail(), userAgent);
        }
    }

}

