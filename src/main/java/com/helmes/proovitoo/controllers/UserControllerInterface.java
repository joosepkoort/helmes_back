package com.helmes.proovitoo.controllers;

import com.helmes.proovitoo.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserControllerInterface {

    @PostMapping("/api/register")
    ResponseEntity<Boolean> addUser(@RequestBody User user);
}
