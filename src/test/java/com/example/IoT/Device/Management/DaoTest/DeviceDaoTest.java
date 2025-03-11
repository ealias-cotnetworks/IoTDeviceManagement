package com.example.IoT.Device.Management.DaoTest;

import com.example.IoT.Device.Management.dao.DevicesDao;
import com.example.IoT.Device.Management.model.Devices;
import com.example.IoT.Device.Management.repository.DevicesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class DeviceDaoTest {


    @Mock
    private DevicesRepository deviceRepository;
    @InjectMocks
    private DevicesDao deviceDao;

    private UUID id=UUID.randomUUID();

    @Test
    public void getActiveDevicesByDevicesId()  {
        Mockito.when(deviceRepository.findByIdAndActiveTrue(Mockito.any())).thenReturn(Optional.ofNullable(getDevice()));
        var response=deviceDao.getActiveDevicesByDevicesId(id);
        assertEquals("name",response.getName());
    }
    @Test
    public void saveDevices()  {
        Mockito.when(deviceRepository.save(Mockito.any())).thenReturn(getDevice());
        var response=deviceDao.saveDevices(getDevice());
        assertEquals("name",response.getName());
    }
    @Test
    public void deleteDevices()  {
        Mockito.doNothing().when(deviceRepository).deleteDevicesById(Mockito.any());
        deviceDao.deleteDevicesById(id);
    }
    @Test
    public void getRegisterByRegisterName()
    {
        Mockito.when(deviceRepository.getDevicesByName(Mockito.any())).thenReturn(getDevice());
        var response=deviceDao.getActiveDevicesByDevicesName("name");
        assertEquals("name",response.getName());
    }

    private Devices getDevice()
    {
        return Devices.builder().active(true).name("name").timestamp(111).build();
    }
}
