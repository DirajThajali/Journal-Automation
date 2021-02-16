package com.project.journalautomation.controllers;

import com.project.journalautomation.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

import static com.project.journalautomation.services.Config.getUserName;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/send")
    public String send() throws MessagingException {
        emailService.sendMail(getUserName());
        return "Sent";
    }

}
