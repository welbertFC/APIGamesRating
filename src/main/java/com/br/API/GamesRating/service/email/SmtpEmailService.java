package com.br.API.GamesRating.service.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;

public class SmtpEmailService extends AbstractEmailService {

  private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);

  @Autowired private JavaMailSender javaMailSender;

  @Override
  public void sendHtmlEmail(MimeMessage msg) {
    LOG.info("Enviando email...");
    javaMailSender.send(msg);
    LOG.info("Email enviado");
  }
}
