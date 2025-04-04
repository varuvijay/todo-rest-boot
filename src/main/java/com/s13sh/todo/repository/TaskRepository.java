package com.s13sh.todo.repository;

import com.s13sh.todo.entity.Session;
import com.s13sh.todo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task,Long> {


}
