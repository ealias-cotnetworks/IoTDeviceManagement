package com.example.IoT.Device.Management.exception;

import com.example.IoT.Device.Management.utils.Constants;
import org.springframework.http.HttpStatus;

public class InvalidTokenException extends IotDeviceManagement {
  private static final long serialVersionUID = 30763009752460581L;

  public InvalidTokenException() {
    super("Token is invalid", HttpStatus.UNAUTHORIZED, Constants.INVALID_TOKEN_ERROR_CODE);
  }
}
