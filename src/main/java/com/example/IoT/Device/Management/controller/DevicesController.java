package com.example.IoT.Device.Management.controller;

import com.example.IoT.Device.Management.exception.DevicesAllreadyExistException;
import com.example.IoT.Device.Management.exception.DevicesNotFountException;
import com.example.IoT.Device.Management.request.DevicesRequest;
import com.example.IoT.Device.Management.service.DevicesService;
import com.example.IoT.Device.Management.vo.PaginatedResponseVOAndCount;
import com.example.IoT.Device.Management.vo.DevicesVO;
import com.example.IoT.Device.Management.vo.ResponseVO;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.UUID;


@RestController
@Slf4j
@Validated
@RequestMapping("/devices")
public class DevicesController {

    @Autowired
    private DevicesService devicesService;

    @PostMapping("/create")
    public ResponseEntity<ResponseVO<Object>> createDevices(
            @RequestBody(required = true) DevicesRequest request) throws DevicesAllreadyExistException {
        ResponseVO<Object> response = new ResponseVO<>();
        DevicesVO devicesVO= devicesService.saveDevices(request);
        response.addData(devicesVO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ResponseVO<Object>> deleteDevices(@PathVariable("id") UUID id) throws DevicesNotFountException {
        ResponseVO<Object> responseVO = new ResponseVO<>();
        devicesService.deleteDevices(id);
        return new ResponseEntity<>(responseVO, HttpStatus.OK);
    }
    @GetMapping("/{id}/detials")
    public ResponseEntity<ResponseVO<Object>> getDevices(@PathVariable("id") UUID id) throws DevicesNotFountException {
        ResponseVO<Object> responseVO = new ResponseVO<>();
        DevicesVO devicesVO= devicesService.getDevicesById(id);
        responseVO.addData(devicesVO);
        return new ResponseEntity<>(responseVO, HttpStatus.OK);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ResponseVO<Object>> updateDevices(
            @PathVariable("id") UUID id, @RequestBody(required = true) DevicesRequest request) throws DevicesNotFountException {
        ResponseVO<Object> responseVO = new ResponseVO<>();
        DevicesVO devicesVO= devicesService.updateDevices(request,id);
        responseVO.addData(devicesVO);
        return new ResponseEntity<>(responseVO, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseVO<DevicesVO>> listDevices(@Parameter(description = "offset, start from 1")
                                                               @RequestParam(value = "offset", required = false, defaultValue = "1") @Min(1) Integer offset,
                                                              @Parameter(description = "limit") @RequestParam(value = "limit", required = false, defaultValue = "10") @Min(1) Integer limit,
                                                              @RequestParam(value = "filter", required = false) String filter) {
        ResponseVO<DevicesVO> response = new ResponseVO<>();
        PaginatedResponseVOAndCount<DevicesVO> paginatedResponseVOAndCount = devicesService.listDevices(offset, limit,filter);
        response.addPaginatedDataList(paginatedResponseVOAndCount.getData(), paginatedResponseVOAndCount.getTotalCount());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/{id}/sends")
    public ResponseEntity<ResponseVO<Object>> saveDevicesInKafka( @RequestBody(required = true) DevicesRequest request,@PathVariable("id") UUID id) throws DevicesAllreadyExistException {
        ResponseVO responseVO=new ResponseVO<>();
        devicesService.saveDevicesInKafka(request,id);
        return new ResponseEntity<>(responseVO, HttpStatus.OK);
    }



}
