package com.example.IoT.Device.Management.config;

import static java.lang.String.format;

import com.example.IoT.Device.Management.filter.JwtTokenFilter;
import com.example.IoT.Device.Management.interceptor.GlobalExceptionHandler;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@ConditionalOnExpression("${spring.security.enabled:true}")
@EnableMethodSecurity
public class SecurityConfig {

  @Value("${springdoc.api-docs.path}")
  private String restApiDocPath;

  @Value("${springdoc.swagger-ui.path}")
  private String swaggerPath;

  private final JwtTokenFilter jwtTokenFilter;

  public SecurityConfig(JwtTokenFilter jwtTokenFilter) {
    this.jwtTokenFilter = jwtTokenFilter;
  }

  @Bean
  GrantedAuthorityDefaults grantedAuthorityDefaults() {
    return new GrantedAuthorityDefaults("");
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.cors(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(
            sessionManagementConfigurer ->
                sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .exceptionHandling(
            exceptionHandlingConfigurer ->
                exceptionHandlingConfigurer.authenticationEntryPoint(
                    (request, response, ex) -> sendUnauthorizedResponse(response, ex)))
        .authorizeHttpRequests(
            request ->
                request
                    // Swagger endpoints must be publicly accessible
                    .requestMatchers("/")
                    .permitAll()
                    .requestMatchers("/")
                    .permitAll()
                    .requestMatchers(format("%s/**", restApiDocPath))
                    .permitAll()
                    .requestMatchers(format("%s/**", swaggerPath))
                    .permitAll()
                    .requestMatchers("/public/**")
                    .permitAll()
                    .requestMatchers("/user/login")
                    .permitAll()
                    .requestMatchers("/user/create")
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, "/test/**")
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/swagger-ui.html")
                    .permitAll()
                    .anyRequest()
                    .authenticated());
    // Add JWT token filter
    http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  private void sendUnauthorizedResponse(HttpServletResponse response, Exception ex)
      throws IOException {
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType("application/json");
    response
        .getOutputStream()
        .write(GlobalExceptionHandler.getUnauthorizedResponseForAuthFilterErrors(ex));
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
