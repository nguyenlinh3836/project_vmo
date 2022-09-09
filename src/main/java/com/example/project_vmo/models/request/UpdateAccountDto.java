package com.example.project_vmo.models.request;

import java.util.Date;
import lombok.Data;

@Data
public class UpdateAccountDto {
  private String fullName;
  private String email;
  private String address;
  private Date updated_at;
}
