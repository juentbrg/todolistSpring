package com.juent.todo.repositories;

import com.juent.todo.models.User;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    
    Optional<User> findByUsername(String username);    
    Optional<User> findByEmail(String email);    
}