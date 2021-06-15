package com.br.API.GamesRating.service.email;

import com.br.API.GamesRating.model.UserClient;

import javax.mail.internet.MimeMessage;

public interface EmailService {

  void sendOrderConfirmationHtmlEmail(UserClient userClient);

  void sendHtmlEmail(MimeMessage msg);

  void sendNewPasswordEmail(UserClient userClient, String newPassword);
}
