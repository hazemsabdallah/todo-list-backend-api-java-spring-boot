package com.example.todolist.service;

import com.example.todolist.exception.ResourceNotFoundException;
import com.example.todolist.model.Task;

import java.util.List;

public interface TaskService {

    Task findById(Integer taskId,  Integer userId) throws ResourceNotFoundException;

    List<Task> findAll(Integer userId) throws ResourceNotFoundException;

    Task create(Task task, Integer userId) throws ResourceNotFoundException;

    Task update(Task task, Integer userId) throws ResourceNotFoundException;

    void delete(Integer taskId, Integer userId) throws ResourceNotFoundException;

    void clear(Integer userId) throws ResourceNotFoundException;
}