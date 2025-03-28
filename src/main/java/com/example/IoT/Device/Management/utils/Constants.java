package com.example.IoT.Device.Management.utils;

public class Constants {
  private Constants() {
    throw new IllegalStateException("Constants class");
  }

  // custom 401 errors
  public static final String UNAUTHORIZED_ERROR_CODE = "401-100";
  public static final String INVALID_USER_CREDENTIALS = "401-101";
  public static final String INVALID_TOKEN_ERROR_CODE = "401-102";

  public static final String UNAUTHOR_ERROR_CODE = "401-104";


  // custom 404

  public static final String ROLE_NOT_FOUND_ERROR_CODE = "404-101";
  public static final String EXPIRED_TOKEN_ERROR_CODE = "404-102";

  public static final String DEVICES_NOT_FOUND_ERROR_CODE = "404-103";


  // custom 409
  public static final String USER_EXISTS_ERROR_CODE = "409-100";
  public static final String DEVICES_EXISTS_ERROR_CODE = "409-101";
}
