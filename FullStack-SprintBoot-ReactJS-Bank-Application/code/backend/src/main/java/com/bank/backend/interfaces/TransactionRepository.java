package com.bank.backend.interfaces;

import com.bank.backend.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Handles the functionality to store transactions.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
