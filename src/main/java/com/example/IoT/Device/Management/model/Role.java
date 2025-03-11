package com.example.IoT.Device.Management.model;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Role {

  @Id
  @Column(name = "id", updatable = false, nullable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @UuidGenerator
  private UUID id;

  @Column(name = "name", unique = true)
  private String name;
}
