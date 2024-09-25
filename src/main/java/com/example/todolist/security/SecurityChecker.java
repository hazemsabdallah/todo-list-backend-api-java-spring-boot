package com.example.todolist.security;

import com.example.todolist.model.User;
import com.example.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class SecurityChecker {

    private final UserRepository userRepository;

    @Autowired
    public SecurityChecker(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean checkUserId(Authentication authentication, String userIdString) {
        if (!(authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.User))
            return false;

        if (authentication.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ROLE_ADMIN")))
            return true;


        String userName = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByUserName(userName);

        return user.getUserId().toString().equals(userIdString);
    }
}