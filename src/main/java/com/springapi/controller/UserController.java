package com.springapi.controller;

import com.springapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/user")
    public ResponseEntity getUser(Integer id){
        return ResponseEntity.ok().body(userService.findUsersById(id));
    }
}
