package com.s13sh.todo.service;

import com.s13sh.todo.dto.TaskRequest;
import com.s13sh.todo.entity.SessionStatus;
import com.s13sh.todo.entity.Task;
import com.s13sh.todo.exception.InvalidException;
import com.s13sh.todo.repository.SessionRepository;
import com.s13sh.todo.repository.TaskRepository;
import com.s13sh.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Map<String, Object> createTask(TaskRequest taskRequest, String sessionId) {
        return sessionRepository.findBySessionId(sessionId)
                .filter(session -> session.getStatus().equals(SessionStatus.active))
                .map(session -> {
                    Task newTask = taskRepository.save(new Task(taskRequest, session.getUserId()));

                    Map<String, Object> response = new LinkedHashMap<>();
                    response.put("id", newTask.getId());
                    response.put("name", newTask.getName());
                    response.put("description", newTask.getDescription());
                    response.put("createdAt", newTask.getCreatedAt());

                    return response;
                })
                .orElseThrow(() -> new InvalidException("Invalid session id or session not found"));
    }









}
