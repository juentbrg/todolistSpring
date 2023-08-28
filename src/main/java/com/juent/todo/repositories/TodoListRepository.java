package com.juent.todo.repositories;

import org.springframework.data.repository.CrudRepository;
import com.juent.todo.models.TodoList;

public interface TodoListRepository extends CrudRepository<TodoList, Long> {

}