package org.example.mqtt_server.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import service.Mqtt;

import java.util.Set;

@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    String email;
    private String name;
    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "user"
    )
    private Auth auth;

    public static User fromProto(Mqtt.User user) {
        return new User(user.getId(),
                user.getEmail(),
                user.getName(),
                null
        );
    }
    public Mqtt.User toProto(){
        return Mqtt.User.newBuilder()
                .setId(id)
                .setEmail(email)
                .setName(name)
                .build();
    }
}