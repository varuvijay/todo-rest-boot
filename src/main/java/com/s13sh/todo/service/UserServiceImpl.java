package com.s13sh.todo.service;

import com.s13sh.todo.dto.UserRequest;
import com.s13sh.todo.entity.Session;
import com.s13sh.todo.entity.SessionStatus;
import com.s13sh.todo.entity.User;
import com.s13sh.todo.exception.InvalidException;
import com.s13sh.todo.exception.UserExistsException;
import com.s13sh.todo.helper.AES;
import com.s13sh.todo.repository.SessionRepository;
import com.s13sh.todo.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public Map<String, String> registerUser(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserExistsException("Email Already Exists");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserExistsException("Username Already Exists");
        }

        userRepository.save(new User(request));

        return Map.of("message", "User Registered Successfully");
    }

    @Override
    public Map<String, Object> loginUser(UserRequest request, HttpSession session) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new InvalidException("Invalid Username"));

        if (!request.getPassword().equals(AES.decrypt(user.getPassword()))) {
            throw new InvalidException("Invalid Password");
        }

        sessionRepository.save(new Session(session.getId(), user.getId(), SessionStatus.active));

        return Map.of(
                "message", "User Login Success",
                "sessionId", session.getId()
        );
    }

    @Override
    public Map<String, String> logoutUser(UserRequest request, String sessionId) {
        sessionRepository.findBySessionId(sessionId)
                .ifPresentOrElse(session -> {
                    if (!session.getStatus().equals(SessionStatus.invalidated)) {
                        session.setStatus(SessionStatus.invalidated);
                        sessionRepository.save(session);
                    } else
                        throw new InvalidException("Session is invalidated");
                }, () -> {
                    throw new InvalidException("Session not found");
                });

        return Map.of("message", "Logged out successfully");
    }


}


















// Understanding .ifPresentOrElse() in Java Optional
//    The method .ifPresentOrElse() is a part of Java's Optional class, introduced in Java 9.
//    It allows us to handle both cases when an Optional<T>:
//
//    Contains a value → Executes a given action.
//
//    Is empty (i.e., no value is present) → Executes an alternative action.
//    .ifPresentOrElse(session -> { ... }, () -> { ... });
//    optional.ifPresentOrElse(
//    value -> {
//        // Code executes if value is present
//    },
//            () -> {
//        // Code executes if value is absent
//    }
//);
