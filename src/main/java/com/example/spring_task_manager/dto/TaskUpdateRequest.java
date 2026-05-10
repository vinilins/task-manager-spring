package com.example.spring_task_manager.dto;

public class TaskUpdateRequest {
    private String title;
    private Boolean completed;

    public TaskUpdateRequest() {}

    public String getTitle() { return title; }
    public Boolean isCompleted() { return completed; }
}
