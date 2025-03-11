package com.example.IoT.Device.Management.exception;

import com.example.IoT.Device.Management.utils.Constants;
import org.springframework.http.HttpStatus;

public class AuthenticationFailureException extends IotDeviceManagement {
  private static final long serialVersionUID = 30763009752460581L;

  public AuthenticationFailureException() {
    super(
        "Authentication failed due to invalid credentials",
        HttpStatus.UNAUTHORIZED,
        Constants.INVALID_USER_CREDENTIALS,
        "Invalid credentials",
        "Username or password is not correct");
  }
}
