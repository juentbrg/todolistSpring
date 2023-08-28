package com.juent.todo.models;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;


@Entity
@Table(name = "todo")
public class Tasks {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    
    
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "list_id")
    private TodoList todoList;

    @Column(name = "task_name")
    private String taskName;  
    
    @Column(name = "is_done")
    private Boolean isDone;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public TodoList getTodoList() {
		return todoList;
	}
	public void setTodoList(TodoList todoList) {
		this.todoList = todoList;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public Boolean getIsDone() {
		return isDone;
	}
	public void setIsDone(Boolean isDone) {
		this.isDone = isDone;
	}
	
	@Override
	public String toString() {
		return "Tasks [id=" + id + ", todoList=" + todoList + ", taskName=" + taskName + ", isDone=" + isDone + "]";
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tasks other = (Tasks) obj;
		return Objects.equals(id, other.id);
	}
}

