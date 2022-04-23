package com.bank.backend.transactionTest;

import com.bank.backend.interfaces.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TransactionRepositoryTest {
    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    public void addTransaction(){

    }

}