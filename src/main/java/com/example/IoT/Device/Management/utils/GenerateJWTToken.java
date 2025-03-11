package com.example.IoT.Device.Management.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GenerateJWTToken {
  @Value("${spring.security.jwt.secret}")
  private String jwtSecret;

  @Value("${access.token.expiry.minutes}")
  private Integer expiry;

  public String createJWTToken(long expiryInMinutes, Map<String, Object> claims) {

    Date expiryDate = new Date(System.currentTimeMillis() + (expiryInMinutes * expiry * 1000));

    final Map<String, Object> jwtHeader = new HashMap<>();
    jwtHeader.put("alg", "HS256");
    jwtHeader.put("typ", "JWT");

    try {
      SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
      return Jwts.builder()
          .signWith(key)
          .header()
          .add(jwtHeader)
          .and()
          .claims(claims)
          .expiration(expiryDate)
          .compact();
    } catch (Exception e) {
      log.error("failed to generate token ", e);
      throw e;
    }
  }
}
