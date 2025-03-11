package com.example.IoT.Device.Management.dao;

import com.example.IoT.Device.Management.model.Devices;
import com.example.IoT.Device.Management.repository.DevicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class DevicesDao {

    @Autowired
    public DevicesRepository registerRepository;

    public Devices saveDevices(Devices entity)
    {
        return registerRepository.save(entity);
    }
    public Devices getActiveDevicesByDevicesId(UUID id)
    {
        return registerRepository.findByIdAndActiveTrue(id).orElse(null);
    }
    public Devices getActiveDevicesByDevicesName(String name)
    {
        return registerRepository.getDevicesByName(name);
    }
    public void deleteDevicesById(UUID id)
    {
        registerRepository.deleteDevicesById(id);
    }
    public Page<Devices> getAllDevicesPage(Pageable pageable)
    {
        return registerRepository.getDevicesAllPage(pageable);
    }
    public Page<Devices> getAllDevicesPageByName(Pageable pageable, String name)
    {
        return registerRepository.getDevicesAllPageByName(name,pageable);
    }
}
