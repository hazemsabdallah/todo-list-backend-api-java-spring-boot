package com.example.todolist.service;

import com.example.todolist.exception.ResourceNotFoundException;
import com.example.todolist.model.Task;
import com.example.todolist.model.User;
import com.example.todolist.repository.TaskRepository;
import com.example.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final UserRepository userRepository;

    private final UserService userService;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public Task findById(Integer taskId, Integer userId) throws ResourceNotFoundException {
        Optional<Task> result = taskRepository.findByTaskIdAndUserUserId(taskId, userId);
        return result.orElseThrow(() -> new ResourceNotFoundException(
                "There is no task with id " + taskId + " for a user with id " + userId +  "!"));
    }

    @Override
    public List<Task> findAll(Integer userId) throws ResourceNotFoundException {
        User user = userService.findById(userId);
        return user.getTaskList();
    }

    @Override
    public Task create(Task task, Integer userId) throws ResourceNotFoundException {
        User user = userService.findById(userId);
        task.setTaskId(0);
        task.setUser(user);
        return taskRepository.save(task);
    }

    @Override
    public Task update(Task task, Integer userId) throws  ResourceNotFoundException {
        Task oldTask = this.findById(task.getTaskId(), userId);
        task.setUser(oldTask.getUser());
        return taskRepository.save(task);
    }

    @Override
    public void delete(Integer taskId, Integer userId) throws  ResourceNotFoundException {
        Task task = this.findById(taskId, userId);
        taskRepository.delete(task);
    }

    @Override
    public void clear(Integer userId) throws ResourceNotFoundException {
        User user = userService.findById(userId);
        user.getTaskList().clear();
        userRepository.save(user);
    }
}