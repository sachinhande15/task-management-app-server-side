package com.learningwithsachin.taskmanagement.services;

import com.learningwithsachin.taskmanagement.model.CustomUserDetails;
import com.learningwithsachin.taskmanagement.model.User;
import com.learningwithsachin.taskmanagement.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByusername(username).orElseThrow(() -> new UsernameNotFoundException(
                "User not found with username " + username
        ));
        return new CustomUserDetails(user);
    }
}