package com.example.IoT.Device.Management.dao;

import com.example.IoT.Device.Management.model.Users;
import com.example.IoT.Device.Management.repository.UserRepository;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository

public class UserDao {
  @Autowired
  private UserRepository userRepository;

  public Users getUserByName(String name) {
    return userRepository.getUserByName(name);
  }

  public Users getUserById(UUID id) {
    return userRepository.getReferenceById(id);
  }

  public Users saveUser(Users user) {
    return userRepository.save(user);
  }
}
