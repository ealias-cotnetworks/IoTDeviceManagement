package com.example.IoT.Device.Management.model;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Users  {
  @Id
  @Column(name = "id", updatable = false, nullable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(name = "username", unique = true)
  private String username;

  @Column(name = "password")
  private String password;

  @Column(name = "active")
  private Boolean active;

  @Column(name = "email")
  private String email;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "role_id", referencedColumnName = "id")
  @ToString.Exclude
  private Role role;
}
