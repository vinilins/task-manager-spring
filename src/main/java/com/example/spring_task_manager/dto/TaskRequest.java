package com.example.spring_task_manager.dto;

public class TaskRequest {
    private String title;
    private boolean completed;

    public TaskRequest(String title, boolean completed) {
        this.title = title;
        this.completed = completed;
    }

    public String getTitle() { return title; }
    public boolean isCompleted() { return completed; }
}
