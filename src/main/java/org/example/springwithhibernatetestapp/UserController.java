package org.example.springwithhibernatetestapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/user")
    public String getUser() {
        return "Hello World!";
    }

    @PostMapping("/user")
    public User createUser() {
        User u = new User("Ajay");
    }
}
