package com.example.project_vmo.repositories;

import com.example.project_vmo.models.entities.Account;
import com.example.project_vmo.models.entities.UserStatic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStaticRepo extends JpaRepository<UserStatic,Integer> {
UserStatic findByAccount(Account account);
}
