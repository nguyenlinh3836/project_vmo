package com.example.project_vmo.services;

import com.example.project_vmo.models.request.AccountDto;
import com.example.project_vmo.models.request.UpdateAccountDto;
import com.example.project_vmo.models.response.RoleListResponse;
import java.util.List;
import javax.security.auth.login.AccountNotFoundException;

public interface AccountService {

  List<AccountDto> getAll();

  AccountDto createAccount(AccountDto accountDto);

  AccountDto updateAccount(int id, AccountDto accountDto);

  void deleteAccount(int id);

  RoleListResponse getAccountByRole(String name, int pageNo,int pageSize);

  void updateResetPasswordToken(String token, String email) throws AccountNotFoundException;

  AccountDto getByResetPasswordToken(String token);

  void updatePassword(AccountDto accountDto, String newPassword);

  UpdateAccountDto updateAccount(UpdateAccountDto accountDto,int id);
}
