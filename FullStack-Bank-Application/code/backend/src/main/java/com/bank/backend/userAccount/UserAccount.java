package com.bank.backend.userAccount;

import com.bank.backend.bankaccount.BankAccount;
import com.bank.backend.security.access.UserAccountRole;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Model for User Account
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@Entity
@Table(name="user_account",uniqueConstraints=@UniqueConstraint(columnNames={"id","username","email"}))
public class UserAccount implements UserDetails {

    @Id
    @SequenceGenerator(
            name = "user_account_sequence",
            sequenceName = "user_account_sequence",
            allocationSize = 1
    )
    @GeneratedValue (
            strategy = GenerationType.SEQUENCE,
            generator = "user_account_sequence"
    )
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserAccountRole userRole;
    private LocalDate dateOfBirth;
    private Boolean locked = false;
    private Boolean enabled = false;

    /**
     * Constructor for creating user without an assigned ID
     * @param firstName first name
     * @param lastName last name
     * @param email email
     * @param username username
     * @param password password
     * @param dateOfBirth dateOfBirth
     * @param userRole userRole
     */
    public UserAccount(String firstName, String lastName, String email,
                       String username, String password, LocalDate dateOfBirth,
                       UserAccountRole userRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.userRole = userRole;
    }

    /**
     * Override for login functionality
     * @return status
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Override for login functionality
     * @return status
     */
    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    /**
     * Override for login functionality
     * @return status
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Override for login functionality
     * @return status
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Override for login functionality
     * @return authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(userRole.name()));
    }

    /**
     * Override for login functionality
     * @return password
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Override for login functionality
     * @return username
     */
    @Override
    public String getUsername() {
        return username;
    }
}
