CREATE TABLE devices (
  id BINARY(16) NOT NULL,
   name VARCHAR(255) NULL,
   status VARCHAR(255) NULL,
   timestamp BIGINT NULL,
   metadate JSON NOT NULL,
   active BIT(1) NULL,
   CONSTRAINT pk_devices PRIMARY KEY (id)
);

ALTER TABLE devices ADD CONSTRAINT uc_devices_name UNIQUE (name);


CREATE TABLE roles (
  id BINARY(16) NOT NULL,
   name VARCHAR(255) NULL,
   CONSTRAINT pk_roles PRIMARY KEY (id)
);

ALTER TABLE roles ADD CONSTRAINT uc_roles_name UNIQUE (name);

CREATE TABLE users (
  id BINARY(16) NOT NULL,
   username VARCHAR(255) NULL,
   password VARCHAR(255) NULL,
   active BIT(1) NULL,
   email VARCHAR(255) NULL,
   role_id BINARY(16) NULL,
   CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users ADD CONSTRAINT uc_users_username UNIQUE (username);

ALTER TABLE users ADD CONSTRAINT FK_USERS_ON_ROLE FOREIGN KEY (role_id) REFERENCES roles (id);

INSERT INTO iotdevicemanagement.roles(id,name)
VALUES(UUID_TO_BIN(UUID()), 'USER');
INSERT INTO iotdevicemanagement.roles(id,name)
VALUES(UUID_TO_BIN(UUID()), 'ADMIN');
