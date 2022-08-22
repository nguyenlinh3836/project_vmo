package com.example.project_vmo.services;

import com.example.project_vmo.commons.config.MapperUtil;
import com.example.project_vmo.models.entities.Account;
import com.example.project_vmo.models.entities.Role;
import com.example.project_vmo.models.request.AccountDto;
import com.example.project_vmo.repositories.AccountRepo;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.security.auth.login.AccountNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

  @Autowired
  private ModelMapper mapper;
  @Autowired
  private AccountRepo accountRepo;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public List<AccountDto> getAll() {
    return accountRepo.findAll().stream().map(account -> mapper.map(account, AccountDto.class))
        .collect(
            Collectors.toList());
  }

  @Override
  public AccountDto createAccount(AccountDto accountDto) {
    Account account = mapper.map(accountDto, Account.class);
    return mapper.map(account, AccountDto.class);
  }

  @Override
  public AccountDto updateAccount(int id, AccountDto accountDto) {
    Account account = mapper.map(accountDto, Account.class);
    account.setAccountId(id);
    return mapper.map(accountRepo.save(account), AccountDto.class);
  }

  @Override
  public AccountDto deleteAccount(int id) {
    Account account = mapper.map(accountRepo.findByAccountId(id), Account.class);
    account.setIs_deleted(true);
    return mapper.map(account, AccountDto.class);
  }

  @Override
  public List<AccountDto> getAccountByRole(String name) {
    List<Account> accountDtoListByRole = accountRepo.findAllByRoles(name);
    return accountDtoListByRole.stream().map(account -> mapper.map(account, AccountDto.class))
        .collect(
            Collectors.toList());
  }

  @Override
  public void updateResetPasswordToken(String token, String email) throws AccountNotFoundException {
    Account account = accountRepo.findByEmail(email);
    if(account != null){
      account.setResetPasswordToken(token);
      accountRepo.save(account);
    } else {
      throw new AccountNotFoundException("Could not find any account with the email " + email);
    }
  }

  @Override
  public AccountDto getByResetPasswordToken(String token) {
    return MapperUtil.map(accountRepo.findByResetPasswordToken(token), AccountDto.class);
  }

  @Override
  public void updatePassword(AccountDto accountDto, String newPassword) {
    Account account = MapperUtil.map(accountDto,Account.class);
    account.setPassword(passwordEncoder.encode(newPassword));
    account.setResetPasswordToken(null);
    accountRepo.save(account);
  }
}
