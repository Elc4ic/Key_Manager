package org.example.mqtt_server.Repository;

import org.example.mqtt_server.Entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<Auth, Long> {
    Auth getByUserId(Long userId);
}
