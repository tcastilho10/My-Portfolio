package com.example.jarvisbackend.repository;

import com.example.jarvisbackend.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
