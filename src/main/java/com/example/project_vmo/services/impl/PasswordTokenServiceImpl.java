package com.example.project_vmo.services.impl;

import com.example.project_vmo.models.entities.Account;
import com.example.project_vmo.models.entities.PasswordResetToken;
import com.example.project_vmo.models.request.PasswordResetLinkRequest;
import com.example.project_vmo.models.request.PasswordResetRequest;
import com.example.project_vmo.models.response.MessageResponse;
import com.example.project_vmo.repositories.AccountRepo;
import com.example.project_vmo.repositories.PasswordResetTokenRepo;
import com.example.project_vmo.services.EmailSender;
import com.example.project_vmo.services.PasswordTokenService;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PasswordTokenServiceImpl implements PasswordTokenService {
  @Autowired
  private PasswordResetTokenRepo passwordResetTokenRepo;
  @Autowired
  private AccountRepo accountRepo;
  @Autowired
  private EmailSender emailSender;

  @Autowired
  private PasswordEncoder passwordEncoder;

  private static final String url = "http://localhost:8081/api/forgot-password/confirm?token=";

  @Override
  public String confirmTokenResetPassword(String token) {
    PasswordResetToken passwordResetToken = passwordResetTokenRepo.findByToken(token);
    LocalDateTime expiresAt = passwordResetToken.getExpiryDate();
    if (expiresAt.isBefore(LocalDateTime.now())) {
      throw new IllegalStateException("Token is already expired!");
    }
    passwordResetTokenRepo.updateConfirmedAt(token,LocalDateTime.now());
    return  "Your email is confirmed. Please input new password to reset password!!!";
  }

  @Override
  public String sendTokenToChangePassword(PasswordResetLinkRequest request) {
    Account isExist = accountRepo.findByEmail(request.getEmail());
    if(isExist != null){
      Account account = accountRepo.findByEmail(request.getEmail());
      String token = UUID.randomUUID().toString();
      saveConfirmationToken(account, token);
      String link = url + token;
      emailSender.sendEmail(account.getEmail(), buildEmail(account.getUsername(), link));
      return String.valueOf(new MessageResponse("Success: Token send successfully!" + token));
    } else {
      return String.valueOf(new MessageResponse("Fail: Email is not found"));
    }
  }

  @Override
  public MessageResponse changePassword(PasswordResetRequest request) {
    PasswordResetToken token = emailSender.getValidToken(request);
    if(StringUtils.isEmpty(request.getEmail())){
      return new MessageResponse("A new password is required");
    }
    if(request.getToken().equals(token.getToken())){
      updatePassword(request.getEmail(), request.getPassword());
      emailSender.inIsActiveToken(request);

      return new MessageResponse("Change password successfully!!");
    }
    return new MessageResponse("Change password fail!!");
  }

  @Override
  public void updatePassword(String email, String password) {
    Account account = accountRepo.findByEmail(email);
    account.setPassword(passwordEncoder.encode(password));
    accountRepo.save(account);
  }

  @Override
  public void saveConfirmationToken(Account account, String token) {
    PasswordResetToken passwordResetToken = new PasswordResetToken();
    passwordResetToken.setToken(token);
    passwordResetToken.setAccount(account);
    passwordResetToken.setExpiryDate(LocalDateTime.now().plusMinutes(15));
    passwordResetToken.setActive(true);
    passwordResetTokenRepo.save(passwordResetToken);
  }

  @Override
  public String buildEmail(String name, String link) {
    return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
        "\n" +
        "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
        "\n" +
        "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
        "    <tbody><tr>\n" +
        "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
        "        \n" +
        "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
        "          <tbody><tr>\n" +
        "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
        "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
        "                  <tbody><tr>\n" +
        "                    <td style=\"padding-left:10px\">\n" +
        "                  \n" +
        "                    </td>\n" +
        "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
        "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
        "                    </td>\n" +
        "                  </tr>\n" +
        "                </tbody></table>\n" +
        "              </a>\n" +
        "            </td>\n" +
        "          </tr>\n" +
        "        </tbody></table>\n" +
        "        \n" +
        "      </td>\n" +
        "    </tr>\n" +
        "  </tbody></table>\n" +
        "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
        "    <tbody><tr>\n" +
        "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
        "      <td>\n" +
        "        \n" +
        "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
        "                  <tbody><tr>\n" +
        "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
        "                  </tr>\n" +
        "                </tbody></table>\n" +
        "        \n" +
        "      </td>\n" +
        "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
        "    </tr>\n" +
        "  </tbody></table>\n" +
        "\n" +
        "\n" +
        "\n" +
        "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
        "    <tbody><tr>\n" +
        "      <td height=\"30\"><br></td>\n" +
        "    </tr>\n" +
        "    <tr>\n" +
        "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
        "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
        "        \n" +
        "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
        "        \n" +
        "      </td>\n" +
        "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
        "    </tr>\n" +
        "    <tr>\n" +
        "      <td height=\"30\"><br></td>\n" +
        "    </tr>\n" +
        "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
        "\n" +
        "</div></div>";
  }
}
