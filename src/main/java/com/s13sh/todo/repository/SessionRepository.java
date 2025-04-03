package com.s13sh.todo.repository;

import com.s13sh.todo.entity.Session;
import com.s13sh.todo.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session,Long> {




}
