package org.example.mqtt_server.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.mqtt_server.Entity.Auth;
import org.example.mqtt_server.Entity.User;
import org.example.mqtt_server.Repository.UserRepository;
import org.springframework.stereotype.Service;
import service.Mqtt;

import java.util.List;

@Service
public class UserService {

    private AuthService authService;
    private UserRepository userRepository;

    @Transactional
    public User createUser(Mqtt.CreateRequest request) {
        User user = User.fromProto(request.getUser());
        Auth auth = Auth.builder()
                .password(authService.hashPassword(request.getPassword()))
                .user(user)
                .build();
        user.setAuth(auth);
        return userRepository.save(user);
    }

    public User getUserByEmail(String email){
        return userRepository.getByEmail(email);
    }

}
