package com.hammad.task.controllers;

import com.hammad.task.domain.entities.User;
import com.hammad.task.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/auth/register")
    public User register(@RequestBody User user){
        return userService.registerUser(user);

    }

    @PostMapping("/auth/login")
    public Map<String, String> login(@RequestBody User user){
        return userService.verify(user);
    }

    @GetMapping("/users")
    public List<User> checkUsers(){
        return userService.getUsers();
    }
}
