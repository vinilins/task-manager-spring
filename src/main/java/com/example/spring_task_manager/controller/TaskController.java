package com.example.spring_task_manager.controller;

import com.example.spring_task_manager.dto.TaskRequest;
import com.example.spring_task_manager.dto.TaskUpdateRequest;
import com.example.spring_task_manager.model.Task;
import com.example.spring_task_manager.repository.TaskRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskRepository repository;

    public TaskController(TaskRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Task> listTasks() {
        return repository.getAllTasks();
    }

    @PostMapping
    public ResponseEntity<Task> createTasks(@RequestBody TaskRequest request) {
        String requestTitle = request.getTitle();
        boolean requestCompleted = request.isCompleted();

        Task newTask = repository.createNewTask(requestTitle, requestCompleted);

        return ResponseEntity.status(201).body(newTask);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Object> editTasks(@PathVariable Long id, @RequestBody TaskUpdateRequest request) {
        Task task_finded = repository.findTaskById(id);

        if (task_finded == null) {
            return ResponseEntity.status(404).build();
        }
        if (request.getTitle() != null) {
            task_finded.setTitle(request.getTitle());
        }
        if (request.isCompleted() != null) {
            task_finded.setCompleted(request.isCompleted());
        }

        return ResponseEntity.status(200).body(task_finded);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteTasks(@PathVariable Long id) {
        var deleted = repository.delete(id);

        if (deleted) return ResponseEntity.status(204).build();
        else return ResponseEntity.status(404).build();
    }

}
