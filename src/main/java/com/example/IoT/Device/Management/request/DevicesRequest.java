package com.example.IoT.Device.Management.request;

import com.example.IoT.Device.Management.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DevicesRequest {

    @NotNull()
    @JsonProperty("name")
    private String name;

    @NotNull()
    @JsonProperty("status")
    private Status status;

    @NotNull
    @JsonProperty("metadata")
    private String metadata;

}
