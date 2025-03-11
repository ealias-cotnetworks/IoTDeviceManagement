package com.example.IoT.Device.Management.utils;

import com.example.IoT.Device.Management.auth.AuthUser;
import java.util.Random;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class Utility {

  private static String system = "system";

  private Utility() {
    throw new IllegalStateException("Utility class");
  }

  private static final Random random = new Random();

  public static String convertToSnakeCase(String camelCaseString) {
    return camelCaseString.replaceAll("(.)(\\p{Upper})", "$1_$2").toLowerCase();
  }

  public static String getUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      return system;
    }
    if (authentication.getPrincipal() instanceof String) {
      return (String) authentication.getPrincipal();
    }
    return ((AuthUser) authentication.getPrincipal()).getUserId();
  }

  public static String getUserName() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      return system;
    }
    if (authentication.getPrincipal() instanceof String) {
      return system;
    }
    String name = ((AuthUser) authentication.getPrincipal()).getName();
    return name;
  }

  public static String getUserRole() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      return system;
    }
    if (authentication.getPrincipal() instanceof String) {
      return system;
    }
    String name = ((AuthUser) authentication.getPrincipal()).getRole();
    return name;
  }
}
