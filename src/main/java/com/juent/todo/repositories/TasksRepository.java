package com.juent.todo.repositories;

import org.springframework.data.repository.CrudRepository;
import com.juent.todo.models.Tasks;

public interface TasksRepository extends CrudRepository<Tasks, Long> {

}