package com.example.todolist.controller;

import com.example.todolist.exception.ResourceNotFoundException;
import com.example.todolist.model.Task;
import com.example.todolist.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // endpoint for reading all task a given user id
    // accessible by admin and each user for their id
    @GetMapping("/users/{userId}/tasks")
    public ResponseEntity<List<Task>> readTasks(@PathVariable Integer userId) throws ResourceNotFoundException {
        List<Task> tasks = taskService.findAll(userId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    // endpoint for creating a task for a given user id
    // accessible by admin and each user for their id
    @PostMapping("/users/{userId}/tasks")
    public ResponseEntity<Task> CreateTask(@PathVariable Integer userId, @Valid @RequestBody Task task)
            throws ResourceNotFoundException {
        return new ResponseEntity<>(taskService.create(task, userId), HttpStatus.CREATED);
    }

    // endpoint for updating a task for a given user id
    // accessible by admin and each user for their id
    @PutMapping("/users/{userId}/tasks/{taskId}")
    public ResponseEntity<Task> updateTask(@Valid @RequestBody Task task,
                                           @PathVariable Integer userId,
                                           @PathVariable Integer taskId)
            throws  ResourceNotFoundException {
        task.setTaskId(taskId);
        return new ResponseEntity<>(taskService.update(task, userId), HttpStatus.OK);
    }

    // endpoint for deleting a task by id for a given user id
    // accessible by admin and each user for their id
    @DeleteMapping("/users/{userId}/tasks/{taskId}")
    public ResponseEntity<String> deleteTask(
            @PathVariable Integer userId, @PathVariable Integer taskId)
            throws  ResourceNotFoundException {
        taskService.delete(taskId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // endpoint for clearing all tasks given a user id
    // accessible by admin and each user for their id
    @DeleteMapping("/users/{userId}/tasks")
    public ResponseEntity<String> clearTasks(@PathVariable Integer userId) throws ResourceNotFoundException {
        taskService.clear(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}