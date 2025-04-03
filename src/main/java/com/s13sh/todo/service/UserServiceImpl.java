package com.s13sh.todo.service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import com.s13sh.todo.entity.Session;
import com.s13sh.todo.repository.SessionRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.s13sh.todo.dto.UserRequest;
import com.s13sh.todo.entity.User;
import com.s13sh.todo.exception.UserExistsException;
import com.s13sh.todo.repository.UserRepository;

import static com.s13sh.todo.entity.SessionStatus.*;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	SessionRepository sessionRepository;

	@Override
	public Map<String, String> registerUser(UserRequest request) {
		if (userRepository.existsByEmail(request.getEmail()))
			throw new UserExistsException("Email Already Exists");
		if (userRepository.existsByUsername(request.getUsername()))
			throw new UserExistsException("Username Already Exists");
		
		User user = new User(request);
		userRepository.save(user);
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("message", "User Registered Successfully");
		return map;
	}

	@Override
	public Map<String, Object> loginUser(UserRequest request, HttpSession sessions) {
		User user = userRepository.findByUsername(request.getUsername())
				.orElseThrow(() -> new InvalidException("Invalid username"));

		Session session = new Session();
		session.setSession_id(String.valueOf(sessions));
		System.out.println("Varun@123");
		System.out.println(userRepository.findByUsername(request.getUsername()));
		session.setUser(user.get());
		session.setStatus(active);
		sessionRepository.save(session);
		return Map.of("message",sessions);
	}




	@Override
	public Map<String, String> logoutUser(UserRequest request, String sessionId) {

//		sessionRepository.deleteBySession_id(sessionId);


		return Map.of("message","Logged out successfully");
	}

	

}
