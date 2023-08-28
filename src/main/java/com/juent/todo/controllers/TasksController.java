package com.juent.todo.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.juent.todo.models.Tasks;
import com.juent.todo.services.TasksService;
import com.juent.todo.utils.ApiResponse;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TasksController {

    private final TasksService tasksService;

    @Autowired
    public TasksController(TasksService tasksService) {
        this.tasksService = tasksService;
    }

    @GetMapping
    public List<Tasks> getAllTasks() {
        return tasksService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Tasks>> getTasksById(@PathVariable Long id) {
        Optional<Tasks> optionalTask = tasksService.findById(id);
        if (optionalTask.isPresent()) {
            return ResponseEntity.ok(new ApiResponse<>(true, optionalTask.get(), "Task found."));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, null, "Task not found."));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Tasks>> createTask(@RequestBody Tasks tasks) {
        Tasks createdTask = tasksService.save(tasks);
        return ResponseEntity.ok(new ApiResponse<>(true, createdTask, "Task created successfully."));
    }

    @PutMapping("/{id}/markAsDone")
    public ResponseEntity<ApiResponse<Tasks>> markTaskAsDone(@PathVariable Long id) {
        Tasks updatedTask = tasksService.markAsDone(id);
        return ResponseEntity.ok(new ApiResponse<>(true, updatedTask, "Task marked as done successfully."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTasks(@PathVariable Long id) {
        tasksService.deleteById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, null, "Task deleted successfully."));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<String>> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(new ApiResponse<>(false, null, e.getMessage()));
    }
}