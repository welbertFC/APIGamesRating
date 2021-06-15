package com.br.API.GamesRating.settings;

import com.br.API.GamesRating.service.email.EmailService;
import com.br.API.GamesRating.service.email.SmtpEmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailSettings {

  @Bean
  public EmailService emailService() {
    return new SmtpEmailService();
  }
}
