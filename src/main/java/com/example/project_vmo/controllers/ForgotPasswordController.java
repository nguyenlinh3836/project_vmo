package com.example.project_vmo.controllers;

import com.example.project_vmo.models.request.PasswordResetLinkRequest;
import com.example.project_vmo.models.request.PasswordResetRequest;
import com.example.project_vmo.services.PasswordTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ForgotPasswordController {

  @Autowired
  private PasswordTokenService passwordTokenService;

  @GetMapping("/forgot-password/confirm")
  public String confirmTokenToChangePassword(@RequestParam("token") String token) {
    return passwordTokenService.confirmTokenResetPassword(token);
  }

  @PostMapping("/forgot-password")
  public ResponseEntity<?> sendTokenToChangePassword(
      @RequestBody PasswordResetLinkRequest request) {
    return ResponseEntity.ok(passwordTokenService.sendTokenToChangePassword(request));
  }

  @PostMapping("/reset-password")
  public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest request) {
    return ResponseEntity.ok(passwordTokenService.changePassword(request));
  }

}
