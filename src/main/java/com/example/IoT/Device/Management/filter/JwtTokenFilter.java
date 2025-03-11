package com.example.IoT.Device.Management.filter;

import com.example.IoT.Device.Management.auth.AuthUser;
import com.example.IoT.Device.Management.exception.ExpiredTokenException;
import com.example.IoT.Device.Management.exception.InvalidTokenException;
import com.example.IoT.Device.Management.interceptor.GlobalExceptionHandler;
import com.example.IoT.Device.Management.model.enums.Roles;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

  @Value("${spring.security.jwt.secret}")
  private String jwtSecret;

  private static final String AUTHORITIES_KEY = "role";
  private static final String AUTHORIZATION_PREFIX = "Bearer ";

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {
    // Get authorization header and validate
    final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (header == null || header.isEmpty() || !header.startsWith(AUTHORIZATION_PREFIX)) {
      chain.doFilter(request, response);
      return;
    }
    // Get jwt token and validate
    final String token = header.split(" ")[1].trim();
    try {
      validate(token);
    } catch (Exception e) {
      sendUnauthorizedResponse(response, e);
      return;
    }
    Claims claims = getClaims(token);
    String userId = claims.getSubject();
    String name = (String) claims.get("name");
    String role = (String) claims.get(AUTHORITIES_KEY);
    Roles roles = Roles.valueOf(role);

    // Get user identity and set it on the spring security context
    UserDetails userDetails = AuthUser.build(userId, roles, name);
    UsernamePasswordAuthenticationToken authentication =
        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    chain.doFilter(request, response);
  }

  private Claims getClaims(String token) {
    return Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token).getPayload();
  }

  private void validate(String token) throws ExpiredTokenException, InvalidTokenException {
    try {
      Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token).getPayload();
    } catch (ExpiredJwtException ex) {
      log.error("Expired JWT token - {}", ex.getMessage());
      throw new ExpiredTokenException();
    } catch (Exception ex) {
      log.error("Invalid JWT token - {}", ex.getMessage());
      throw new InvalidTokenException();
    }
  }

  private SecretKey getSecretKey() {
    return Keys.hmacShaKeyFor(jwtSecret.getBytes());
  }

  private void sendUnauthorizedResponse(HttpServletResponse response, Exception ex)
      throws IOException {
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType("application/json");
    response
        .getOutputStream()
        .write(GlobalExceptionHandler.getUnauthorizedResponseForAuthFilterErrors(ex));
  }
}
