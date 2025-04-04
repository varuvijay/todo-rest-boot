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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public Object getALlTask(String sessionId) {
        return sessionRepository.findBySessionId(sessionId)
                .filter(session -> session.getStatus().equals(SessionStatus.active))
                .map(session -> {
                    List<Task> newTask = (List<Task>) taskRepository.findAllByUserId(session.getUserId());
                    return newTask;
                })
                .orElseThrow(() -> new InvalidException("Invalid session id"));

    }


}












//
//
//
//
//
//
//    Great question! This is using **Java Streams** to **convert a list of `Task` objects into a list of `TaskResponse` objects**. Let’s break it down step by step! 🚀
//
//            ---
//
//            ### **🔍 The Full Line**
//            ```java
//tasks.stream()
//        .map(TaskResponse::new)
//    .collect(Collectors.toList());
//```
//
//        ### **🛠 Step-by-Step Breakdown**
//            | **Part** | **What It Does** |
//            |---------|-----------------|
//            | `tasks.stream()` | Converts the `List<Task>` into a **stream** (a pipeline of operations). |
//            | `.map(TaskResponse::new)` | **Transforms** each `Task` into a `TaskResponse`. |
//            | `.collect(Collectors.toList())` | Converts the **stream** back into a `List<TaskResponse>`. |
//
//            ---
//
//            ## **📌 Understanding Each Part in Detail**
//
//            ### **1️⃣ `tasks.stream()` - What is a Stream?**
//            - A **Stream** in Java is like a pipeline where you can process a collection **step by step**.
//            - Instead of using `for` loops, streams allow you to **transform, filter, and collect** data in a clean way.
//
//### **2️⃣ `.map(TaskResponse::new)` - What is `map()` Doing?**
//            - `map()` is **transforming** each `Task` object into a `TaskResponse` object.
//- `TaskResponse::new` is **a constructor reference**, which is the same as:
//            ```java
//            .map(task -> new TaskResponse(task))
//            ```
//    Meaning, for each `task` in the list, it's calling:
//            ```java
//  new TaskResponse(task);
//  ```
//    This assumes your `TaskResponse` class has a **constructor** like:
//            ```java
//    public class TaskResponse {
//        private Long id;
//        private String name;
//        private String description;
//        private LocalDateTime createdAt;
//
//        public TaskResponse(Task task) {
//            this.id = task.getId();
//            this.name = task.getName();
//            this.description = task.getDescription();
//            this.createdAt = task.getCreatedAt();
//        }
//    }
//  ```
//
//          ### **3️⃣ `.collect(Collectors.toList())` - What is `collect()` Doing?**
//            - Since `.map()` creates a **stream**, we need to turn it **back into a List**.
//            - `Collectors.toList()` gathers all `TaskResponse` objects into a **List<TaskResponse>`.
//            - Without `collect()`, the result would stay inside the stream.
//
//---
//
//        ## **💡 Full Example**
//    Imagine you have a **list of tasks** and want to transform them into `TaskResponse`:
//
//            ```java
//    List<Task> tasks = List.of(
//            new Task(1L, "Task 1", "Description 1", LocalDateTime.now()),
//            new Task(2L, "Task 2", "Description 2", LocalDateTime.now())
//    );
//
//    List<TaskResponse> taskResponses = tasks.stream()
//            .map(TaskResponse::new)
//            .collect(Collectors.toList());
//
//System.out.println(taskResponses);  // List<TaskResponse>
//```
//
//        ---
//
//        ## **✨ TL;DR - What This Code Does**
//            ```java
//tasks.stream()
//        .map(TaskResponse::new)
//    .collect(Collectors.toList());
//```
//        ✔ **Converts** `List<Task>` → `List<TaskResponse>`
//            ✔ Uses **Streams** for a clean transformation
//✔ **Avoids for-loops**, making code shorter and more readable
//
//---
//
//        ## **🤔 Alternative (Without Streams)**
//    If you **don’t** use streams, you would do:
//            ```java
//    List<TaskResponse> taskResponses = new ArrayList<>();
//for (Task task : tasks) {
//        taskResponses.add(new TaskResponse(task));
//    }
//return taskResponses;
//```
//        💡 **Both work, but streams are cleaner!** 🚀
//
//            ---
//
//            ## **🔎 Want More Practice?**
//    Want some **small stream exercises** to try? Just ask! 😊