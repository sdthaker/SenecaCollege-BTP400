package com.bank.backend.transaction;

import com.bank.backend.bankaccount.BankAccount;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Records all transactions made between bank accounts
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table
public class Transaction implements Serializable {
    @Id
    @SequenceGenerator(
            name = "transaction_sequence",
            sequenceName = "transaction_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "transaction_sequence"
    )
    private Long id;
    private Double value;
    private String transactionType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "source")
    private BankAccount source;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "destination")
    private BankAccount destination;

    /**
     * The constructor for a transaction.
     * @param value The amount of money that was part of the transaction.
     * @param source The bank account from which the money is moved from.
     * @param destination The bank account from which the money is moved to.
     * @param transactionType The type of the transaction (WITHDRAW, DEPOSIT, TRANSFER)
     */
    public Transaction(Double value, BankAccount source,
                       BankAccount destination, TransactionType transactionType) {
        this.value = value;
        this.source = source;
        this.destination = destination;
        if(transactionType == TransactionType.TRANSFER){
            this.transactionType = "Transfer";
        }
        else if(transactionType == TransactionType.DEPOSIT){
            this.transactionType = "Deposit";
        }
        else{
            this.transactionType = "Withdraw";
        }
    }
}
