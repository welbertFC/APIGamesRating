package com.br.API.GamesRating.controller;

import com.br.API.GamesRating.dto.CheckUserEmail;
import com.br.API.GamesRating.security.JWTUtil;
import com.br.API.GamesRating.service.AuthService;
import com.br.API.GamesRating.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
@Api(tags = "Refresh Token")
public class AuthController {

  @Autowired private JWTUtil jwtUtil;

  @Autowired private AuthService authService;

  @PostMapping("/refresh_token")
  @ApiOperation(value = "Refresh token")
  public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
    var user = UserService.authenticated();
    var token = jwtUtil.generateToken(user.getUsername());
    response.addHeader("Authorization", "Bearer " + token);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/forgot")
  @ApiOperation(value = "Password forgot")
  public ResponseEntity<Void> passwordForgot(@Valid @RequestBody CheckUserEmail email) {
    authService.sendNewPassword(email.getEmail());
    return ResponseEntity.noContent().build();
  }
}
