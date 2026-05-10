package com.example.spring_task_manager.service;

import com.example.spring_task_manager.dto.TaskCreateRequest;
import com.example.spring_task_manager.dto.TaskUpdateRequest;
import com.example.spring_task_manager.exception.TaskNotFoundException;
import com.example.spring_task_manager.model.Task;
import com.example.spring_task_manager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public List<Task> getAll() {
        return repository.getAll();
    }

    public Task create(TaskCreateRequest request) {
        var title = request.getTitle();
        var completed = request.isCompleted();

        return repository.create(title, completed);
    }

    public Task editById(Long id, TaskUpdateRequest request) {
        var title = request.getTitle();
        var completed = request.isCompleted();

        Task taskFound = repository.findById(id);

        if (taskFound == null) {
            throw new TaskNotFoundException(id);
        }
        if (title != null) {
            taskFound.setTitle(title);
        }
        if (completed != null) {
            taskFound.setCompleted(completed);
        }

        return taskFound;
    }

    public boolean delete(Long id) {
        var taskFound = repository.findById(id);

        if (taskFound == null) { throw new TaskNotFoundException(id); }

        return repository.delete(taskFound);
    }
}
