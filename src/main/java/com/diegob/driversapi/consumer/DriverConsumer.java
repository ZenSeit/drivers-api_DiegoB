package com.diegob.driversapi.consumer;

import com.diegob.driversapi.config.RabbitConfig;
import com.diegob.driversapi.usecases.AddCarToDriverUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DriverConsumer {

    private final AddCarToDriverUseCase addCarToDriverUseCase;
    private final ObjectMapper objectMapper;

    //@RabbitListener(queues = "cars-rented.queue")
    public void receiveEventCar(String message) throws JsonProcessingException {
        CarEvent event = objectMapper.readValue(message, CarEvent.class);
        addCarToDriverUseCase.addCar(event.getIdDriver(),event.getCarRented()).subscribe();
    }

    @RabbitListener(queues = "cars-rented.queue.direct")
    public void receiveMessageFromDirect(String message) throws JsonProcessingException {
        CarEvent event = objectMapper.readValue(message, CarEvent.class);
        System.out.println("Direct: " + event.getEventType()+" Message received");
    }

    @RabbitListener(queues = {"cars-rented.queue.topic.one","cars-rented.queue.topic.two","cars-rented.queue.topic.three"})
    public void receiveMessageFromTopic(String message) throws JsonProcessingException {
        CarEvent event = objectMapper.readValue(message, CarEvent.class);
        System.out.println("Topic: " + event.getEventType()+" Message received");
    }

    @RabbitListener(queues = {"cars-rented.queue.fanout.one","cars-rented.queue.fanout.two","cars-rented.queue.fanout.three"})
    public void receiveMessageFromFanout(String message) throws JsonProcessingException {
        CarEvent event = objectMapper.readValue(message, CarEvent.class);
        System.out.println("Fanout: " + event.getEventType()+" Message received");
    }

}
