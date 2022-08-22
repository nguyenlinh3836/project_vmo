package com.example.project_vmo.services;

import com.example.project_vmo.models.entities.Role;
import com.example.project_vmo.models.request.AccountDto;
import java.util.List;
import java.util.Set;
import javax.security.auth.login.AccountNotFoundException;

public interface AccountService {

  List<AccountDto> getAll();

  AccountDto createAccount(AccountDto accountDto);

  AccountDto updateAccount(int id, AccountDto accountDto);

  AccountDto deleteAccount(int id);

  List<AccountDto> getAccountByRole(String name);

  void updateResetPasswordToken(String token, String email) throws AccountNotFoundException;

  AccountDto getByResetPasswordToken(String token);

  void updatePassword(AccountDto accountDto, String newPassword);
}
