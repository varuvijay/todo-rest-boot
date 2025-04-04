package com.s13sh.todo.entity;

import com.s13sh.todo.dto.TaskRequest;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private java.lang.Long id;

    private Long userId;

    @Column(nullable = false)
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status = TaskStatus.Pending;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Task(TaskRequest taskRequest, java.lang.Long id) {
        this.name = taskRequest.getName();
        this.description=taskRequest.getDescription();
        this.status=getStatus();
        this.userId=id;
    }


    public Task(Task task) {
    }
}
