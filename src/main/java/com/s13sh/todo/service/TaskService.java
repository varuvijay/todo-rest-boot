package com.s13sh.todo.service;

import com.s13sh.todo.dto.TaskRequest;

import java.util.Map;

public interface TaskService {
    Map<String, Object> createTask(TaskRequest taskRequest, String sessionId);
}
