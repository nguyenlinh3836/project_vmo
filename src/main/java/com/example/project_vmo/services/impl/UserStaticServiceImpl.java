package com.example.project_vmo.services.impl;

import com.example.project_vmo.models.entities.Account;
import com.example.project_vmo.models.entities.UserStatic;
import com.example.project_vmo.repositories.AccountRepo;
import com.example.project_vmo.repositories.UserStaticRepo;
import com.example.project_vmo.services.UserStaticService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserStaticServiceImpl implements UserStaticService {

  @Autowired
  private UserStaticRepo userStaticRepo;
  @Autowired
  private AccountRepo accountRepo;

  @Override
  public void totalStatic(String usernameOrEmail) {
    Optional<Account> account = accountRepo.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
    if (account.isPresent()) {
      UserStatic userStatic = userStaticRepo.findByAccount(account.get());
      if (userStatic == null) {
        UserStatic userStaticNew = new UserStatic();
        userStaticNew.setAccount(account.get());
        userStaticNew.setCount(1);
        userStaticRepo.save(userStaticNew);
      } else {
        UserStatic userStaticOld = userStaticRepo.findByAccount(account.get());
        userStaticOld.setCount(userStatic.getCount() + 1);
        userStaticRepo.save(userStaticOld);
      }
    }
  }
}
