package com.example.IoT.Device.Management.exception;

import com.example.IoT.Device.Management.utils.Constants;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class DevicesNotFountException extends IotDeviceManagement{

    public DevicesNotFountException(UUID id)
    {
        super(
                "Devices id is not found with id: " + id,
                HttpStatus.NOT_FOUND,
                Constants.DEVICES_NOT_FOUND_ERROR_CODE);

    }
}
