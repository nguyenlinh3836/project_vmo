package com.example.project_vmo.models.request;

import java.util.Date;
import java.util.List;
import javax.persistence.Id;
import lombok.Data;

@Data
public class UpdateAccountDto {
  @Id
  private int accountId;
  private String username;
  private String email;
  private Date create_at;
  private Date updated_at;
}
