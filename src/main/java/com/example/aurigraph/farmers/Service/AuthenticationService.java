package com.example.aurigraph.farmers.Service;

import com.example.aurigraph.farmers.DTO.LoginUserDTO;
import com.example.aurigraph.farmers.DTO.RegisterUserDTO;
import com.example.aurigraph.farmers.Domain.User;
import com.example.aurigraph.farmers.Repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


public interface AuthenticationService {

    User signup(RegisterUserDTO input);

    User authenticate(LoginUserDTO input);
}
