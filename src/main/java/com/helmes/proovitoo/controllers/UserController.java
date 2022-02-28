package com.helmes.proovitoo.controllers;

import com.helmes.proovitoo.dao.UserDao;
import com.helmes.proovitoo.model.User;
import com.helmes.proovitoo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@Transactional
@CrossOrigin(originPatterns = "*", allowedHeaders = "*")
public class UserController implements UserControllerInterface {
    UserDao dao = new UserDao();

    @Autowired
    UserService userService;

    @Override
    @PostMapping("/api/register")
    public ResponseEntity<Boolean> addUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

}