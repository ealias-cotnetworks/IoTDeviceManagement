package com.example.IoT.Device.Management.mapper;

import com.example.IoT.Device.Management.model.Devices;
import com.example.IoT.Device.Management.request.DevicesRequest;
import com.example.IoT.Device.Management.vo.DevicesVO;

import java.util.List;
import java.util.stream.Collectors;

public class DevicesMapper {

    public static Devices getDevices(DevicesRequest request)
    {
        return Devices.builder().status(request.getStatus()).active(true).name(request.getName()).metadata(request.getMetadata()).timestamp(System.currentTimeMillis()).build();
    }
    public static DevicesVO getDevicesVO(Devices register)
    {
        return DevicesVO.builder().
                status(register.getStatus().name()).
                metadata(register.getMetadata()).registerId(register.getId()).name(register.getName()).build();
    }
    public static List<DevicesVO> getAllDevicesListVO(List<Devices> list)
    {
        return list.stream()
                .map(
                        regster ->
                                new DevicesVO(
                                        regster.getId(),
                                        regster.getName(),
                                        regster.getStatus().name(),
                                        regster.getMetadata()
                                ))
                .collect(Collectors.toList());
    }
}
