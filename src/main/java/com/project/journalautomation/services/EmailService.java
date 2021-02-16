package com.project.journalautomation.services;

import javax.mail.MessagingException;

public interface EmailService {
    public void sendMail(String recipient) throws MessagingException;
}
