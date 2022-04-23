package com.bank.backend.userAccount.registration;

import lombok.*;

import java.time.LocalDate;

/**
 * User Registration Model
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private LocalDate dateOfBirth;
}
