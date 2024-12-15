package com.user_registration_system.user_registration_system.Service;

import com.user_registration_system.user_registration_system.Entity.User;
import com.user_registration_system.user_registration_system.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
