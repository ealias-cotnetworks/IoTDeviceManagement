package com.example.IoT.Device.Management.dao;

import com.example.IoT.Device.Management.model.Role;
import com.example.IoT.Device.Management.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository

public class RoleDao {
  @Autowired
  private
  RoleRepository roleRepository;

  public Role getRoleByName(String role) {
    return roleRepository.getRoleByName(role);
  }
}
