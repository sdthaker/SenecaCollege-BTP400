package com.bank.backend.userAccount.registration.email;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

/**
 * Validates email
 */
@Service
public class EmailValidator implements Predicate<String> {

    /**
     * Test email patern
     * @param email
     * @return status
     */
    @Override
    public boolean test(String email) {
        return email.matches("(.+)@(.+)\\.(.+)");
    }
}
