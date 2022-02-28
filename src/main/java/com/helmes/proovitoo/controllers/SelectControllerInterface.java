package com.helmes.proovitoo.controllers;

import com.helmes.proovitoo.model.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.validation.Valid;
import java.security.Principal;

public interface SelectControllerInterface {
    @PostMapping("/api/update")
    ResponseEntity<Data> updateData(@Valid @RequestBody Data inputData, Principal principal);

    @GetMapping("/api/data")
    ResponseEntity<String> getData(Principal principal);

    @GetMapping("/api/tree")
    ResponseEntity<String> getTreeData();
}
