package com.example.spring_task_manager.repository;

import com.example.spring_task_manager.model.Task;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TaskRepository {

    private Long idSequence = 1L;
    private List<Task> allTasks = new ArrayList<>(
            List.of(
                    new Task(idSequence, "Aprender Spring Boot", false)
            )
    );

    public List<Task> getAllTasks() {
        return allTasks;
    }

    public Task createNewTask(String title, boolean completed) {
        idSequence++;

        Task newTask = new Task(idSequence, title, completed);
        allTasks.add(newTask);

        return newTask;
    }

    public Task findTaskById(Long id) {
        for (Task task : allTasks) {
            if (task.getId().equals(id)) { return task; }
        }

        return null;
    }

    public boolean delete(Long id) {
        var findedTask = findTaskById(id);

        if (findedTask == null) { return false;}

        allTasks.remove(findedTask);
        return true;
    }
}
