package com.backend.CrimsonCompass.service;

public interface IEmailService {
    void sendEmail(String to, String subject, String body);
}
