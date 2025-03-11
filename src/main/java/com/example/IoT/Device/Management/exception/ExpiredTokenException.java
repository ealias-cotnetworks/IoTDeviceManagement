package com.example.IoT.Device.Management.exception;

import com.example.IoT.Device.Management.utils.Constants;
import org.springframework.http.HttpStatus;

public class ExpiredTokenException extends IotDeviceManagement {
  private static final long serialVersionUID = 30763009752460581L;

  public ExpiredTokenException() {
    super("Token is expired", HttpStatus.UNAUTHORIZED, Constants.EXPIRED_TOKEN_ERROR_CODE);
  }
}
