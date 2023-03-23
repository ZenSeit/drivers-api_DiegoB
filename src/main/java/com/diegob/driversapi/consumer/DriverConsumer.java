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

    @RabbitListener(queues = "cars-rented.queue")
    public void receiveEventCar(String message) throws JsonProcessingException {
        CarEvent event = objectMapper.readValue(message, CarEvent.class);
        addCarToDriverUseCase.addCar(event.getIdDriver(),event.getCarRented()).subscribe();
    }

}
