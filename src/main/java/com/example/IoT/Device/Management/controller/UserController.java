package com.example.IoT.Device.Management.controller;

import com.example.IoT.Device.Management.exception.*;
import com.example.IoT.Device.Management.request.*;
import com.example.IoT.Device.Management.service.UserService;
import com.example.IoT.Device.Management.vo.LoginVO;
import com.example.IoT.Device.Management.vo.ResponseVO;
import com.example.IoT.Device.Management.vo.UsersVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@Validated
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping("/create")
  public ResponseEntity<ResponseVO<Object>> createUser(
      @RequestBody(required = true) UserCreateRequest request)
      throws RoleNotFoundException, UsersExistsException {
    ResponseVO<Object> response = new ResponseVO<>();
    UsersVO usersVO = userService.createUser(request);
    response.addData(usersVO);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping("/login")
  public ResponseEntity<ResponseVO<Object>> loginUser(
      @RequestBody(required = true) LoginRequest loginRequest)
      throws AuthenticationFailureException {
    ResponseVO<Object> response = new ResponseVO<>();
    LoginVO loginVO = userService.loginUser(loginRequest);
    response.addData(loginVO);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
