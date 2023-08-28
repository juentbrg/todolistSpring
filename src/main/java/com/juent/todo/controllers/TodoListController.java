package com.juent.todo.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.juent.todo.models.TodoList;
import com.juent.todo.services.TodoListService;
import com.juent.todo.utils.ApiResponse;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/todolists")
public class TodoListController {

    private final TodoListService todoListService;

    @Autowired
    public TodoListController(TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    @GetMapping
    public ApiResponse<List<TodoList>> getAllTodoLists() {
        return new ApiResponse<>(true, todoListService.findAll(), "Fetched all todo lists successfully.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TodoList>> getTodoListById(@PathVariable Long id) {
        Optional<TodoList> todoList = todoListService.findById(id);
        if(todoList.isPresent()) {
            return ResponseEntity.ok(new ApiResponse<>(true, todoList.get(), "Todo list found."));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TodoList>> createTodoList(@RequestBody TodoList todoList) {
        TodoList createdList = todoListService.save(todoList);
        return ResponseEntity.ok(new ApiResponse<>(true, createdList, "Todo list created successfully."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTodoList(@PathVariable Long id) {
        todoListService.deleteById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, null, "Todo list deleted successfully."));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<String>> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(new ApiResponse<>(false, null, e.getMessage()));
    }
}


