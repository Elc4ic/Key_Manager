package org.example.mqtt_server.Service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.example.mqtt_server.entity.Lock;
import org.example.mqtt_server.Repository.LockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import service.Mqtt;

import java.util.List;
import java.util.concurrent.TimeUnit;


@GrpcService
public class LockServiceGrpc extends service.LockServiceGrpc.LockServiceImplBase {

    private final LockRepository lockRepository;

    public LockServiceGrpc(LockRepository lockRepository) {
        this.lockRepository = lockRepository;
    }

    @Autowired
    private MqttService mqttService;

    @Value("${mqtt.actual}")
    private boolean isActual;


    @Override
    public void getLockList(Mqtt.Empty request, StreamObserver<Mqtt.Lock> responseObserver) {
        isActual = true;
        int i =0;
        while (i<20) {
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
            i++;
        }
        System.out.println("End");
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
