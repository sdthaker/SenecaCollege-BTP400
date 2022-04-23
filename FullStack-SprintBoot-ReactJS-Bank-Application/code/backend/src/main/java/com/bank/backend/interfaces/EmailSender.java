package com.bank.backend.interfaces;

/**
 * Handles the functionality to send emails
 */
public interface EmailSender {

    /**
     * Sends an email.
     * @param to The email to which the message will be sent.
     * @param email The email body that will be sent.
     */
    void send(String to, String email);
}
