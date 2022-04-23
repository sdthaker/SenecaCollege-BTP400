package com.bank.backend.userAccountTest;

import com.bank.backend.interfaces.UserAccountRepository;
import com.bank.backend.userAccount.UserAccount;
import com.bank.backend.security.access.UserAccountRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class UserAccountRepositoryTest {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Test
    public void createAccounts() {
        UserAccount ua1 = new UserAccount("Omar", "Hussein", "ohussein2@myseneca.ca", "binAdmin", new BCryptPasswordEncoder().encode("12345"), LocalDate.of(2020, 2, 25), UserAccountRole.ADMIN);
        UserAccount ua2 = new UserAccount("Soham", "Thaker", "sthaker@myseneca.ca", "belFast", new BCryptPasswordEncoder().encode("12345"), LocalDate.of(2020, 2, 25), UserAccountRole.ADMIN);
        UserAccount ua3 = new UserAccount("Philippe", "Cormier", "pcormier3@myseneca.ca", "bigBrain", new BCryptPasswordEncoder().encode("12345"), LocalDate.of(2020, 2, 25), UserAccountRole.ADMIN);

        userAccountRepository.saveAll(List.of(ua1, ua2, ua3));
    }

}