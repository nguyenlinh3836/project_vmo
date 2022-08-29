package com.example.project_vmo.models.request;

import lombok.Data;

@Data
public class PasswordResetRequest {
  private String email;
  private String password;
  private String confirmPassword;
  private String token;
}
