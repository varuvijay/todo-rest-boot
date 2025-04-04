package com.s13sh.todo.controller;

import com.s13sh.todo.dto.TaskRequest;
import com.s13sh.todo.dto.UserRequest;
import com.s13sh.todo.repository.TaskRepository;
import com.s13sh.todo.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    TaskService taskService;

    @PostMapping("/")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody TaskRequest taskRequest,@RequestHeader("X-Session-ID") String sessionId) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(taskService.createTask(taskRequest, sessionId));
    }

}
