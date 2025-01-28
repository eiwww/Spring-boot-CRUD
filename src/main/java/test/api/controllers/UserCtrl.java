package test.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import test.api.entities.User;
import test.api.models.UserRequest;
import test.api.services.UserService;

@RestController
@RequestMapping("/users")
public class UserCtrl {
    
    @Autowired
    private UserService userService;

    @PostMapping("")
    public User createUser(UserRequest userRequest) {
        return userService.createUser(userRequest);
    }
}
