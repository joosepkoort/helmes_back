package com.helmes.proovitoo.controllers;

import com.helmes.proovitoo.model.Data;
import com.helmes.proovitoo.services.SelectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.security.Principal;

@RestController
@Validated
@CrossOrigin(originPatterns = "*", allowedHeaders = "*")
@Transactional
public class SelectController implements SelectControllerInterface {

    @Autowired
    SelectService selectService;

    @Override
    @PostMapping("/api/update")
    @Validated
    public ResponseEntity<Data> updateData(@Valid @RequestBody Data inputData, Principal principal) {
        return ResponseEntity.ok(selectService.updateUserData(inputData, principal));
    }

    @Override
    @GetMapping("/api/data")
    public ResponseEntity<String> getData(Principal principal) {
        return ResponseEntity.ok(selectService.loadDataForUser(principal));
    }

    @Override
    @GetMapping("/api/tree")
    public ResponseEntity<String> getTreeData() {
        return ResponseEntity.ok(selectService.loadTree());

    }
}
