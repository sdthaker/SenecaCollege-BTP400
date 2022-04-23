package com.bank.backend.userAccount.registration.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bank.backend.userAccount.UserAccount;
import com.bank.backend.userAccount.UserAccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Route controller for JWT token refresh.
 * controls GET operations
 */
@RestController
@RequestMapping(path="api")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@AllArgsConstructor
public class JWTTokenController {

    private final UserAccountService userService;

    /**
     * Refreshes user access token as long as refresh token is still valid
     * @param request request object
     * @param response response object
     * @throws IOException unable to communicate with client
     */
    @GetMapping("token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String refreshToken = request.getHeader(AUTHORIZATION);
        if(refreshToken != null) {
            try {
                Algorithm algorithm = Algorithm.HMAC256("SECRET".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                UserAccount user = userService.getUser(username)
                        .orElseThrow(() -> new UsernameNotFoundException(String.format("Username <<%s>> not found", username)));
                String accessToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", refreshToken);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            }
            catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setHeader("Error", e.getMessage());
                Map<String, String> error = new HashMap<>();
                error.put("cod", Integer.toString(HttpServletResponse.SC_FORBIDDEN));
                error.put("msg", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        }
        else throw new IllegalStateException("Refresh token is missing");
    }
}
