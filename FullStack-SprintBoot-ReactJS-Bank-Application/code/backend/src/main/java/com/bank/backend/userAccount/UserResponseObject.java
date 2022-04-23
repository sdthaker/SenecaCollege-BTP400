package com.bank.backend.userAccount;

import com.bank.backend.security.access.UserAccountRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * Stripped down User Account Object for response purposes leaving sensitive data hidden.
 */
@AllArgsConstructor
@Getter
public class UserResponseObject implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private UserAccountRole userRole;

    /**
     * Constructor takes a user account object and strips it of sensitive data
     * @param user user account
     */
    public UserResponseObject(UserAccount user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.userRole = user.getUserRole();
    }
}
