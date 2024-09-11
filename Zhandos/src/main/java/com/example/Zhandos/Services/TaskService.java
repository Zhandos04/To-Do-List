package com.example.Zhandos.Services;

import com.example.Zhandos.Repositories.TaskRepository;
import com.example.Zhandos.models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Создание новой задачи
    @Transactional
    public void createTask(Task task) {
        taskRepository.save(task);
    }

    // Получение всех задач
    public Page<Task> getTasks(String status, Pageable pageable) {
        if (status != null) {
            return taskRepository.findByStatus(status, pageable);
        } else {
            return taskRepository.findAll(pageable); // Возвращаем все задачи
        }
    }

    // Получение задачи по ID
    public Optional<Task> getTaskById(Integer id) {
        return taskRepository.findById(id);
    }

    // Обновление статуса задачи
    @Transactional
    public void updateTask(Task task) {
        taskRepository.save(task);
    }
    @Transactional
    public void deleteTask(Integer id) {
        taskRepository.deleteById(id);
    }
}
