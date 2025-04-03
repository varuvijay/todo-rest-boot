package com.s13sh.todo.entity;

import java.time.LocalDateTime;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import com.s13sh.todo.dto.UserRequest;
import com.s13sh.todo.helper.AES;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true, nullable = false)
	private String username;
	@Column(nullable = false)
	private String password;
	@Column(unique = true, nullable = false)
	private String email;
	@CreationTimestamp
	private LocalDateTime createdTime;

	public User() {
		
	}

	public User(UserRequest request) {
		this.email = request.getEmail();
		this.password = AES.encrypt(request.getPassword());
		this.username = request.getUsername();
	}
}
