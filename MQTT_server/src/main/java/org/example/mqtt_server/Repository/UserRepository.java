package org.example.mqtt_server.Repository;

import org.example.mqtt_server.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
