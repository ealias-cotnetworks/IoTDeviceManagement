package com.example.IoT.Device.Management.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserCreateRequest {
  @NotEmpty
  @JsonProperty(value = "user_name")
  private String username;

  @NotEmpty
  @JsonProperty(value = "password")
  private String password;

  @NotEmpty
  @JsonProperty(value = "user_role")
  private String userRole;

  @Email @NotEmpty private String email;
}
