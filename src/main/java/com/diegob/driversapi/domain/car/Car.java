package com.diegob.driversapi.domain.car;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Data
@ToString
public class Car {

    private String id;
    private String brand;
    private String color;
    private int model;
    private String licensePlate;
    private boolean isRented;
}
