package com.example.project_vmo.controllers;

import com.example.project_vmo.models.request.AccountDto;
import com.example.project_vmo.models.request.LoginDto;
import com.example.project_vmo.models.request.UpdateAccountDto;
import com.example.project_vmo.models.request.UpdatePasswordRequest;
import com.example.project_vmo.models.response.JWTAuthResponse;
import com.example.project_vmo.security.jwt.JwtUtils;
import com.example.project_vmo.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/buyer")
public class BuyerController {

  @Autowired
  private AccountService accountService;

  @PutMapping("/{id}")
  public ResponseEntity<?> updateUser(@RequestBody UpdateAccountDto accountDto,
      @PathVariable int id) {
    return ResponseEntity.status(HttpStatus.ACCEPTED)
        .body(accountService.updateAccount(accountDto, id));
  }

  @PutMapping("/updatePassword/{id}")
  public ResponseEntity<?> updatePassword(@PathVariable("id") int id,@RequestBody
      UpdatePasswordRequest updatePasswordRequest){
    accountService.updatePassword(updatePasswordRequest,id);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body("Password has been change !");
  }




}
