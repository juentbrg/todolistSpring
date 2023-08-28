package com.juent.todo.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.juent.todo.models.TodoList;
import com.juent.todo.repositories.TodoListRepository;
import com.juent.todo.repositories.UserRepository;
import java.util.List;
import java.util.Optional;

@Service
public class TodoListService {

    private final TodoListRepository todoListRepository;
    private final UserRepository userRepository;

    @Autowired
    public TodoListService(TodoListRepository todoListRepository, UserRepository userRepository) {
        this.todoListRepository = todoListRepository;
        this.userRepository = userRepository;
    }

    public List<TodoList> findAll() {
        return (List<TodoList>) todoListRepository.findAll();
    }

    public Optional<TodoList> findById(Long id) {
        return todoListRepository.findById(id);
    }

    @Transactional
    public TodoList save(TodoList todoList) {
        if (todoList.getOwner() == null || !userRepository.existsById(todoList.getOwner().getId())) {
            throw new IllegalArgumentException("TodoList must have an associated existing user as owner.");
        }
        return todoListRepository.save(todoList);
    }

    public void deleteById(Long id) {
        todoListRepository.deleteById(id);
    }

}
