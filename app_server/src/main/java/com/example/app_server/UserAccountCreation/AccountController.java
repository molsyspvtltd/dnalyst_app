//
//
//package com.example.app_server.UserAccountCreation;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.Map;
//import javax.validation.Valid;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//@RestController
//@RequestMapping("/api/account")
//@Validated
//public class AccountController {
//
//    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
//
//    @Autowired
//    private AccountService accountService;
//
//
//
//    @PostMapping("/create")
//    public ResponseEntity<User> createAccount(@RequestBody @Valid UserCreationRequest request) {
//        logger.info("Creating account for phone number: {}", request.getPhoneNumber());
//        User user = accountService.createAccount(request.getPhoneNumber());
//        return ResponseEntity.ok(user);
//    }
//
//    @PostMapping("/verify")
//    public ResponseEntity<Map<String, Object>> verifyAccount(@RequestBody @Valid VerifyAccountRequest request) {
//        logger.info("Verifying account for phone number: {}", request.getPhoneNumber());
//        boolean verified = accountService.verifyAccount(request.getPhoneNumber(), request.getVerificationCode());
//
//        Map<String, Object> response = new HashMap<>();
//        if (verified) {
//            response.put("message", "Account verified successfully.");
//            response.put("status", "success");
//            return ResponseEntity.ok(response);
//        } else {
//            response.put("message", "Verification failed.");
//            response.put("status", "error");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//        }
//    }
//
//    @PostMapping("/complete-profile")
//    public ResponseEntity<Map<String, Object>> completeProfile(@RequestBody @Valid CompleteProfileRequest request) {
//        logger.info("Completing profile for phone number: {}", request.getPhoneNumber());
//        Map<String, Object> response = new HashMap<>();
//        try {
//            LocalDate dob = LocalDate.parse(request.getDateOfBirth());
//            User user = accountService.completeProfile(
//                    request.getPhoneNumber(),
//                    request.getFirstName(),
//                    request.getLastName(),
//                    dob,
//                    request.getGender(),
//                    request.getEmail(),
//                    String.valueOf(request.getAge()) // Pass age as Integer
//            );
//
//            if (user != null) {
//                response.put("message", "Profile completed successfully.");
//                response.put("status", "success");
//                response.put("user", user); // Include the user object
//                response.put("id", user.getId()); // Assuming user object has getId method
//                response.put("firstName",user.getFirstName());
//                response.put("lastName",user.getLastName());
//                response.put("email", user.getEmail());
//                return ResponseEntity.ok(response);
//            } else {
//                response.put("message", "Failed to complete profile.");
//                response.put("status", "error");
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//            }
//        } catch (Exception e) {
//            logger.error("Error occurred while completing profile for phone number: {}", request.getPhoneNumber(), e);
//            response.put("message", "An error occurred: " + e.getMessage());
//            response.put("status", "error");
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//        }
//    }
//
//    @PostMapping("/userlogin")
//    public ResponseEntity<String> requestOtp(@RequestBody @Valid OtpRequest request) {
//        logger.info("Requesting OTP for phone number: {}", request.getPhoneNumber());
//        boolean otpSent = accountService.sendOtp(request.getPhoneNumber());
//        if (otpSent) {
//            return ResponseEntity.ok("OTP sent successfully.");
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to send OTP.");
//        }
//    }
//
////    @PostMapping("/verify-otp")
////    public ResponseEntity<Map<String, Object>> verifyOtp(@RequestBody @Valid OtpRequest request) {
////        logger.info("Verifying OTP for phone number: {}", request.getPhoneNumber());
////        User user = accountService.verifyOtp(request.getPhoneNumber(), request.getOtp());
////        Map<String, Object> response = new HashMap<>();
////
////        if (user.isVerified()) {
////            response.put("message", "Login successful.");
////            response.put("id", user.getId());
////            response.put("status", "success");
////            return ResponseEntity.ok(response);
////        } else {
////            response.put("message", "Invalid OTP.");
////            response.put("status", "error");
////            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
////        }
////    }
//
//    @PostMapping("/verify-otp")
//    public ResponseEntity<Map<String, Object>> verifyOtp(@RequestBody @Valid OtpRequest request) {
//        logger.info("Verifying OTP for phone number: {}", request.getPhoneNumber());
//
//        // Call service to verify OTP and save phone number in LoginStatus
//        User user = accountService.verifyOtp(request.getPhoneNumber(), request.getOtp());
//
//        Map<String, Object> response = new HashMap<>();
//
//        if (user.isVerified()) {
//            response.put("message", "Login successful.");
//            response.put("id", user.getId());
//            response.put("firstName", user.getFirstName());
//            response.put("lastName", user.getLastName());
//            response.put("email", user.getEmail());
//            response.put("status", "success");
//
//            return ResponseEntity.ok(response);
//        } else {
//            response.put("message", "Invalid OTP.");
//            response.put("status", "error");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//        }
//    }
//
//
//    @PostMapping("/logout")
//    public ResponseEntity<String> logout(@RequestBody @Valid LogoutRequest request) {
//        logger.info("Logging out user with phone number: {}", request.getPhoneNumber());
//        boolean loggedOut = accountService.logout(request.getPhoneNumber());
//        if (loggedOut) {
//            return ResponseEntity.ok("Logged out successfully.");
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to log out.");
//        }
//    }
//
//}
//
//class OtpRequest {
//    private String phoneNumber;
//    private String otp;
//
//    // Getters and Setters
//
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }
//
//    public String getOtp() {
//        return otp;
//    }
//
//    public void setOtp(String otp) {
//        this.otp = otp;
//    }
//}
//
//class LogoutRequest {
//    private String phoneNumber;
//
//    // Getters and Setters
//
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }
//}
//
//class UserCreationRequest {
//    private String phoneNumber;
//
//    // Getters and Setters
//
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }
//}
//
//class VerifyAccountRequest {
//    private String phoneNumber;
//    private String verificationCode;
//
//    // Getters and Setters
//
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }
//
//    public String getVerificationCode() {
//        return verificationCode;
//    }
//
//    public void setVerificationCode(String verificationCode) {
//        this.verificationCode = verificationCode;
//    }
//}
//
//class CompleteProfileRequest {
//    private String phoneNumber;
//    private String firstName;
//    private String lastName;
//    private String dateOfBirth;
//    private String gender;
//    private String email;
//    private Integer age;
//
//    // Getters and Setters
//
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setlastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public String getDateOfBirth() {
//        return dateOfBirth;
//    }
//
//    public void setDateOfBirth(String dateOfBirth) {
//        this.dateOfBirth = dateOfBirth;
//    }
//
//    public String getGender() {
//        return gender;
//    }
//
//    public void setGender(String gender) {
//        this.gender = gender;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public Integer getAge() {
//        return age;
//    }
//
//    public void setAge(Integer age) {
//        this.age = age;
//    }
//}
//
//class Response<T> {
//    private String status;
//    private String message;
//    private T data;
//
//    // Constructor, Getters, Setters
//    public Response(String status, String message, T data) {
//        this.status = status;
//        this.message = message;
//        this.data = data;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public T getData() {
//        return data;
//    }
//
//    public void setData(T data) {
//        this.data = data;
//    }
//}


package com.example.app_server.UserAccountCreation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/account")
@Validated
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<String> createAccount(@RequestBody @Valid UserCreationRequest request) {
        logger.info("Creating account for email: {}", request.getEmail());
        User user = accountService.createAccount(request.getEmail(), request.getPassword()); // ✅ Pass the password
        return ResponseEntity.ok("Account created successfully and a Verification code is sent to your email");
    }


    @PostMapping("/verify")
    public ResponseEntity<String> verifyAccount(@RequestBody @Valid VerifyAccountRequest request) {
        logger.info("Verifying account for email: {}", request.getEmail());
        boolean verified = accountService.verifyAccount(request.getEmail(), request.getVerificationCode());

        if (verified) {
            return ResponseEntity.ok("Account verified successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Verification failed.");
        }
    }

    @PostMapping("/complete-profile")
    public ResponseEntity<String> completeProfile(@RequestBody @Valid CompleteProfileRequest request) {
        logger.info("Completing profile for email: {}", request.getEmail());
        try {
            LocalDate dob = LocalDate.parse(request.getDateOfBirth());
            User user = accountService.completeProfile(
                    request.getEmail(),
                    request.getFirstName(),
                    request.getLastName(),
                    dob,
                    request.getGender(),
                    request.getPhoneNumber(),
                    String.valueOf(request.getAge()) // Pass age as Integer
            );

            if (user != null) {
                return ResponseEntity.ok("Profile completed successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to complete profile.");
            }
        } catch (Exception e) {
            logger.error("Error completing profile: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong.");
        }
    }

    @PostMapping("/userlogin")
    public ResponseEntity<String> requestOtp(@RequestBody @Valid OtpRequest request) {
        logger.info("Requesting OTP for email: {}", request.getEmail());
        boolean otpSent = accountService.sendOtp(request.getEmail());
        if (otpSent) {
            return ResponseEntity.ok("OTP sent successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to send OTP.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginWithPassword(@RequestBody @Valid LoginRequest request, HttpServletRequest httpRequest) {
        logger.info("Attempting login for: {}", request.getEmail());
        String userAgent = httpRequest.getHeader("User-Agent");

        try {
            User user = accountService.login(request.getEmail(), request.getPassword(), userAgent);

            if (user != null) {
                return ResponseEntity.ok("Login successful.");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
            }
        } catch (Exception e) {
            logger.error("Login error for {}: {}", request.getEmail(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed due to server error.");
        }
    }



//    @PostMapping("/verify-otp")
//    public ResponseEntity<Map<String, Object>> verifyOtp(@RequestBody @Valid OtpRequest request) {
//        logger.info("Verifying OTP for phone number: {}", request.getPhoneNumber());
//        User user = accountService.verifyOtp(request.getPhoneNumber(), request.getOtp());
//        Map<String, Object> response = new HashMap<>();
//
//        if (user.isVerified()) {
//            response.put("message", "Login successful.");
//            response.put("id", user.getId());
//            response.put("status", "success");
//            return ResponseEntity.ok(response);
//        } else {
//            response.put("message", "Invalid OTP.");
//            response.put("status", "error");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//        }
//    }

//    @PostMapping("/verify-otp")
//    public ResponseEntity<Map<String, Object>> verifyOtp(@RequestBody @Valid OtpRequest request) {
//        logger.info("Verifying OTP for phone number: {}", request.getPhoneNumber());
//
//        // Call service to verify OTP and save phone number in LoginStatus
//        User user = accountService.verifyOtp(request.getPhoneNumber(), request.getOtp());
//
//        Map<String, Object> response = new HashMap<>();
//
//        if (user.isVerified()) {
//            response.put("message", "Login successful.");
//            response.put("mrnId", user.getMrnId());
//            response.put("firstName", user.getFirstName());
//            response.put("lastName", user.getLastName());
//            response.put("email", user.getEmail());
//            response.put("status", "success");
//
//            return ResponseEntity.ok(response);
//        } else {
//            response.put("message", "Invalid OTP.");
//            response.put("status", "error");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//        }
//    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody @Valid OtpRequest request, HttpServletRequest httpRequest) {
        logger.info("Verifying OTP for email: {}", request.getEmail());
        String userAgent = httpRequest.getHeader("User-Agent");

        User user = accountService.verifyOtp(request.getEmail(), request.getOtp(), userAgent);

        if (user != null && user.isVerified()) {
            return ResponseEntity.ok("Login successful.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP.");
        }
    }



    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody @Valid LogoutRequest request) {
        logger.info("Logging out: {}", request.getEmail());
        boolean success = accountService.logout(request.getEmail());

        if (success) {
            return ResponseEntity.ok("Logged out successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Logout failed.");
        }
    }


    @PutMapping("/updateDeviceToken")
    public ResponseEntity<String> updateDeviceToken(@RequestParam String email, @RequestParam String deviceToken) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setDeviceToken(deviceToken);
            userRepository.save(user);
            return ResponseEntity.ok("Device token updated.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
    }


    @PostMapping("/reset-password")
    public ResponseEntity<String> sendResetCode(@RequestBody @Valid SendResetCodeRequest request) {
        logger.info("Sending reset code to: {}", request.getEmail());

        boolean success = accountService.sendPasswordResetEmail(request.getEmail());

        if (success) {
            return ResponseEntity.ok("Reset code sent.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found.");
        }
    }


    @PostMapping("/change-password")
    public ResponseEntity<String> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
        logger.info("Resetting password for: {}", request.getEmail());

        boolean success = accountService.resetPassword(request.getEmail(), request.getResetCode(), request.getNewPassword());

        if (success) {
            return ResponseEntity.ok("Password changed successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid reset code.");
        }
    }


}

class OtpRequest {
    private String email;
    private String otp;

    // Getters and Setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}

class LogoutRequest {
    private String email;

    // Getters and Setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

class UserCreationRequest {
    @Email
    private String email;
    @NotBlank
    private String password;

    // Getters and Setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String phoneNumber) {
        this.email = phoneNumber;
    }

    public @NotBlank String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank String password) {
        this.password = password;
    }
}

class VerifyAccountRequest {
    private String email;
    private String verificationCode;

    // Getters and Setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}

class LoginRequest {
    @Email
    private String email;

    @NotBlank
    private String password;

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

class SendResetCodeRequest {
    @NotBlank
    private String email;

    public @NotBlank String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank String email) {
        this.email = email;
    }
}


class ResetPasswordRequest {
        @NotBlank
        private String email;

        @NotBlank
        private String resetCode;

        @NotBlank
        private String newPassword;

    public @NotBlank String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank String email) {
        this.email = email;
    }

    public @NotBlank String getResetCode() {
        return resetCode;
    }

    public void setResetCode(@NotBlank String resetCode) {
        this.resetCode = resetCode;
    }

    public @NotBlank String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(@NotBlank String newPassword) {
        this.newPassword = newPassword;
    }
}

class CompleteProfileRequest {
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String gender;
    private String email;
    private Integer age;

    // Getters and Setters

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

class Response<T> {
    private String status;
    private String message;
    private T data;

    // Constructor, Getters, Setters
    public Response(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}


