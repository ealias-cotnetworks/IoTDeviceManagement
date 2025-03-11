package com.example.IoT.Device.Management.ServiceTest;

import com.example.IoT.Device.Management.dao.DevicesDao;
import com.example.IoT.Device.Management.exception.DevicesAllreadyExistException;
import com.example.IoT.Device.Management.exception.DevicesNotFountException;
import com.example.IoT.Device.Management.model.Devices;
import com.example.IoT.Device.Management.model.enums.Status;
import com.example.IoT.Device.Management.request.DevicesRequest;
import com.example.IoT.Device.Management.service.DevicesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
@ExtendWith(MockitoExtension.class)
public class DeviceServiceTest {


    @Mock
    private DevicesDao devicesDao;
    @InjectMocks
    private DevicesService devicesService;

    private UUID id=UUID.randomUUID();

    @Test
    public void saveDeviceExistException()
    {
        Mockito.when(devicesDao.getActiveDevicesByDevicesName(Mockito.anyString())).thenReturn(getDevice());
        assertThrows(DevicesAllreadyExistException.class,()-> devicesService.saveDevices(getDeviceRequest()));
    }
    @Test
    public void saveDevice() throws DevicesAllreadyExistException {
        Mockito.when(devicesDao.getActiveDevicesByDevicesName(Mockito.anyString())).thenReturn(null);
        Mockito.when(devicesDao.saveDevices(Mockito.any())).thenReturn(getDevice());
        var response= devicesService.saveDevices(getDeviceRequest());
        assertEquals("name",response.getName());
    }
    @Test
    public void getDeviceByIdforDiviceNoFountException()
    {
        Mockito.when(devicesDao.getActiveDevicesByDevicesId(Mockito.any())).thenReturn(null);
        assertThrows(DevicesNotFountException.class,()-> devicesService.getDevicesById(id));
    }
    @Test
    public void getDeviceByDeviceId() throws DevicesNotFountException {
        Mockito.when(devicesDao.getActiveDevicesByDevicesId(Mockito.any())).thenReturn(getDevice());
        var response= devicesService.getDevicesById(id);
        assertEquals("name",response.getName());
    }
    @Test
    public void deleteDeviceByDeviceIdforDeviceIdNotFountException() throws DevicesNotFountException {
        Mockito.when(devicesDao.getActiveDevicesByDevicesId(Mockito.any())).thenReturn(null);
        assertThrows(DevicesNotFountException.class,()-> devicesService.deleteDevices(id));
    }
    @Test
    public void deleteDeviceByDeviceId() throws DevicesNotFountException {
        Mockito.when(devicesDao.getActiveDevicesByDevicesId(Mockito.any())).thenReturn(getDevice());
        Mockito.doNothing().when(devicesDao).deleteDevicesById(Mockito.any());
        devicesService.deleteDevices(id);
    }
    @Test
    public void updateDeviceForDeviceNotFountException()
    {
        Mockito.when(devicesDao.getActiveDevicesByDevicesId(Mockito.any())).thenReturn(null);
        assertThrows(DevicesNotFountException.class,()-> devicesService.updateDevices(getDeviceRequest(),id));
    }
    @Test
    public void updateRegister() throws DevicesAllreadyExistException, DevicesNotFountException {
        Mockito.when(devicesDao.getActiveDevicesByDevicesId(Mockito.any())).thenReturn(getDevice());
        Mockito.when(devicesDao.saveDevices(Mockito.any())).thenReturn(getDevice());
        var response= devicesService.updateDevices(getDeviceRequest(),id);
        assertEquals("name",response.getName());
    }
    private Devices getDevice()
    {
        return Devices.builder().status(Status.ERROR).active(true).name("name").timestamp(111).build();
    }
    private DevicesRequest getDeviceRequest()
    {
        return DevicesRequest.builder().name("name").build();
    }

}
