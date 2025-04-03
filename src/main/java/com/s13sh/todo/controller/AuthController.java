package com.s13sh.todo.controller;

import java.util.Map;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.s13sh.todo.dto.UserRequest;
import com.s13sh.todo.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	UserService userService;

	@PostMapping("/register")
	public ResponseEntity<Map<String, String>> registerUser(@RequestBody @Valid UserRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(request));
	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> loginUser(@RequestBody  UserRequest request, HttpSession sessions) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.loginUser(request,sessions));
	}

	@PostMapping("/logout")
	public ResponseEntity<Map<String, String>> logoutUser(@RequestBody  UserRequest request,@RequestHeader ("X-Session-ID") String sessionId ) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.logoutUser(request,sessionId));
	}



}
