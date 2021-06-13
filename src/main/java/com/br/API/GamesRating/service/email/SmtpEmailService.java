package com.br.API.GamesRating.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;

public class SmtpEmailService extends AbstractEmailService {

  @Autowired private JavaMailSender javaMailSender;

  @Override
  public void sendHtmlEmail(MimeMessage msg) {
    javaMailSender.send(msg);
  }
}
