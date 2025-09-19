package com.hammad.task.services;


import com.hammad.task.domain.entities.User;
import com.hammad.task.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class UserService {

    private final AuthenticationManager authManager;
    private final UserRepository repo;
    private final JwtService jwt;
    private final TokenBlacklistService tokenBlacklistService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    public UserService(UserRepository repo, AuthenticationManager authManager, JwtService jwt, TokenBlacklistService tokenBlacklistService) {
        this.repo = repo;
        this.authManager = authManager;
        this.jwt = jwt;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    // Register a user by email and password
    public User registerUser(User user){

        user.setPassword(encoder.encode(user.getPassword()));
        return repo.save(user);
    }

    public List<User> getUsers(){
        return repo.findAll();
    }



    public Map<String, String> verify(User user) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );

            // If authentication succeeds, generate JWT
            String token = jwt.generateToken(user.getEmail());
            return Map.of("accessToken", token);

        } catch (BadCredentialsException ex) {
            // Throw exception to be handled by GlobalExceptionsHandler
            throw new BadCredentialsException("Invalid email or password");
        }
    }

    public Map<String, String> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Date expirationTime = jwt.getTokenExpiration(token);
            tokenBlacklistService.blacklistToken(token, expirationTime);
        }
        
        return Map.of("message", "Logged out successfully");
    }

}
