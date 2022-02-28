package com.peoplestrong.activitymanagement.api;

import java.net.URI;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.peoplestrong.activitymanagement.payload.response.MessageResponse;
import com.peoplestrong.activitymanagement.service.EmailService;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.sun.net.httpserver.Authenticator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.peoplestrong.activitymanagement.models.User;
import com.peoplestrong.activitymanagement.service.UserService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api") @Slf4j
public class PasswordController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

@PostMapping("/forgot-password")
public ResponseEntity<?> processForgotPasswordForm(HttpServletRequest request,@RequestParam("username") String username) {

    Optional<User> optional = userService.findByUsername(username);

    if (!optional.isPresent()) {
        return ResponseEntity.badRequest().body(new MessageResponse("Error:User does not exists"));
    } else {

        URI uri=URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/forgot-password").toUriString());
        User user = optional.get();
        // Generate random 36-character string token for reset password
        user.setResetToken(UUID.randomUUID().toString());

        // Save token to database
        userService.saveUser(user);

        String appUrl = request.getScheme() + "://" + request.getServerName();
        String to=user.getUsername();
        String subject="Password Reset Request";
        String message="To reset your password, click the link below:\n" + appUrl + "/reset-password?token=" + user.getResetToken();
        emailService.sendEmail(subject,message,to);
        return ResponseEntity.status(HttpStatus.OK).body("Reset Password link has been set to your email");
    }
}
    @PostMapping("/reset-password")
    public ResponseEntity<?> setNewPassword(@RequestBody Map<String, String> body) {
        Optional<User> user = userService.findUserByResetToken(body.get("token"));
        if (user.isPresent()) {
            URI uri=URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/reset-password").toUriString());
            User resetUser = user.get();
            resetUser.setPassword(body.get("password"));
            resetUser.setResetToken(null);
            userService.saveUser(resetUser);
            return new ResponseEntity<Authenticator.Success>(HttpStatus.OK);
        }
        else {
            return ResponseEntity.badRequest().body(new MessageResponse("Error:User does not exists"));
        }
    }
}