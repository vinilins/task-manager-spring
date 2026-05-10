package com.example.spring_task_manager.controller;

import com.example.spring_task_manager.dto.TaskCreateRequest;
import com.example.spring_task_manager.dto.TaskUpdateRequest;
import com.example.spring_task_manager.model.Task;
import com.example.spring_task_manager.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public List<Task> listTasks() {
        return service.getAll();
    }

    @PostMapping
    public ResponseEntity<Task> createTasks(@RequestBody TaskCreateRequest request) {
        var newTask = service.create(request);

        return ResponseEntity.status(201).body(newTask);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Task> editTasks(@PathVariable Long id, @RequestBody TaskUpdateRequest request) {
        var taskEdited = service.editById(id, request);

        return ResponseEntity.ok(taskEdited);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteTasks(@PathVariable Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

}
