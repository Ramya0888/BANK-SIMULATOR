package com.simulator.bank.service;

public class EmailTest {
    public static void main(String[] args) {
        EmailService emailService = new EmailService();
        emailService.sendEmail(
            "sramyasaravanan08@gmail.com", 
            "Test Email from Bank App",
            "This is a test notification from SimBank."
        );
    }
}
