package com.bank.backend.interfaces;

import com.bank.backend.bankaccount.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Jpa repository responsible to create and update new BankAccount rows into the table.
 */
@Repository
@Transactional
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    /**
     * Updates the balance of a given bank account.
     * @param accountNumber Account number that needs to be updated.
     * @param balance New balance of the account number.
     * @return Number of rows that were affected.
     */
    @Modifying
    @Query("update BankAccount b set b.balance = ?2 where b.accountNumber = ?1")
    int updateBalance(Long accountNumber, Double balance);

    /**
     * Gets all the bank accounts for a specific user.
     * @param userId UserId whose bank accounts needs to be retrieved.
     * @return A list of all bank accounts for a specific user.
     */
    @Query(value = "select * from bank_account b where b.user_account = ?1", nativeQuery = true)
    List<BankAccount> getAllBankAccountsByID(Long userId);

    /**
     * Gets balance of an account number by Id.
     * @param id Unique account number which is a primary key for the table.
     * @return Balance of the account number.
     */
    @Query(value = "select balance from bank_account b where b.account_number = ?1", nativeQuery = true)
    Double getBalanceByAccountNumber(Long id);
}