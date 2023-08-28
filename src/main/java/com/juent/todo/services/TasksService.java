package com.juent.todo.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.juent.todo.models.Tasks;
import com.juent.todo.models.TodoList;
import com.juent.todo.repositories.TasksRepository;
import com.juent.todo.repositories.TodoListRepository;
import java.util.List;
import java.util.Optional;

@Service
public class TasksService {
	
	private final TasksRepository tasksRepository;
	private final TodoListRepository todoListRepository;
	
	@Autowired
	public TasksService(TasksRepository tasksRepository, TodoListRepository todoListRepository) {
		this.tasksRepository = tasksRepository;
		this.todoListRepository = todoListRepository;
	}
	
	public List<Tasks> findAll() {
		return (List<Tasks>) tasksRepository.findAll();
	}
	
	public Optional<Tasks> findById(Long id) {
		return tasksRepository.findById(id);
	}
	
	@Transactional
	public Tasks save(Tasks tasks) {
        if (tasks.getTodoList() == null || !todoListRepository.existsById(tasks.getTodoList().getId())) {
            throw new IllegalArgumentException("Tasks must have an associated existing TodoList.");
        }
        return tasksRepository.save(tasks);
    }
	
	@Transactional
	public void deleteById(Long taskId) {
	    Optional<Tasks> optionalTask = tasksRepository.findById(taskId);
	    if (!optionalTask.isPresent()) {
	        throw new IllegalArgumentException("Task not found.");
	    }

	    Tasks task = optionalTask.get();
	    
	    TodoList todoList = task.getTodoList();

	    if (todoList != null) {
	        todoList.getTodos().remove(task);
	    }

	    tasksRepository.delete(task);
	}

	
	@Transactional
	public Tasks markAsDone(Long taskId) {
	    Optional<Tasks> optionalTask = tasksRepository.findById(taskId);
	    if (!optionalTask.isPresent()) {
	        throw new IllegalArgumentException("Task not found.");
	    }
	    Tasks task = optionalTask.get();
	    task.setIsDone(true);
	    return tasksRepository.save(task);
	}
}
