package org.sportybet.formulaonebetting.services.impl;

import org.sportybet.formulaonebetting.entities.User;
import org.sportybet.formulaonebetting.model.CustomUserDetails;
import org.sportybet.formulaonebetting.model.request.AuthRequest;
import org.sportybet.formulaonebetting.model.response.UserDetailsResponse;
import org.sportybet.formulaonebetting.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new CustomUserDetails(user);
    }

    public String signup(AuthRequest request) {
        if (userRepository.existsById(request.getUserId())) {
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setUserId(request.getUserId());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(new ArrayList<>(List.of("USER")));
        user.setBalance(BigDecimal.valueOf(100.00));

        userRepository.save(user);
        return "User registered successfully";
    }


    public UserDetailsResponse getUserDetails(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return UserDetailsResponse.builder()
                .userId(user.getUserId())
                .balance(user.getBalance())
                .build();
    }
}
