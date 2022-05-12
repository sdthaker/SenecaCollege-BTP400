package com.bank.backend.bankaccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Route Controller for Bank Account.
 * Controls GET/POST/PUT/DELETE operations
 */
@RestController
@RequestMapping(path = "api/bankAccount")
@CrossOrigin("*")
public class BankAccountController {
    /**
     * BankAccountService instance used to call the methods defined in the service.
     */
    private final BankAccountService bankAccountService;

    /**
     * Initializes the controller's attributes with argument values.
     * @param bankAccountService Initializes the class' attribute.
     */
    @Autowired
    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    /**
     * Get route for checking balance for an account number.
     * @param acctNum Account number on which the querying needs to happen.
     * @return Balance of the account number.
     */
    @GetMapping(path = "checkBalance")
    public Double getBalance(Long acctNum) {
        return bankAccountService.getBalance(acctNum);
    }

    /**
     * Get route for getting all the bank accounts for a specific user.
     * @param userId User Id on which the querying needs to happen.
     * @return A List of Bank Accounts for a specific user.
     */
    @GetMapping(path = "getAllBankAccount")
    public ResponseEntity<List<BankAccount>> getAllBankAccounts(Long userId) {
        return new ResponseEntity<>(bankAccountService.getAllBankAccounts(userId),
                                    HttpStatus.OK);
    }

    /**
     * Put route for depositing money into a bank account.
     * @param acctNum Account number where the money needs to be deposited.
     * @param amount Amount that needs to be deposited.
     * @return A boolean indicating whether the deposit was successful.
     */
    @PutMapping(path = "deposit")
    public boolean deposit(Long acctNum, Double amount){
        return bankAccountService.deposit(acctNum,amount);
    }

    /**
     * Put route for withdrawing money from a bank account.
     * @param acctNum Account number where the money needs to be withdrawn.
     * @param amount Amount that needs to be withdrawn.
     * @return A boolean indicating whether the withdrawal was successful.
     */
    @PutMapping(path = "withdraw")
    public boolean withdraw(Long acctNum, Double amount){
        return bankAccountService.withdraw(acctNum, amount);
    }

    /**
     * Put route for transferring money from one bank account to another.
     * @param from Source bank account number.
     * @param to Destination account number;
     * @param amount Amount that needs to be transferred.
     * @return A boolean indicating whether the withdrawal was successful.
     */
    @PutMapping(path = "transfer")
    public boolean transfer(Long from, Long to, Double amount){
        return bankAccountService.transfer(from, to, amount);
    }
}
