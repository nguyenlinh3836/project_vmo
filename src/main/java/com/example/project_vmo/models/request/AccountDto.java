package com.example.project_vmo.models.request;


import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

  @Id
  private int accountId;
  private String username;
  private String email;
  private String password;
  private List<RoleDto> roles;
  private Date create_at;
  private Date updated_at;
}
