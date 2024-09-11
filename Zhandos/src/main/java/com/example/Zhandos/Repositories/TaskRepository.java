package com.example.Zhandos.Repositories;

import com.example.Zhandos.models.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    Page<Task> findByStatus(String status, Pageable pageable);
}