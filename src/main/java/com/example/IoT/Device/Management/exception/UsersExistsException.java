package com.example.IoT.Device.Management.exception;

import com.example.IoT.Device.Management.utils.Constants;
import org.springframework.http.HttpStatus;

public class UsersExistsException extends IotDeviceManagement {
  private static final long serialVersionUID = 30763009752460581L;

  public UsersExistsException(String name) {

    super(
        "Username  (" + name + ") already exists",
        HttpStatus.CONFLICT,
        Constants.USER_EXISTS_ERROR_CODE,
        "Username  not available",
        "Username  already being used! Please try another");
  }
}
