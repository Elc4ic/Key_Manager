package org.example.mqtt_server.Service;

import com.google.protobuf.StringValue;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.example.mqtt_server.Entity.Lock;
import org.example.mqtt_server.Repository.LockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.Mqtt;

import java.util.List;
import java.util.concurrent.TimeUnit;

@GrpcService
@AllArgsConstructor
public class LockService extends service.LockServiceGrpc.LockServiceImplBase {

    private final LockRepository lockRepository;

    @Autowired
    private MqttService mqttService;

    @Override
    public void getLockList(Mqtt.Empty request, StreamObserver<Mqtt.Lock> responseObserver) {
        List<Lock> locks = lockRepository.findAll();
        for (Lock lock : locks) {
            responseObserver.onNext(next(lock));
            System.out.println(lock);
        }
        responseObserver.onCompleted();
    }

    private Mqtt.Lock next(Lock lock) {
        return Mqtt.Lock.newBuilder()
                .setId(lock.getId())
                .setStatus(lock.getStatus())
                .build();
    }

    @Override
    public void open(Mqtt.Id request, StreamObserver<Mqtt.Empty> responseObserver) {
        String topic = "control/";
        try {
            mqttService.publish(topic + request.getId(), "1", 0, true);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
        responseObserver.onNext(Mqtt.Empty.newBuilder().build());
        responseObserver.onCompleted();
        try {
            TimeUnit.SECONDS.sleep(1);
            mqttService.publish(topic + request.getId(), "0", 0, true);
        } catch (InterruptedException | MqttException e) {
            throw new RuntimeException(e);
        }
    }

}
