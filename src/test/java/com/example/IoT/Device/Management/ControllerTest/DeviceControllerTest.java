package com.example.IoT.Device.Management.ControllerTest;

import com.example.IoT.Device.Management.controller.DevicesController;
import com.example.IoT.Device.Management.request.DevicesRequest;
import com.example.IoT.Device.Management.service.DevicesService;
import com.example.IoT.Device.Management.vo.DevicesVO;
import com.example.IoT.Device.Management.vo.ResponseVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class DeviceControllerTest {
    @InjectMocks
    private DevicesController deviceController;
    @Mock
    private DevicesService deviceService;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper=new ObjectMapper();

    @BeforeEach
    public void setup()
    {
        mockMvc= MockMvcBuilders.standaloneSetup(deviceController).build();
    }
    @Test
    public  void saveDevice() throws Exception {
        Mockito.when(deviceService.saveDevices(Mockito.any())).thenReturn(getDeviceVO());
        ResultActions resultActions=mockMvc.perform(post("/devices/create").content(objectMapper.writeValueAsString(getDeviceRequest())).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(print());
        ResponseVO response = objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), ResponseVO.class);
        assertEquals("success",response.getMessage());
    }
    @Test
    public void deleteDevice() throws Exception {
        Mockito.doNothing().when(deviceService).deleteDevices(Mockito.any());
        ResultActions resultActions=mockMvc.perform(delete("/devices/22936e6a-5b86-463b-9808-79903f718e9a/delete")).andDo(print()).andExpect(status().isOk());
        ResponseVO responseVO=objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsString(),ResponseVO.class);
        assertEquals(200,responseVO.getStatus());

    }

    @Test
    public  void updateDevice() throws Exception {
        Mockito.when(deviceService.updateDevices(Mockito.any(),Mockito.any())).thenReturn(getDeviceVO());
        ResultActions resultActions=mockMvc.perform(put("/devices/22936e6a-5b86-463b-9808-79903f718e9a/update").content(objectMapper.writeValueAsString(getDeviceRequest())).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(print());
        ResponseVO response = objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), ResponseVO.class);
        assertEquals("success",response.getMessage());
    }
    @Test
    public  void getdevices() throws Exception {
        Mockito.when(deviceService.getDevicesById(Mockito.any())).thenReturn(getDeviceVO());
        ResultActions resultActions=mockMvc.perform(get("/devices/22936e6a-5b86-463b-9808-79903f718e9a/detials").content(objectMapper.writeValueAsString(getDeviceRequest())).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(print());
        ResponseVO response = objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), ResponseVO.class);
        assertEquals("success",response.getMessage());
    }


    public DevicesVO getDeviceVO()
    {
        return DevicesVO.builder().registerId(UUID.randomUUID()).name("name").build();
    }
    public DevicesRequest getDeviceRequest()
    {
        return DevicesRequest.builder().name("name").build();
    }
}
