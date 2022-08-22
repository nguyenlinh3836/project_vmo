package com.example.project_vmo.controllers;

import com.example.project_vmo.models.request.AccountDto;
import com.example.project_vmo.models.request.LoginDto;
import com.example.project_vmo.models.response.JWTAuthResponse;
import com.example.project_vmo.security.jwt.JwtUtils;
import com.example.project_vmo.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/api/user")
public class UserController {

  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private AccountService accountService;

  @Autowired
  private AuthenticationManager authenticationManager;

  @PostMapping
  public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginDto.getUsernameOrEmail(), loginDto.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    String token = jwtUtils.generateJwtToken(authentication);

    return ResponseEntity.ok(new JWTAuthResponse(token));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateUser(@RequestBody AccountDto accountDto, @PathVariable int id) {
    return ResponseEntity.status(HttpStatus.ACCEPTED)
        .body(accountService.updateAccount(id, accountDto));
  }



}
