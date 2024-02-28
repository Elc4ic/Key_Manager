package org.example.mqtt_server.ServiceGrpc;

import io.grpc.stub.StreamObserver;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.example.mqtt_server.Entity.Lock;
import org.example.mqtt_server.Repository.LockRepository;
import org.example.mqtt_server.Service.MqttService;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import service.Mqtt;

import java.util.List;
import java.util.concurrent.TimeUnit;


@GRpcService
public class LockServiceGrpc extends service.LockServiceGrpc.LockServiceImplBase {

    private final LockRepository lockRepository;

    @Autowired
    private MqttService mqttService;

    @Value("${mqtt.actual}")
    private boolean isActual;

    public LockServiceGrpc(LockRepository lockRepository) {
        this.lockRepository = lockRepository;
    }

    @Override
    public void getLockList(Mqtt.Empty request, StreamObserver<Mqtt.Lock> responseObserver) {
        while (true) {
            if (isActual) {
                responseObserver.onNext(Mqtt.Lock.newBuilder().setId(0).build());
                List<Lock> locks = lockRepository.findAll();
                for (Lock lock : locks) {
                    responseObserver.onNext(next(lock));
                    System.out.println(lock);
                }
                isActual = false;
            }
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(isActual);
        }
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
        isActual = true;
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
