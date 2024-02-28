package org.example.mqtt_server.ServiceGrpc;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.example.mqtt_server.Entity.User;
import org.example.mqtt_server.Service.UserService;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.dao.DataIntegrityViolationException;
import service.Mqtt;

@GRpcService(applyGlobalInterceptors = false)
public class UnauthorizedUserServiceGrpc extends service.UnauthorizedUserServiceGrpc.UnauthorizedUserServiceImplBase {

    private UserService userService;

    @Override
    public void create(Mqtt.CreateRequest request, StreamObserver<Mqtt.CreateResponse> responseObserver) {
        Status errStatus = Status.ALREADY_EXISTS;
        try {
            User user = userService.createUser(request);
            responseObserver.onNext(Mqtt.CreateResponse.newBuilder().setUser(user.toProto()).build());
            responseObserver.onCompleted();
        } catch (DataIntegrityViolationException e) {
            responseObserver.onError(errStatus.asRuntimeException());
        }
    }
}
