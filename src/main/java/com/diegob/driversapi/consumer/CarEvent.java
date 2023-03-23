package com.diegob.driversapi.consumer;

import com.diegob.driversapi.domain.car.Car;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class CarEvent {

    private String idDriver;
    private Car carRented;
    private String eventType;
}
