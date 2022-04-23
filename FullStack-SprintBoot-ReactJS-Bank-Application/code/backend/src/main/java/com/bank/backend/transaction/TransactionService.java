package com.bank.backend.transaction;

import com.bank.backend.interfaces.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Creates new transactions.
 */
@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /**
     *
     * @param transaction The transaction to be saved to the database.
     * @return The transaction that was saved.
     */
    public Transaction addTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
}
