package com.example.IoT.Device.Management.repository;

import com.example.IoT.Device.Management.model.Devices;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface DevicesRepository extends JpaRepository<Devices, UUID> {

    Optional<Devices> findByIdAndActiveTrue(UUID id);

    @Modifying
    @Query("update Devices d  set d.active=false where d.id=:id")
    void deleteDevicesById(@Param("id") UUID id);

    @Query("select d from Devices d where d.name=:name and d.active=true ")
    Devices getDevicesByName(@Param("name") String name);

    @Query("select d from Devices d where d.active=true and d.name like:name")
    Page<Devices> getDevicesAllPageByName(@Param("name")String name, Pageable pageable);

    @Query("select d from Devices d where d.active=true ")
    Page<Devices> getDevicesAllPage(Pageable pageable);

}
