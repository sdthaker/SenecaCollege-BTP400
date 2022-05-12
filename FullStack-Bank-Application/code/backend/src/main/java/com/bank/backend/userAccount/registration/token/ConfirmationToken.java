package com.bank.backend.userAccount.registration.token;

import com.bank.backend.userAccount.UserAccount;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Confirmation token Model
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConfirmationToken {
    @Id
    @SequenceGenerator(
            name = "confirmation_token_sequence",
            sequenceName = "confirmation_token_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "confirmation_token_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime expiresAt;

    @Column(nullable = true)
    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "user_account_id"
    )
    private UserAccount user;

    /**
     * Constructor for creating confirmation tokens without specifying ID
     * @param token token string
     * @param createdAt created time
     * @param expiresAt expiry date
     * @param user user account
     */
    public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt, UserAccount user) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.user = user;
    }
}
