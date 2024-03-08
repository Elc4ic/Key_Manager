package org.example.mqtt_server.Repository;

import org.example.mqtt_server.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
}
