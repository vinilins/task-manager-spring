package com.example.spring_task_manager.controller;

import com.example.spring_task_manager.dto.TaskCreateRequest;
import com.example.spring_task_manager.dto.TaskUpdateRequest;
import com.example.spring_task_manager.exception.GlobalExceptionHandler;
import com.example.spring_task_manager.exception.TaskNotFoundException;
import com.example.spring_task_manager.model.Task;
import com.example.spring_task_manager.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({TaskController.class, GlobalExceptionHandler.class})
public class TaskControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    TaskService service;

    @Test
    void getTasksReturnsEmptyListsIfNotHasTasks() throws Exception {
        when(service.getAll()).thenReturn(List.of());

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

    }

    @Test
    void getTasksReturnsTaskLists() throws Exception {
        var tasks = List.of(
                new Task(1L, "Aprender Spring", false),
                new Task(2L, "Escrever testes", true)
        );

        when(service.getAll()).thenReturn(tasks);

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Aprender Spring"))
                .andExpect(jsonPath("$[0].completed").value(false))
                .andExpect(jsonPath("$[1].title").value("Escrever testes"))
                .andExpect(jsonPath("$[1].completed").value(true));

    }

    @Test
    void createTasksSuccesAndReturnsOwnTask() throws Exception {
        String title = "Aprender Spring";
        boolean completed = false;

        var task = new Task(1L, title, completed);
        var fakeRequest = new TaskCreateRequest(title, completed);

        when(service.create(any(TaskCreateRequest.class))).thenReturn(task);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fakeRequest))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Aprender Spring"))
                .andExpect(jsonPath("$.completed").value(false));

    }

    @Test
    void editTaskSuccessAndReturnsOwnTask() throws Exception {
        var task = new Task(1L, "Aprender Inglês", false);

        var fakeRequest = Map.of("title", "Aprender Inglês");

        when(service.editById(any(Long.class), any(TaskUpdateRequest.class))).thenReturn(task);

        mockMvc.perform(patch("/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fakeRequest))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Aprender Inglês"))
            .andExpect(jsonPath("$.completed").value(false));

    }

    @Test
    void editTaskThatNotExistsReturns404() throws Exception {
        var fakeRequest = Map.of("title", "Aprender Inglês");

        doThrow(new TaskNotFoundException(99L)).when(service).editById(
                any(Long.class), any(TaskUpdateRequest.class)
        );

        mockMvc.perform(patch("/tasks/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fakeRequest))
        )
        .andExpect(status().isNotFound());
    }

    @Test
    void deleteTaskSuccessAndReturns204() throws Exception {
        // service.delete doesn't raises anyone exception, so anyone mock is needed

        mockMvc.perform(delete("/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isNoContent());
    }

    @Test
    void deleteTaskThatNotExistsReturns404() throws Exception {
        doThrow(new TaskNotFoundException(99L)).when(service).delete(any(Long.class));

        mockMvc.perform(delete("/tasks/99")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }
}
