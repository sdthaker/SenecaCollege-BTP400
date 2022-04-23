package com.bank.backend.userAccount.registration;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * Route controller for Registration.
 * Controls GET/POST operations
 */
@RestController
@RequestMapping(path = "api/user/registration")
@CrossOrigin("*")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    /**
     *
     * @param request The body of the request.
     * @return The status and the registration token.
     */
    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegistrationRequest request) {
        return ResponseEntity
                .created(URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/registration/register").toUriString()))
                .body(registrationService.register(request));
    }

    /**
     *
     * @param token The confirmation token.
     * @return The confirmation status.
     */
    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}
