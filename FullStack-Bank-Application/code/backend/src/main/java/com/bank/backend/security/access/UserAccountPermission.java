package com.bank.backend.security.access;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * User permission sets
 */
@Getter
@AllArgsConstructor
public enum UserAccountPermission {
    USER_ACCOUNT_READ("user_account:read"),
    USER_ACCOUNT_WRITE("user_account:write"),
    TRANSACTION_READ("transaction:read"),
    TRANSACTION_WRITE("transaction:write"),
    BANK_ACCOUNT_READ("bank_account:read"),
    BANK_ACCOUNT_WRITE("bank_account:write");

    private final String permission;
}
