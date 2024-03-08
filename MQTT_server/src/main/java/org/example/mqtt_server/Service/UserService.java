package org.example.mqtt_server.Service;

import jakarta.transaction.Transactional;
import org.example.mqtt_server.Entity.User;
import org.example.mqtt_server.Repository.UserRepository;
import org.springframework.stereotype.Service;
import service.Mqtt;

@Service
public class UserService {

    private UserRepository userRepository;

}
