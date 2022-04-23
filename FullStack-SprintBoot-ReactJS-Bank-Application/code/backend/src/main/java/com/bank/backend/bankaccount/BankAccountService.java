package com.bank.backend.bankaccount;
import com.bank.backend.interfaces.BankAccountRepository;
import com.bank.backend.interfaces.TransactionRepository;
import com.bank.backend.transaction.Transaction;
import com.bank.backend.transaction.TransactionService;
import com.bank.backend.transaction.TransactionType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

/**
 * Service class that makes calls to the
 * repository class to retrieve data from database.
 */
@Service
@AllArgsConstructor
public class BankAccountService {
    /**
     * Bank Account Repository instance.
     */
    private final BankAccountRepository bankAccountRepository;

    /**
     * Transaction Repository instance.
     */
    private final TransactionRepository transactionRepository;

    /**
     * Gets the balance for a specific bank account.
     * @param acctNum The account number on which the querying needs to happen.
     * @return Balance of the bank account.
     */
    public Double getBalance(Long acctNum) {
        Optional<BankAccount> bankAccount =
                bankAccountRepository.findById(acctNum);
        return bankAccount.orElseThrow().getBalance();
    }

    /**
     * Gets all bank accounts for a specific user.
     * @param userId The user id on which the querying needs to happen.
     * @return List of bank accounts for a specific user.
     */
    public List<BankAccount> getAllBankAccounts(Long userId) {
        return bankAccountRepository.getAllBankAccountsByID(userId);
    }

    /**
     * Transfers an amount from one bank account to another.
     * @param from Source bank account number.
     * @param to Destination bank account number.
     * @param amount Amount that needs to be transferred.
     * @return Boolean indicating whether a transfer was successful.
     */
    public boolean transfer(Long from, Long to, Double amount) {
        Double fromBalance = bankAccountRepository.getBalanceByAccountNumber(from);
        Double toBalance = bankAccountRepository.getBalanceByAccountNumber(to);

        if(fromBalance >= amount) {
            fromBalance -= amount;
            toBalance += amount;

            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.CEILING);

            int updatedFromRows = bankAccountRepository.updateBalance(from,
                                Double.valueOf(df.format(fromBalance)));
            int updatedToRows = bankAccountRepository.updateBalance(to,
                                Double.valueOf(df.format(toBalance)));

            Optional<BankAccount> fromBA = bankAccountRepository.findById(from);
            Optional<BankAccount> toBA = bankAccountRepository.findById(to);

            if(updatedFromRows > 0
                    && updatedToRows > 0
                    && fromBA.isPresent()
                    && toBA.isPresent()) {

                Transaction tr = new Transaction(amount, fromBA.get(),
                        toBA.get(), TransactionType.TRANSFER);

                TransactionService ts = new TransactionService(transactionRepository);
                ts.addTransaction(tr);
            }

            return (updatedToRows + updatedFromRows) > 0;
        }

        return false;
    }

    /**
     * Deposits an amount to a bank account.
     * @param acctNum Account number where the money is supposed to be deposited.
     * @param amount Amount that needs to be deposited.
     * @return Boolean indicating whether a deposit was successful.
     */
    public boolean deposit(Long acctNum, Double amount) {
        double updatedBalance = getBalance(acctNum) + amount;

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);

        int updatedRows = bankAccountRepository.updateBalance(acctNum,
                                    Double.valueOf(df.format(updatedBalance)));
        Optional<BankAccount> ba = bankAccountRepository.findById(acctNum);

        if(updatedRows > 0 && ba.isPresent()) {
            System.out.println("inside deposit");
            Transaction tr = new Transaction(amount, null,
                    ba.get(), TransactionType.DEPOSIT);

            TransactionService ts = new TransactionService(transactionRepository);
            ts.addTransaction(tr);
        }

        return updatedRows > 0;
    }

    /**
     * Withdraws an amount to a bank account.
     * @param acctNum Account number where the money is supposed to be withdrawn.
     * @param amount Amount that needs to be withdrawn.
     * @return Boolean indicating whether a withdrawal was successful.
     */
    public boolean withdraw(Long acctNum, Double amount) {
        double currentBalance = getBalance(acctNum);
        if(currentBalance > 0 && currentBalance >= amount) {
            double updatedBalance = currentBalance - amount;

            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.CEILING);

            int updatedRows = bankAccountRepository.updateBalance(acctNum,
                                    Double.valueOf(df.format(updatedBalance)));

            Optional<BankAccount> ba = bankAccountRepository.findById(acctNum);

            if(updatedRows > 0 && ba.isPresent()) {
                Transaction tr = new Transaction(amount, ba.get(),
                        null, TransactionType.WITHDRAW);

                TransactionService ts = new TransactionService(transactionRepository);
                ts.addTransaction(tr);
            }

            return updatedRows > 0;
        }
        return false;
    }

    /**
     * Adds a bank account to the database.
     * @param bankAccount Bank account that needs to be added to the database.
     * @return Saved Bank Account object.
     */
    public BankAccount addBankAccount(BankAccount bankAccount) {
        return bankAccountRepository.save(bankAccount);
    }
}