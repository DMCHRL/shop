package com.shop.user.controller;

import com.shop.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("check/{value}/{type}")
    public ResponseEntity<Boolean> check(@PathVariable(value = "value") String value,
                                         @PathVariable(value = "type") Integer type){
        return ResponseEntity.ok(userService.check(value,type));
    }
}
