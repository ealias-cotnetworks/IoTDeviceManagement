package com.example.IoT.Device.Management.listeners;

import com.example.IoT.Device.Management.dao.DevicesDao;
import com.example.IoT.Device.Management.mapper.DevicesMapper;
import com.example.IoT.Device.Management.model.Devices;
import com.example.IoT.Device.Management.request.DevicesRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class KafkaConsumer {
    @Autowired
    private DevicesDao devicesDao;

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
    @KafkaListener(topics = "thingwire.devices.events", groupId = "mygroup")
    public void deviceRegistered(String message) throws JsonProcessingException {
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            Devices devices = objectMapper.readValue(message, Devices.class);
            Devices saveDevices=devicesDao.saveDevices(devices);
            log.info(String.format("Create Devices successfully, Request: {}", message));
        }
        catch (Exception e)
        {
            log.error("Error while creating Devices,  Request: {}", message);
            throw e;
        }
    }
//    @KafkaListener(topics = "thingwire.devices.responses", groupId = "mygroup")
//    public void deviceCommandResponse(String message) throws JsonProcessingException {
//        try {
//
//            ObjectMapper objectMapper = new ObjectMapper();
//            Devices devicesClass = objectMapper.readValue(message, Devices.class);
//            Devices devices=devicesDao.getActiveDevicesByDevicesId(devicesClass.getId());
//            Devices saveDevices=devicesDao.saveDevices(devices);
//            log.info(String.format("Update Devices successfully, Request: {}", message));
//        }
//        catch (Exception e)
//        {
//            log.error("Error while Update Devices,  Request: {}", message);
//            throw e;
//        }
//    }
}