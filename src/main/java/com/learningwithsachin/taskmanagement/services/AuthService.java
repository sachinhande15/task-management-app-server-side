package com.learningwithsachin.taskmanagement.services;


import com.learningwithsachin.taskmanagement.dto.AuthRequest;
import com.learningwithsachin.taskmanagement.dto.AuthResponse;
import com.learningwithsachin.taskmanagement.exception.UserAlreadyExistsException;
import com.learningwithsachin.taskmanagement.model.User;
import com.learningwithsachin.taskmanagement.repository.UserRepo;
import com.learningwithsachin.taskmanagement.utilities.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final UserRepo userRepo;

    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepo userRepo, JwtUtil jwtUtil, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public boolean register(User user) {
        User foundUser = userRepo.findByusername(user.getUsername()).orElse(null);
        if (foundUser == null) {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            userRepo.save(user);
            logger.info("user saved in the database successfully");
            return true;
        }
        throw new UserAlreadyExistsException("User with username " + user.getUsername() + " " + "already exists.");
    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()
        ));
        String jwtToken = jwtUtil.generateToken(request.getUsername());
        return new AuthResponse(jwtToken);
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepo.findByusername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found for username: " + username));
    }

}
