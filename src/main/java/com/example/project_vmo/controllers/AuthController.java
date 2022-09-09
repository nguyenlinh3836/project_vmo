package com.example.project_vmo.controllers;

import com.example.project_vmo.models.entities.RefreshToken;
import com.example.project_vmo.models.request.LoginDto;
import com.example.project_vmo.models.response.JWTAuthResponse;
import com.example.project_vmo.security.jwt.JwtUtils;
import com.example.project_vmo.services.RefreshTokenService;
import com.example.project_vmo.services.UserStaticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private UserStaticService userStaticService;

  @Autowired
  private RefreshTokenService refreshTokenService;

  @PostMapping("/buyer")
  public ResponseEntity<JWTAuthResponse> authenticateBuyer(@RequestBody LoginDto loginDto) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginDto.getUsernameOrEmail(), loginDto.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    String token = jwtUtils.generateJwtToken(authentication);

    RefreshToken refreshToken = refreshTokenService.createRefreshToken(loginDto.getUsernameOrEmail());

    userStaticService.totalStatic(loginDto.getUsernameOrEmail());

    JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();

    jwtAuthResponse.setAccessToken(token);

    jwtAuthResponse.setRefreshToken(refreshToken.getToken());

    return ResponseEntity.ok(jwtAuthResponse);
  }

  @PostMapping("/supplier")
  public ResponseEntity<JWTAuthResponse> authenticateSupplier(@RequestBody LoginDto loginDto) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginDto.getUsernameOrEmail(), loginDto.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    userStaticService.totalStatic(loginDto.getUsernameOrEmail());

    String token = jwtUtils.generateJwtToken(authentication);

    return ResponseEntity.ok(new JWTAuthResponse(token));
  }

}
