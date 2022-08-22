package com.example.project_vmo.controllers;

import com.example.project_vmo.models.entities.Role;
import com.example.project_vmo.models.request.AccountDto;
import com.example.project_vmo.services.AccountService;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
  @Autowired
  private AccountService accountService;

  @PostMapping
  public ResponseEntity<?> createAccount(@RequestBody AccountDto accountDto){
    return ResponseEntity.ok(accountService.createAccount(accountDto));
  }

}
