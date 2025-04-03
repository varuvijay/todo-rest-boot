package com.s13sh.todo.service;

import java.util.Map;

import com.s13sh.todo.dto.UserRequest;
import jakarta.servlet.http.HttpSession;

public interface UserService {
	Map<String, String> registerUser(UserRequest request);

	Map<String, Object> loginUser(UserRequest request, HttpSession sessions);

    Map<String, String> logoutUser(UserRequest request, String sessionId);

   
}
