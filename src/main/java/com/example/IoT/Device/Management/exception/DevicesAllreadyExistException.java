package com.example.IoT.Device.Management.exception;

import com.example.IoT.Device.Management.utils.Constants;
import org.springframework.http.HttpStatus;

public class DevicesAllreadyExistException extends IotDeviceManagement{
    public DevicesAllreadyExistException(String name)
    {
        super(
                "Devices name  (" + name + ") already exists",
                HttpStatus.CONFLICT,
                Constants.DEVICES_EXISTS_ERROR_CODE,
                "Devices name   available",
                "Devices name  already being used! Please try another");
    }

}
