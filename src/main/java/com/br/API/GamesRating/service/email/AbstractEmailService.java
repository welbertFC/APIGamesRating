package com.br.API.GamesRating.service.email;

import com.br.API.GamesRating.model.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

public abstract class AbstractEmailService implements EmailService {

  @Value("${default.sender}")
  private String sender;

  @Autowired private TemplateEngine templateEngine;

  @Autowired private JavaMailSender javaMailSender;

  protected String htmlFromTemplateNewUser(UserClient client) {
    Context context = new Context();
    context.setVariable("client", client);
    return templateEngine.process("newUser", context);
  }

  protected String htmlFromTemplatePasswordForgot(UserClient client, String newPassword) {
    Context context = new Context();
    context.setVariable("client", client);
    context.setVariable("newPassword", newPassword);
    return templateEngine.process("passwordForgot", context);
  }

  @Override
  public void sendOrderConfirmationHtmlEmail(UserClient client) {
    try {
      var mimeMessage = prepareMimeMessageFromPedido(client);
      sendHtmlEmail(mimeMessage);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }

  protected MimeMessage prepareMimeMessageFromPedido(UserClient client) throws MessagingException {
    var mimeMessage = javaMailSender.createMimeMessage();
    var mmh = new MimeMessageHelper(mimeMessage, true);
    mmh.setTo(client.getEmail());
    mmh.setFrom(sender);
    mmh.setSubject("Bem vindo ao Game Rating " + client.getName() + " !!!!!");
    mmh.setSentDate(new Date(System.currentTimeMillis()));
    mmh.setText(htmlFromTemplateNewUser(client), true);
    return mimeMessage;
  }

  @Override
  public void sendNewPasswordEmail(UserClient client, String newPassword) {
    try {
      var mimeMessage = prepareMimeMessageNewPassword(client, newPassword);
      sendHtmlEmail(mimeMessage);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }

  protected MimeMessage prepareMimeMessageNewPassword(UserClient client, String newPassword)
      throws MessagingException {
    var mineMessage = javaMailSender.createMimeMessage();
    var mmh = new MimeMessageHelper(mineMessage, true);
    mmh.setTo(client.getEmail());
    mmh.setFrom(sender);
    mmh.setSubject("Solicitação de nova senha");
    mmh.setSentDate(new Date(System.currentTimeMillis()));
    mmh.setText(htmlFromTemplatePasswordForgot(client, newPassword), true);
    return mineMessage;
  }
}
