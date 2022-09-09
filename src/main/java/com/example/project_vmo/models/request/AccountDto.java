package com.example.project_vmo.models.request;


import com.example.project_vmo.commons.exception.PasswordValueMatch;
import com.example.project_vmo.commons.exception.ValidPassword;
import com.sun.istack.NotNull;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
  @NotNull
  private String username;
  @NotNull
  @Email
  private String email;
  @ValidPassword
  private String password;
  private List<RoleDto> roles;
  private String fullName;
  private int age;
  private Date create_at;
}
