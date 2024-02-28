package org.example.mqtt_server.ServiceGrpc;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import org.example.mqtt_server.Entity.Auth;
import org.example.mqtt_server.Entity.User;
import org.example.mqtt_server.Service.AuthService;
import org.example.mqtt_server.Service.UserService;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.data.util.Pair;
import service.Mqtt;

@GRpcService
public class AuthServiceGrpc extends service.AuthServiceGrpc.AuthServiceImplBase {

    private AuthService authService;
    private UserService userService;

    @Override
    public void login(Mqtt.LoginRequest request, StreamObserver<Mqtt.LoginResponse> responseObserver) {
        User user = userService.getUserByEmail(request.getEmail());
        Auth auth = authService.getAuthByUserId(user.getId());
        Status errStatus = Status.UNAUTHENTICATED.withDescription("Token issues");

        if (authService.checkPassword(auth.getPassword(), request.getPassword())) {
            Pair<String, String> pair = authService.createJwt(user);
            authService.saveRefreshToken(auth, pair.getSecond());
            responseObserver.onNext(Mqtt.LoginResponse.newBuilder()
                    .setAccessToken(pair.getFirst())
                    .setRefreshToken(pair.getSecond())
                    .build());
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(errStatus.asRuntimeException());
        }
    }
}
