package com.bank.backend.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Filter user requests and handle authorities
 */
public class AuthorizationFilter extends OncePerRequestFilter {


    /**
     * Override for filter handling, permits user access based on permissions
     * @param request request object
     * @param response response object
     * @param filterChain filter chain
     * @throws ServletException unable to perform filtering
     * @throws IOException unable to communicate with client
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/login")
                || request.getServletPath().equals("/api/token/refresh")
                || request.getServletPath().matches("/api/users/registration/*"))
            filterChain.doFilter(request, response);

        else {
            String authHeader = request.getHeader(AUTHORIZATION);
            if(authHeader != null) {
                try {
                    Algorithm algorithm = Algorithm.HMAC256("SECRET".getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(authHeader);
                    String username = decodedJWT.getSubject();
                    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, null, null));
                    filterChain.doFilter(request, response);
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
            else filterChain.doFilter(request, response);
        }
    }
}
