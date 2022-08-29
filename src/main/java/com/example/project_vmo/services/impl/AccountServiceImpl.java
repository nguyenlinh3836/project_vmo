package com.example.project_vmo.services.impl;

import com.example.project_vmo.commons.config.MapperUtil;
import com.example.project_vmo.models.entities.Account;
import com.example.project_vmo.models.entities.Role;
import com.example.project_vmo.models.request.AccountDto;
import com.example.project_vmo.models.request.UpdateAccountDto;
import com.example.project_vmo.models.response.RoleListResponse;
import com.example.project_vmo.repositories.AccountRepo;
import com.example.project_vmo.services.AccountService;
import java.util.List;
import java.util.stream.Collectors;
import javax.security.auth.login.AccountNotFoundException;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {


  @Autowired
  private AccountRepo accountRepo;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public List<AccountDto> getAll() {
    return accountRepo.findAll().stream().map(account -> MapperUtil.map(account, AccountDto.class))
        .collect(
            Collectors.toList());
  }

  @Override
  public AccountDto createAccount(AccountDto accountDto) {
    Account account = MapperUtil.map(accountDto, Account.class);
    List<Role> roles = MapperUtil.mapList(accountDto.getRoles(),Role.class);
    account.setRoles(roles);
    account.setPassword(passwordEncoder.encode(accountDto.getPassword()));
    return MapperUtil.map(accountRepo.save(account), AccountDto.class);
  }

  @Override
  public AccountDto updateAccount(int id, AccountDto accountDto) {
    Account account = MapperUtil.map(accountDto, Account.class);
    account.setAccountId(id);
    return MapperUtil.map(accountRepo.save(account), AccountDto.class);
  }

  @Override
  public void deleteAccount(int id) {
    Account account = MapperUtil.map(accountRepo.findByAccountId(id), Account.class);
    account.setIs_deleted(true);
    MapperUtil.map(account, AccountDto.class);
  }

  @Override
  public RoleListResponse getAccountByRole(String name,int pageNo,int pageSize) {
    Pageable pageable = PageRequest.of(pageNo,pageSize);
    Page<Account> accounts = accountRepo.findByRoles_roleName(name,pageable);
    List<AccountDto> content = accounts.getContent().stream().map(account -> MapperUtil.map(account,AccountDto.class)).collect(
        Collectors.toList());
    RoleListResponse response = new RoleListResponse();
    response.setContent(content);
    response.setPageNo(pageNo);
    response.setPageSize(pageSize);
    response.setTotalElements(accounts.getTotalElements());
    response.setTotalPages(accounts.getTotalPages());
    response.setLast(accounts.isLast());
    return  response;
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
    Account account = MapperUtil.map(accountDto, Account.class);
    account.setPassword(passwordEncoder.encode(newPassword));
    account.setResetPasswordToken(null);
    accountRepo.save(account);
  }

  @Override
  public UpdateAccountDto updateAccount(UpdateAccountDto accountDto, int id) {
    Account account = MapperUtil.map(accountDto, Account.class);
    account.setAccountId(id);
    return MapperUtil.map(accountRepo.save(account), UpdateAccountDto.class);
  }
}
