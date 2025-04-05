//package com.example.app_server.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeHttpRequests(authz -> authz
//                        .requestMatchers("/auth/**").permitAll()  // Allow authentication endpoints
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                        .requestMatchers("/subadmin/**").hasRole("SUB_ADMIN")
//                        .requestMatchers("/staff/**").hasRole("STAFF")
//                        .requestMatchers(
//                                "/api/doctor/login",
//                                "/api/doctors",
//                                "/api/doctors/logout/**",
//                                "/api/hospitals",
//                                "/api/account/create",
//                                "/api/account/verify",
//                                "/api/account/complete-profile",
//                                "/api/account/update/**",
//                                "/api/account/delete/**",
//                                "/api/account/userlogin",
//                                "/api/account/verify-otp",
//                                "/api/counselors/**",
//                                "/bookings/book",
//                                "/api/products/**",
//                                "/api/carts/**",
//                                "/api/subscriptions/**",
//                                "/api/tests/**",
//                                "/api/bookings/**",
//                                "/api/counsellor-bookings/**",
//                                "/api/phlebotomist-bookings/**",
//                                "/api/doctor-bookings/**",
//                                "/api/dietician-bookings/**",
//                                "/api/physiotherapist-bookings/**",
//                                "/api/prescriptions/**",
//                                "/api/hospitals/**",
//                                "/api/excel/**",
//                                "/api/admin/**",
//                                "/api/exercise-charts/**",
//                                "/api/exercise-details/**",
//                                "/api/diet-charts/**",
//                                "/api/diets/**",
//                                "/api/reports/**"
//                        ).permitAll()
//                        .anyRequest().authenticated())
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        return http.build();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}



//package com.example.app_server.security;
//
//import com.example.app_server.Roles.JwtAuthenticationFilter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
//        http.csrf().disable()
//                .authorizeHttpRequests(authz -> authz
//                        .requestMatchers("/auth/**").permitAll()  // Allow authentication endpoints
//                        .requestMatchers("/admin/**").hasAuthority("ADMIN")   // Secure Admin endpoints
//                        .requestMatchers("/subadmin/**").hasAuthority("SUB_ADMIN") // Secure Sub-Admin
//                        .requestMatchers("/staff/**").hasAuthority("STAFF")   // Secure Staff
//                        .requestMatchers(
//                                "/api/doctor/login",
//                                "/api/doctors",
//                                "/api/doctors/logout/**",
//                                "/api/hospitals",
//                                "/api/account/create",
//                                "/api/account/verify",
//                                "/api/account/complete-profile",
//                                "/api/account/update/**",
//                                "/api/account/delete/**",
//                                "/api/account/userlogin",
//                                "/api/account/verify-otp",
//                                "/api/counselors/**",
//                                "/bookings/book",
//                                "/api/products/**",
//                                "/api/carts/**",
//                                "/api/subscriptions/**",
//                                "/api/tests/**",
//                                "/api/bookings/**",
//                                "/api/counsellor-bookings/**",
//                                "/api/phlebotomist-bookings/**",
//                                "/api/doctor-bookings/**",
//                                "/api/dietician-bookings/**",
//                                "/api/physiotherapist-bookings/**",
//                                "/api/prescriptions/**",
//                                "/api/hospitals/**",
//                                "/api/excel/**",
//                                "/api/exercise-charts/**",
//                                "/api/exercise-details/**",
//                                "/api/diet-charts/**",
//                                "/api/diets/**",
//                                "/api/reports/**"
//                        ).permitAll()
//                        .anyRequest().authenticated())
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        return http.build();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
package com.example.app_server.security;

import com.example.app_server.Roles.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/auth/**").permitAll()  // Allow authentication endpoints
                        .requestMatchers("/admin/**").hasRole("ADMIN")   // Secure Admin endpoints
                        .requestMatchers("/subadmin/create-staff").hasAnyRole("SUB_ADMIN", "DIETICIAN", "DOCTOR", "PHLEBOTOMIST", "PHYSIO", "LAB")// Secure Sub-Admin
                        .requestMatchers("/staff/**").hasRole("STAFF")   // Secure Staff
                        .requestMatchers(
                                "/api/doctor/login",
                                "/api/doctors",
                                "/api/doctors/logout/**",
                                "/api/hospitals",
                                "/api/account/create",
                                "/api/account/verify",
                                "/api/account/complete-profile",
                                "/api/account/update/**",
                                "/api/account/delete/**",
                                "/api/account/userlogin",
                                "/api/account/login",
                                "/api/account/verify-otp",
                                "api/account/reset-password",
                                "api/account/change-password",
                                "api/account/logout",
                                "/api/counselors/**",
                                "/bookings/book",
                                "/api/products/**",
                                "/api/carts/**",
                                "/api/subscriptions/**",
                                "/api/tests/**",
                                "/api/bookings/**",
                                "/api/counsellor-bookings/**",
                                "/api/phlebotomist-bookings/**",
                                "/api/doctor-bookings/**",
                                "/api/dietician-bookings/**",
                                "/api/physiotherapist-bookings/**",
                                "/api/prescriptions/**",
                                "/api/hospitals/**",
                                "/api/excel/**",
                                "/api/exercise-charts/**",
                                "/api/exercise-details/**",
                                "/api/diet-charts/**",
                                "/api/diets/**",
                                "/api/reports/**"
                        ).permitAll()
                        .anyRequest().authenticated())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Ensure JWT authentication is applied

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
