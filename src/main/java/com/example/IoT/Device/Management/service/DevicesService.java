package com.example.IoT.Device.Management.service;

import com.example.IoT.Device.Management.dao.DevicesDao;
import com.example.IoT.Device.Management.exception.DevicesAllreadyExistException;
import com.example.IoT.Device.Management.exception.DevicesNotFountException;
import com.example.IoT.Device.Management.mapper.DevicesMapper;
import com.example.IoT.Device.Management.model.Devices;
import com.example.IoT.Device.Management.request.DevicesRequest;
import com.example.IoT.Device.Management.vo.PaginatedResponseVOAndCount;
import com.example.IoT.Device.Management.vo.DevicesVO;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.UUID;



@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
public class DevicesService {
    @Autowired
    private DevicesDao devicesDao;

    @Autowired
    private KafkaTemplate<Object, Devices> kafkaTemplate;

    public DevicesVO saveDevices(DevicesRequest request) throws DevicesAllreadyExistException {
        try
        {
            Devices existDevices= devicesDao.getActiveDevicesByDevicesName(request.getName());
            if(existDevices!=null)
            {
                throw new DevicesAllreadyExistException(request.getName());
            }
            Devices devices= DevicesMapper.getDevices(request);
            Devices saveRegister= devicesDao.saveDevices(devices);
            log.info("Create Devices successfully, Request: {}", request);
            return DevicesMapper.getDevicesVO(saveRegister);

        }
        catch (Exception e)
        {
            log.error("Error while creating Devices,  Request: {}", request);
            throw e;
        }
    }


    public void deleteDevices(UUID id) throws DevicesNotFountException {
        try {

            Devices existingDevices = devicesDao.getActiveDevicesByDevicesId(id);
            if (existingDevices == null) {
                throw new DevicesNotFountException(id);

            }
            devicesDao.deleteDevicesById(id);
            log.info(
                    "Deleted Devices successfully, devicesId: {}", id);
        } catch (Exception e) {
            log.error(
                    "Error while deleting Devices,devicesId: {} ", id);
            throw e;
        }
    }

    public DevicesVO getDevicesById(UUID id) throws DevicesNotFountException {
        try {

            Devices existingDevices = devicesDao.getActiveDevicesByDevicesId(id);
            if (existingDevices == null) {
                throw new DevicesNotFountException(id);

            }
            log.info("Retrieved Devices details successfully, devicesId: {}", id);
            return DevicesMapper.getDevicesVO(existingDevices);
        } catch (Exception e) {
            log.error("Error while fetching Devices details,devicesId: {}", id);
            throw e;
        }
    }
    public DevicesVO updateDevices(DevicesRequest request, UUID id) throws DevicesNotFountException {
        try {

            Devices existingDevices = devicesDao.getActiveDevicesByDevicesId(id);
            if (existingDevices == null) {
                throw new DevicesNotFountException(id);

            }
            existingDevices.setName(request.getName());
            existingDevices.setStatus(request.getStatus());
            existingDevices.setMetadata(request.getMetadata());
            Devices updateDevices= devicesDao.saveDevices(existingDevices);
            log.info("Update Devices successfully, id and  Request: {}",id, request);
            return DevicesMapper.getDevicesVO(updateDevices);
        } catch (Exception e) {
            log.error("Error while update Devices,id and  Request: {}",id, request);
            throw e;
        }
    }


    public PaginatedResponseVOAndCount<DevicesVO> listDevices(Integer offset, Integer limit, String filter) {
        try {
            Pageable pageable = PageRequest.of(offset - 1, limit);
            Page<Devices> devicesList;
            if (filter == null || filter.trim() == "")
            devicesList= devicesDao.getAllDevicesPage(pageable);
            else
            devicesList= devicesDao.getAllDevicesPageByName(pageable,filter);
            log.info("Retrieved register list successfully");
            var allDevicesListVO = DevicesMapper.getAllDevicesListVO(devicesList.toList());
            return new PaginatedResponseVOAndCount<>(
                    devicesList.getTotalElements(), allDevicesListVO);
        } catch (Exception e) {
            log.error("Error while Retrieved register list");
            throw e;
        }
    }

public void saveDevicesInKafka(DevicesRequest data,UUID id) throws DevicesAllreadyExistException {
    try {
        Devices existDevices=devicesDao.getActiveDevicesByDevicesId(id);
//        if(existDevices!=null)
//        {
//            updateKafka(existDevices);
//        }
        Devices devices=DevicesMapper.getDevices(data);
        saveKafka(devices);
    }
    catch (Exception e)
    {
        log.error("Error while creating Devices,  Request: {}", data);
        throw e;
    }
}
    public void updateKafka(Devices data) throws DevicesAllreadyExistException {
        try {
            Message<Devices> messager = MessageBuilder.withPayload(data).setHeader(KafkaHeaders.TOPIC, "java").build();
            kafkaTemplate.send("thingwire.devices.events", messager.getPayload());
        }
        catch (Exception e)
        {
            log.error("Error while creating Devices,  Request: {}", data);
            throw e;
        }
    }
    public void saveKafka(Devices data) throws DevicesAllreadyExistException {
        try {
            Message<Devices> messager = MessageBuilder.withPayload(data).setHeader(KafkaHeaders.TOPIC, "java").build();
            kafkaTemplate.send("thingwire.devices.responses", messager.getPayload());
        }
        catch (Exception e)
        {
            log.error("Error while creating Devices,  Request: {}", data);
            throw e;
        }
    }


}
