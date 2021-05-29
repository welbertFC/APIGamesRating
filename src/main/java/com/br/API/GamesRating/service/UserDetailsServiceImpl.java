package com.br.API.GamesRating.service;

import com.br.API.GamesRating.repository.UserRepository;
import com.br.API.GamesRating.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    var user = userRepository.findByEmail(email);
    if (user == null) {
      throw new UsernameNotFoundException(email);
    }
    return new UserSS(
        user.getId(),
        user.getName(),
        user.getName(),
        user.getEmail(),
        fomateLocalDate(user.getBirthDate()),
        fomateLocalDateTime(user.getDateCreated()),
        user.getUrlImage(),
        user.getPassword(),
        user.getProfile());
  }

  public String fomateLocalDate(LocalDate date) {
    if (date != null) {
      var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
      try {
        return (formatter.format(date));
      } catch (Exception e) {
        return "Erro na conversão da data: " + e.getLocalizedMessage();
      }
    }
    return "";
  }

  public String fomateLocalDateTime(LocalDateTime date) {
    if (date != null) {
      var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
      try {
        return (formatter.format(date));
      } catch (Exception e) {
        return "Erro na conversão da data: " + e.getLocalizedMessage();
      }
    }
    return "";
  }
}
