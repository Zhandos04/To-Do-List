package com.example.Zhandos.Controllers;

import com.example.Zhandos.Services.TaskService;
import com.example.Zhandos.Validation.DateValidator;
import com.example.Zhandos.models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class TaskController {

    private final TaskService taskService;
    private DateValidator dateValidator = new DateValidator();

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Endpoint для создания новой задачи
    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        LocalDate dueDate = task.getDueDate();

        // Проверка: если дата завершения задачи попадает на выходной или праздник
        if (dateValidator.isHolidayOrWeekend(dueDate)) {
            LocalDate nextAvailableDate = dateValidator.getNextAvailableDate(dueDate);
            String errorMessage = "Выберите другой день — ближайший доступный: " + nextAvailableDate;
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        taskService.createTask(task);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Endpoint для получения всех задач
    @GetMapping("/tasks")
    public ResponseEntity<Page<Task>> getAllTasks(
            @RequestParam(required = false) String status,      // Фильтрация по статусу
            @RequestParam(defaultValue = "0") int page,         // Номер страницы
            @RequestParam(defaultValue = "2") int size         // Размер страницы
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Task> tasks = taskService.getTasks(status, pageable);
        return ResponseEntity.ok(tasks);
    }


    @PostMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Integer id, @RequestBody Task updatedTask) {
        Optional<Task> existingTaskOptional = taskService.getTaskById(id);

        // Если задача с данным ID не найдена
        if (existingTaskOptional.isEmpty()) {
            return new ResponseEntity<>("Задача с ID " + id + " не найдена", HttpStatus.NOT_FOUND);
        }

        // Обновляем поля задачи
        Task existingTask = existingTaskOptional.get();
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setStatus(updatedTask.getStatus());

        // Сохраняем изменения
        taskService.updateTask(existingTask);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Integer id) {
        Optional<Task> existingTaskOptional = taskService.getTaskById(id);

        // Если задача с данным ID не найдена
        if (existingTaskOptional.isEmpty()) {
            return new ResponseEntity<>("Задача с ID " + id + " не найдена", HttpStatus.NOT_FOUND);
        }
        taskService.deleteTask(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
