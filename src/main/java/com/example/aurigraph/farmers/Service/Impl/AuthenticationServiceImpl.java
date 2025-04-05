package com.example.aurigraph.farmers.Service.Impl;

import com.example.aurigraph.farmers.DTO.LoginUserDTO;
import com.example.aurigraph.farmers.DTO.RegisterUserDTO;
import com.example.aurigraph.farmers.Domain.User;
import com.example.aurigraph.farmers.Repository.UserRepository;
import com.example.aurigraph.farmers.Service.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceImpl(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User signup(RegisterUserDTO input) {
        User user = new User();
        user.setFullName(input.getFullName());
        user.setEmail(input.getEmail());
        user.setPhoneNumber(input.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(input.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public User authenticate(LoginUserDTO input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getPhoneNumber(),
                        input.getPassword()
                )
        );

        return userRepository.findByPhoneNumber(input.getPhoneNumber())
                .orElseThrow();
    }
}
