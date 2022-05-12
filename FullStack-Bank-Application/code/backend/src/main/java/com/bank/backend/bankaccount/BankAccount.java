package com.bank.backend.bankaccount;

import com.bank.backend.userAccount.UserAccount;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Model for Bank Account
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table
public class BankAccount implements Serializable {

    /**
     * Auto-generated, auto-incremented, unique ID.
     */
    @Id
    @SequenceGenerator(
            name = "bankAccount_sequence",
            sequenceName = "bankAccount_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "bankAccount_sequence"
    )
    private Long accountNumber;

    /**
     * Current balance of the bank account.
     */
    private Double balance;

    /**
     * Reference to the account holder.
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_account")
    private UserAccount userAccount;

    /**
     * Initializes all the attributes with argument values.
     * @param balance Current balance of the bank account.
     * @param userAccount Reference to the account holder.
     */
    public BankAccount(Double balance, UserAccount userAccount) {
        this.balance = balance;
        this.userAccount = userAccount;
    }
}

