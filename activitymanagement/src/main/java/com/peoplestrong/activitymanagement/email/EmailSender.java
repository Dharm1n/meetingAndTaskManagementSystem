package com.peoplestrong.activitymanagement.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailSender {
    @Autowired
    private JavaMailSender mailSender;

    public void sendemail(String toEmail,String subject,String body)
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);
    }

}
