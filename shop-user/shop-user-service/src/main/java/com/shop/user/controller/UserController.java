package com.shop.user.controller;

import com.shop.user.dto.RegisterUser;
import com.shop.user.pojo.User;
import com.shop.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("check/{value}/{type}")
    public ResponseEntity<Boolean> check(@PathVariable(value = "value") String value,
                                         @PathVariable(value = "type") Integer type){
        return ResponseEntity.ok(userService.check(value,type));
    }

    @PostMapping("code")
    public ResponseEntity check(@RequestParam(value = "phone") String phone){
        userService.code(phone);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("register")
    public ResponseEntity<Void> register(RegisterUser user){
        Boolean register = userService.register(user);
        if(!register){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("queryUser")
    public ResponseEntity<User> queryUser(@RequestParam(value = "username") String username,
                                         @RequestParam(value = "password") String password){
        return ResponseEntity.ok( userService.queryUser(username,password));
    }

    @PostMapping("findByName")
    public ResponseEntity<User> findByName(@RequestParam(value = "username") String username){
        return ResponseEntity.ok( userService.findByName(username));
    }
}
