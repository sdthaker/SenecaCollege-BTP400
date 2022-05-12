package com.bank.backend.userAccount;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Route Controller for User Account.
 * Controls GET/POST/PUT/DELETE operations
 */
@RestController
@RequestMapping(path="api/users")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@AllArgsConstructor
public class UserAccountController {

    private final UserAccountService userService;

    /**
     * Get all users route
     * @return user response object / status
     */
    @GetMapping("all")
    public ResponseEntity<List<UserAccount>> getUsers() {
        return ResponseEntity
                .ok()
                .body(userService.getUsers());
    }

    /**
     * Get single user route
     * @param email user email
     * @return user response object / status
     */
    @GetMapping("user")
    public ResponseEntity<Optional<UserAccount>> getUser(@RequestParam("email") String email) {
        return ResponseEntity
                .ok()
                .body(userService.getUser(email));
    }

    /**
     * Delete route for single user
     * @param id user id
     * @return response object / status
     */
    @DeleteMapping( path = "remove/{studentID}")
    public ResponseEntity<String> deleteUser(@PathVariable("studentID") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.accepted().body("Removed userID: " + id);
    }

    /**
     * Put route for single user
     * @param id user id
     * @param username optional username
     * @param email optional email
     * @return response object / status
     */
    @PutMapping("update/{id}")
    public ResponseEntity<String> updateUser(
        @PathVariable("id") Long id,
        @RequestParam(required = false) String username,
        @RequestParam(required = false) String email) {

        userService.updateUser(id, username, email);
        return ResponseEntity.accepted().body("Updated userID: " + id);
    }

    /**
     * Post route for adding bank account
     * @param userId user id
     * @return response object / status
     */
    @PostMapping(path = "createAccount")
    public ResponseEntity<Boolean> addBankAccount(Long userId) {
        ResponseEntity<Boolean> response;
        boolean updated = userService.addBankAccount(userId);

        if(updated)
            response = new ResponseEntity<>(updated, HttpStatus.CREATED);
        else
            response = new ResponseEntity<>(updated, HttpStatus.BAD_REQUEST);

        return response;
    }
}
