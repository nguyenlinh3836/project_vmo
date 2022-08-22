package com.example.project_vmo.repositories;

import com.example.project_vmo.models.entities.Account;
import com.example.project_vmo.models.request.AccountDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepo extends JpaRepository<Account, Integer> {
  Optional<Account>findByUsernameOrEmail(String name,String email);

  Account getAccountByUsername(String name);

  Account findByEmail(String email);

  Account findByResetPasswordToken(String token);

  Account findByAccountId(int id);

  @Query("select u from Account u join Role r where r.roleName = :roleName")
  List<Account> findAllByRoles(@Param("roleName") String roleName);


}