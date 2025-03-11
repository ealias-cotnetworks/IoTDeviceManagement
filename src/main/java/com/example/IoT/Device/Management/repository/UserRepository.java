package com.example.IoT.Device.Management.repository;

import com.example.IoT.Device.Management.model.Users;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<Users, UUID> {

  @Query("select u from Users u where u.username=:name and u.active=true")
  Users getUserByName(@Param("name") String name);
}
