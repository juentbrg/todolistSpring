package com.juent.todo.services;

import com.juent.todo.models.User;
import com.juent.todo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import javax.transaction.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    
    @Autowired
    private PasswordService passwordService;
    
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
    	user.setPassword(passwordService.hashPassword(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    public Optional<User> getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.getTodoLists().size();  
        }
        return Optional.ofNullable(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public boolean isUsernameTaken(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public boolean isEmailTaken(String email) {
        return userRepository.findByEmail(email).isPresent();
    } 
    
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

}
