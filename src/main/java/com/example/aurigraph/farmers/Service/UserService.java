package com.example.aurigraph.farmers.Service;

import com.example.aurigraph.farmers.Domain.User;
import com.example.aurigraph.farmers.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface UserService {

    List<User> allUsers();
}