package com.bank.backend.userAccount.registration.email;

import com.bank.backend.interfaces.EmailSender;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Confirmation email sender
 */
@Service
@AllArgsConstructor
public class EmailService implements EmailSender {

    private static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    /**
     * Send email override
     * @param to send to
     * @param email email address
     */
    @Override
    @Async
    public void send(String to, String email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Verify your Email!");
            helper.setFrom("77292466+omalk98@users.noreply.github.com");
            mailSender.send(mimeMessage);
        }
        catch (MessagingException e) {
            LOGGER.error("Failed to send Email", e);
            throw new IllegalStateException("Failed to send Email");
        }
    }
}
