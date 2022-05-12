package com.bank.backend.userAccount;

import com.bank.backend.bankaccount.BankAccount;
import com.bank.backend.bankaccount.BankAccountService;
import com.bank.backend.interfaces.BankAccountRepository;
import com.bank.backend.interfaces.UserAccountRepository;
import com.bank.backend.userAccount.registration.token.ConfirmationToken;
import com.bank.backend.userAccount.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * User Account Service handler for Database CRUD operations
 */
@Service
@AllArgsConstructor
public class UserAccountService implements UserDetailsService {

    private final static String USER_NOT_FOUND = "User <<%s>> does NOT Exist";
    private final static String USER_ID_NOT_FOUND = "User with ID: %d does NOT Exist";
    private final static String EMAIL_IN_USE = "The E-mail <<%s>> is already is Use";
    private final static String USERNAME_IN_USE = "The Username <<%s>> is already is Use";

    private final UserAccountRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final BankAccountService bankService;

    /**
     * Get all user accounts from database
     * @return user account list
     */
    public List<UserAccount> getUsers() {
        return userRepository.findAll();
    }

    /**
     * Get a single user from database
     * @param email user email
     * @return user account
     */
    public Optional<UserAccount> getUser(String email) {
        return userRepository.findUserAccountByEmail(email);
    }

    /**
     * Sign up new user and adds their details to database
     * @param user user details
     * @return confirmation token
     */
    public String signupNewUser(UserAccount user) {
        if (userRepository.findUserAccountByEmail(user.getEmail()).isPresent())
            throw new IllegalStateException(String.format(EMAIL_IN_USE, user.getEmail()));
        if (userRepository.findUserAccountByUsername(user.getUsername()).isPresent())
            throw new IllegalStateException(String.format(USERNAME_IN_USE, user.getUsername()));

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        String token = UUID.randomUUID().toString();
        confirmationTokenService.saveConfirmationToken(new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        ));

        return token;
    }

    /**
     * Delete user from database
     * @param id user id
     */
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id))
            throw new IllegalStateException();
    }

    /**
     * Update user details
     * @param id user id
     * @param username optional username
     * @param email optional email
     */
    @Transactional
    public void updateUser(Long id, String username, String email) {
        UserAccount user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(String.format(USER_ID_NOT_FOUND, id)));

        if (username != null && username.length() > 0 && !Objects.equals(user.getUsername(), username))
            user.setUsername(username);

        if (email != null && email.length() > 0 && !Objects.equals(user.getEmail(), email)) {
            if (userRepository.findUserAccountByEmail(email).isPresent())
                throw new IllegalStateException(String.format(EMAIL_IN_USE, email));
            user.setEmail(email);
        }
    }

    /**
     * Enable user account for login
     * @param email user email
     * @return status
     */
    public int enableUserAccount(String email) {
        return userRepository.enableUserAccount(email);
    }

    /**
     * Login provider functionality override
     * @param username username/email
     * @return user details
     * @throws UsernameNotFoundException user not foound in database
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return username.contains("@")
                ? userRepository.findUserAccountByEmail(username).orElseThrow(()->new UsernameNotFoundException(String.format(USER_NOT_FOUND, username)))
                : userRepository.findUserAccountByUsername(username).orElseThrow(()->new UsernameNotFoundException(String.format(USER_NOT_FOUND, username)));
    }

    /**
     * Add bank account to User
     * @param userId user id
     * @return status
     */
    public boolean addBankAccount(Long userId) {
        Optional<UserAccount> ua = userRepository.findById(userId);
        if(ua.isPresent()) {
            BankAccount ba = new BankAccount(0.0, ua.get());
            bankService.addBankAccount(ba);
            return true;
        }
        return false;
    }
}
