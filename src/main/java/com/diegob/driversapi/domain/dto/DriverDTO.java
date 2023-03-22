package com.diegob.driversapi.domain.dto;

import com.diegob.driversapi.domain.car.Car;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDTO {

    private String id;
    private String name;
    private String lastName;
    private int age;
    private String email;
    private List<Car> rentedCars =  new ArrayList<>();

}
