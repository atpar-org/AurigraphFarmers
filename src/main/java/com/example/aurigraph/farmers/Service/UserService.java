package com.example.aurigraph.farmers.Service;

import com.example.aurigraph.farmers.Domain.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    List<User> allUsers();

    Optional<User> findByPhoneNumer(String phoneNumber);

    Optional<User> findById(Long userId);
}