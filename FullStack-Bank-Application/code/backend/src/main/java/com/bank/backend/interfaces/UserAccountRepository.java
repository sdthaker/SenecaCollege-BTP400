package com.bank.backend.interfaces;

import com.bank.backend.userAccount.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * The repository for interfacing with the user entity.
 */
@Repository
@Transactional(readOnly = true)
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    /**
     * @param email The email by which a user will be found
     * @return The account of the found user.
     */
    @Query("SELECT u FROM UserAccount u WHERE u.email = ?1")
    Optional<UserAccount> findUserAccountByEmail(String email);

    /**
     * @param username The username by which a user will be found.
     * @return The account of the found user.
     */
    @Query("SELECT u FROM UserAccount u WHERE u.username = ?1")
    Optional<UserAccount> findUserAccountByUsername(String username);

    /**
     * Sets enabled to true.
     * @param email The email by which a user will be found.
     * @return The status of the update.
     */
    @Transactional
    @Modifying
    @Query("""
            UPDATE UserAccount a
            SET a.enabled = TRUE WHERE a.email = ?1""")
    int enableUserAccount(String email);
}
