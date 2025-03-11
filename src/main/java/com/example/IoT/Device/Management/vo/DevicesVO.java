package com.example.IoT.Device.Management.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DevicesVO {

    @JsonProperty(value = "register_id")
    private UUID registerId;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "status")
    private String status;

    @JsonProperty(value = "metadata")
    private String metadata;
}
