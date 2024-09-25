package com.example.todolist.service;

import com.example.todolist.exception.ResourceNotFoundException;
import com.example.todolist.model.User;
import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Integer userId) throws ResourceNotFoundException;

    User create (User user) ;

    User update (User user) throws ResourceNotFoundException;

    void deleteById(Integer userId) throws ResourceNotFoundException;
}