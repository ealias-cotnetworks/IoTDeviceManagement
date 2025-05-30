package com.example.IoT.Device.Management.repository;

import com.example.IoT.Device.Management.model.Role;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, UUID> {
  @Query("select r from Role r where r.name=:role ")
  Role getRoleByName(@Param("role") String role);
}
