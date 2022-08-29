package com.example.project_vmo.repositories;

import com.example.project_vmo.models.entities.PasswordResetToken;
import java.time.LocalDateTime;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PasswordResetTokenRepo extends JpaRepository<PasswordResetToken,Integer> {
  PasswordResetToken findByToken(String token);
  @Transactional
  @Modifying
  @Query("UPDATE PasswordResetToken c SET c.confirmedAt = ?2 WHERE c.token = ?1")
  int updateConfirmedAt(String token, LocalDateTime localDateTime);
}
