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

  protected String htmlFromTemplatePedido(UserClient client) {
    Context context = new Context();
    context.setVariable("client", client);
    return templateEngine.process("newUser", context);
  }

  @Override
  public void sendOrderConfirmationHtmlEmail(UserClient client) {
    try {
      var mm = prepareMimeMessageFromPedido(client);
      sendHtmlEmail(mm);
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
    mmh.setText(htmlFromTemplatePedido(client), true);
    return mimeMessage;
  }
}
