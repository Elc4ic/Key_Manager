package org.example.mqtt_server.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.mqtt_server.Entity.Auth;
import org.example.mqtt_server.Entity.User;
import org.example.mqtt_server.Repository.AuthRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;

import javax.crypto.SecretKey;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AuthService {
    private AuthRepository authRepository;

    String ACCESS_TOKEN_KEY = "C20F2F601BAA9B88945249CC9D499948C069B517A2278DB6C3307E596F125660";
    String REFRESH_TOKEN_KEY = "249CC9D4B51601BAA9B9C20F2F32278DB6C6F125660307E59948C0698894597A";
    private final SecretKey accessTokenKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(ACCESS_TOKEN_KEY));
    private final SecretKey refreshTokenKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(REFRESH_TOKEN_KEY));

    private String createAccessToken(Long userId) {
        return Jwts.builder()
                .setIssuer("http://localhost")
                .setSubject(userId.toString())
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
                .setExpiration(Timestamp.valueOf(LocalDateTime.now().plusDays(1)))
                .signWith(accessTokenKey)
                .compact();
    }

    private String createRefreshToken(Long userId) {
        return Jwts.builder()
                .setIssuer("http://localhost")
                .setSubject(userId.toString())
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
                .setExpiration(Timestamp.valueOf(LocalDateTime.now().plusMonths(1)))
                .signWith(refreshTokenKey)
                .compact();
    }

    public Auth getAuthByUserId(Long userId) {
        return authRepository.getByUserId(userId);
    }

    public Boolean checkPassword(String hashedPassword, String originalPassword) {
        return BCrypt.checkpw(originalPassword, hashedPassword);
    }

    String hashPassword(String password) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(password, salt);
    }

    public Pair<String, String> createJwt(User user) {
        return Pair.of(createAccessToken(user.getId()), createRefreshToken(user.getId()));
    }

    public void saveRefreshToken(Auth auth, String refreshToken) {
        auth.setRefreshToken(refreshToken);
        authRepository.save(auth);
    }
}
