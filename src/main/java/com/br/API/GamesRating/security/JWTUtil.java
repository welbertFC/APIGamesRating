package com.br.API.GamesRating.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {

  @Value("${JWT_SECRET}")
  private String jwtSecret;

  @Value("${jwt.expiration}")
  private Long expiration;

  public String generateToken(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setExpiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(SignatureAlgorithm.HS512, jwtSecret.getBytes(StandardCharsets.UTF_8))
        .compact();
  }

  public boolean tokenValid(String token) {
    Claims claims = getClaims(token);
    if (claims != null) {
      var username = claims.getSubject();
      var expirationDate = claims.getExpiration();
      var now = new Date(System.currentTimeMillis());
      if (username != null && expirationDate != null && now.before(expirationDate)) {
        return true;
      }
    }
    return false;
  }

  private Claims getClaims(String token) {
    try {
      return Jwts.parser()
          .setSigningKey(jwtSecret.getBytes(StandardCharsets.UTF_8))
          .parseClaimsJws(token)
          .getBody();
    } catch (Exception e) {
      return null;
    }
  }

  public String getUsername(String token) {
    Claims claims = getClaims(token);
    if (claims != null) {
      return claims.getSubject();
    }
    return null;
  }
}
