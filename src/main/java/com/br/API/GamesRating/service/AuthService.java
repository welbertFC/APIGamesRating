package com.br.API.GamesRating.service;

import com.br.API.GamesRating.exception.ObjectNotFoundException;
import com.br.API.GamesRating.repository.UserRepository;
import com.br.API.GamesRating.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {

  @Autowired private UserRepository userRepository;

  @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired private EmailService emailService;

  private Random random = new Random();

  public void sendNewPassword(String email) {
    var user = userRepository.findByEmail(email);
    if (user == null) {
      throw new ObjectNotFoundException("Email não encontrado");
    }

    var newPassWord = newPassWord();
    user.setPassword(bCryptPasswordEncoder.encode(newPassWord));

    userRepository.save(user);
    emailService.sendNewPasswordEmail(user, newPassWord);
  }

  private String newPassWord() {
    var vetor = new char[16];
    for (int i = 0; i < 16; i++) {
      vetor[i] = randomChar();
    }
    return new String(vetor);
  }

  private char randomChar() {
    var options = random.nextInt(3);
    switch (options) {
      case 0:
        return (char) (random.nextInt(16) + 48);
      case 1:
        return (char) (random.nextInt(26) + 65);
      default:
        return (char) (random.nextInt(26) + 97);
    }
  }
}
