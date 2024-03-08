package org.example.mqtt_server.Repository;

import org.example.mqtt_server.entity.Lock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LockRepository extends JpaRepository<Lock,Integer> {
}
