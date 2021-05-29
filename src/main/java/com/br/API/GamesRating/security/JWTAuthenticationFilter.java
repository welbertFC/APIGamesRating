package com.br.API.GamesRating.security;

import com.br.API.GamesRating.dto.LoginDTO;
import com.br.API.GamesRating.service.UserClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private AuthenticationManager authenticationManager;

  private JWTUtil jwtUtil;

  @Autowired
  private UserClientService clientService;

  public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
    this.authenticationManager = authenticationManager;
    this.jwtUtil = jwtUtil;
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    try {
      var loginDTO = new ObjectMapper().readValue(request.getInputStream(), LoginDTO.class);
      var authenticationToken =
          new UsernamePasswordAuthenticationToken(
              loginDTO.getEmail(), loginDTO.getPassword(), new ArrayList<>());
      var authentication = authenticationManager.authenticate(authenticationToken);
      return authentication;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authentication)
      throws IOException, ServletException {

    var username = ((UserSS) authentication.getPrincipal()).getUsername();
    var user = ((UserSS) authentication.getPrincipal());
    var token = jwtUtil.generateToken(username);

    var gson = new Gson();
    String userJson = gson.toJson(user);
    response.addHeader("Authorization", "Bearer " + token);
    response.getWriter().append(userJson);
  }

  private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(
        HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
        throws IOException, ServletException {
      response.setStatus(401);
      response.setContentType("application/json");
      response.getWriter().append(json());
    }

    private String json() {
      long date = new Date().getTime();
      return "{\"timestamp\": "
          + date
          + ", "
          + "\"status\": 401, "
          + "\"error\": \"Não autorizado\", "
          + "\"message\": \"Email ou senha inválidos\", "
          + "\"path\": \"/login\"}";
    }
  }
}
