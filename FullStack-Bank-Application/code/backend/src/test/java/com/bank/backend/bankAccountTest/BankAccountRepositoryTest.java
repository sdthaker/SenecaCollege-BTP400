package com.bank.backend.bankAccountTest;

import com.bank.backend.bankaccount.BankAccount;
import com.bank.backend.interfaces.BankAccountRepository;
import com.bank.backend.interfaces.TransactionRepository;
import com.bank.backend.interfaces.UserAccountRepository;
import com.bank.backend.transaction.Transaction;
import com.bank.backend.transaction.TransactionType;
import com.bank.backend.userAccount.UserAccount;
import com.bank.backend.security.access.UserAccountRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Tester class that tests database functionality.
 */
@SpringBootTest
class BankAccountRepositoryTest {
    /**
     * Reference to BankAccountRepository
     */
    @Autowired
    private BankAccountRepository bankAccountRepository;

    /**
     * Reference to UserAccountRepository
     */
    @Autowired
    UserAccountRepository userAccountRepository;

    /**
     * Reference to TransactionRepository
     */
    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * Tests creating user, associating a bank account and associating transaction to
     * that bank account in the database.
     */
    @Test
    public void createBankAccount(){

        UserAccount ua1 = new UserAccount("Omar", "Hussein",
                "ohussein2@myseneca.ca", "binAdmin",
                new BCryptPasswordEncoder().encode("12345"),
                LocalDate.of(2020, 2, 25),
                UserAccountRole.ADMIN);

        UserAccount ua2 = new UserAccount("Soham", "Thaker",
                "sthaker@myseneca.ca", "belFast",
                new BCryptPasswordEncoder().encode("12345"),
                LocalDate.of(2020, 2, 25),
                UserAccountRole.ADMIN);

        UserAccount ua3 = new UserAccount("Philippe", "Cormier",
                "pcormier3@myseneca.ca", "bigBrain",
                new BCryptPasswordEncoder().encode("12345"),
                LocalDate.of(2020, 2, 25),
                UserAccountRole.ADMIN);

        UserAccount ua4 = new UserAccount("Sam", "Tucker",
                "sam@email.ca", "bigBrain",
                new BCryptPasswordEncoder().encode("12345"),
                LocalDate.of(2020, 2, 25),
                UserAccountRole.ADMIN);

        BankAccount bc1 = new BankAccount(53.2, ua1);
        BankAccount bc2 = new BankAccount(34.2, ua1);
        BankAccount bc3 = new BankAccount(23.4, ua1);

        BankAccount bc4 = new BankAccount(100000.0, ua2);

        BankAccount bc5 = new BankAccount(200000.0, ua3);

        Transaction tr1 = new Transaction(34.23, bc1, bc2, TransactionType.TRANSFER);
        Transaction tr2 = new Transaction(45.45, bc2, null, TransactionType.DEPOSIT);
        Transaction tr3 = new Transaction(12.12, null, bc3, TransactionType.WITHDRAW);

        transactionRepository.saveAll(List.of(tr1,tr2,tr3));
        bankAccountRepository.saveAll(List.of(bc1, bc2, bc3, bc4, bc5));
    }

    /**
     * Tests updating balance for a bank account.
     */
    @Test
    public void updateBalance() {
        int updated = bankAccountRepository.updateBalance(1L, 23.23);
        if(updated > 0){
            System.out.println("Updated balance!");
        }
        else{
            System.out.println("Provided account number was not found!");
        }
    }

    /**
     * Tests deleting a bank account.
     */
    @Test
    public void deleteBankAccount() {
        bankAccountRepository.deleteById(1L);
        System.out.println("User Account deleted!");
    }

    /**
     * Reads all the bank account present in the database.
     */
    @Test
    public void readAllBankAccount(){
        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
        System.out.println(bankAccountList);
    }

    /**
     * Reads a specific bank account based on account number passed.
     */
    @Test
    public void readOneBankAccount(){
        Optional<BankAccount> bankAccountList = bankAccountRepository.findById(4L);
        System.out.println(bankAccountList);
    }
}