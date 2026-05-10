package com.example.spring_task_manager.dto;

public class TaskCreateRequest {
    private String title;
    private boolean completed;

    public TaskCreateRequest(String title, boolean completed) {
        this.title = title;
        this.completed = completed;
    }

    public String getTitle() { return title; }
    public boolean isCompleted() { return completed; }
}
