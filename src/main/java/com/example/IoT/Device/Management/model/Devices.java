package com.example.IoT.Device.Management.model;

import com.example.IoT.Device.Management.model.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "devices")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Devices {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "name", unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "timestamp")
    private long timestamp;

    @Column(name = "metadate", nullable = false, columnDefinition = "json" )
    private String metadata;

    @Column(name = "active")
    private Boolean active;

}
