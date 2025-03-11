package com.example.IoT.Device.Management.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UsersVO {

  @JsonProperty(value = "user_id")
  private UUID userId;

  @JsonProperty(value = "user_name")
  private String userName;

  @JsonProperty(value = "role")
  private String userRole;

  @JsonProperty(value = "email")
  private String email;
}
