package com.example.IoT.Device.Management.service;

import com.example.IoT.Device.Management.dao.RoleDao;
import com.example.IoT.Device.Management.dao.UserDao;
import com.example.IoT.Device.Management.exception.*;
import com.example.IoT.Device.Management.mapper.UserMapper;
import com.example.IoT.Device.Management.model.Role;
import com.example.IoT.Device.Management.model.Users;
import com.example.IoT.Device.Management.request.*;
import com.example.IoT.Device.Management.utils.GenerateJWTToken;
import com.example.IoT.Device.Management.vo.LoginVO;
import com.example.IoT.Device.Management.vo.UsersVO;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@org.springframework.stereotype.Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class UserService {

  @Autowired
  private  UserDao userDao;
  @Autowired
  private RoleDao roleDao;
   @Autowired
   private  PasswordEncoder passwordEncoder;
  @Autowired
  private  GenerateJWTToken generateJWTToken;

  @Value("${access.token.expiry.minutes}")
  private long accessTokenExpiry;

  @Value("${spring.security.jwt.secret}")
  private String jwtSecret;



  public UsersVO createUser(UserCreateRequest request)
      throws RoleNotFoundException, UsersExistsException {
    try {
      Users existingUser = userDao.getUserByName(request.getUsername());
      if (existingUser != null) {
        throw new UsersExistsException(request.getUsername());
      }
      Role role = roleDao.getRoleByName(request.getUserRole());
      if (role == null) {
        throw new RoleNotFoundException(request.getUserRole());
      }
      Users users = UserMapper.createUser(request, role);
      users.setPassword(passwordEncoder.encode(users.getPassword()));
      Users savedUser = userDao.saveUser(users);
      log.info("Create user successfully, Request: {}", request);
      return UserMapper.createUserVO(savedUser);
    } catch (Exception e) {
      log.error("Error while creating user,  Request: {}", request);
      throw e;
    }
  }

  public LoginVO loginUser(LoginRequest loginRequest) throws AuthenticationFailureException {
    try {
      Users users = userDao.getUserByName(loginRequest.getUsername());
      if (users == null
          || !passwordEncoder.matches(loginRequest.getPassword(), users.getPassword())) {
        throw new AuthenticationFailureException();
      }
      String accessToken = getAccessToken(users);
      userDao.saveUser(users);
      log.info("User login successfully, Username: {}", loginRequest.getUsername());
      return UserMapper.createLoginVO(accessToken);
    } catch (Exception e) {
      log.error("Error while login, userName: {}", loginRequest.getUsername());
      throw e;
    }
  }

  private String getAccessToken(Users user) {
    Map<String, Object> claims = new HashMap<>();
    String role = user.getRole().getName();
    claims.put("sub", user.getId().toString());
    claims.put("name", user.getUsername());
    claims.put("role", role);
    return generateJWTToken.createJWTToken(accessTokenExpiry, claims);
  }
}
