package com.juent.todo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TodoList> todoLists;
	
	@Column(name="first_name")
	private String firstName;	
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="username", unique=true)
	private String username;
	
	private String password;
	
	@Column(name="email", unique=true)
	private String email;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public List<TodoList> getTodoLists() {
		return todoLists;
	}
	public void setTodoLists(List<TodoList> todoLists) {
		this.todoLists = todoLists;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@JsonProperty(access = Access.WRITE_ONLY)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
	    return "User ["
	            + "id=" + id 
	            + ", firstName=" + firstName 
	            + ", lastName=" + lastName
	            + ", username=" + username 
	            + ", email=" + email 
	            + ", todoLists size=" + (todoLists != null ? todoLists.size() : "null") 
	            + "]";
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
		User other = (User) obj;
		return id == other.id;
	}	
	
}
