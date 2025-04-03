package com.s13sh.todo.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
@Entity
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "session_id", unique = true, nullable = false)
    String session_id;
    @OneToOne
    @JoinColumn(name = "user_id" )
    private User user;
    @CreationTimestamp
    private LocalDateTime created_at;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SessionStatus status;

    public Session(){

    }hunhu

}
