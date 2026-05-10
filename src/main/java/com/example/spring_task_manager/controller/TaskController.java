package com.example.spring_task_manager.controller;

import com.example.spring_task_manager.dto.TaskRequest;
import com.example.spring_task_manager.model.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private Long idSequence = 1L;
    private List<Task> allTasks = new ArrayList<>(
            List.of(
                new Task(idSequence, "Aprender Spring Boot", false)
            )
    );


    @GetMapping
    public List<Task> listTasks() {
        return allTasks;
    }

    @PostMapping
    public ResponseEntity<Task> createTasks(@RequestBody TaskRequest request) {
        String requestTitle = request.getTitle();
        boolean requestCompleted = request.isCompleted();

        idSequence++;

        Task newTask = new Task(idSequence, requestTitle, requestCompleted);
        allTasks.add(newTask);

        return ResponseEntity.status(201).body(newTask);
    }

}
