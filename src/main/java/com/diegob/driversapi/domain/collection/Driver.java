package com.diegob.driversapi.domain.collection;

import com.diegob.driversapi.domain.car.Car;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Document(collection = "drivers")
public class Driver {

    @Id
    private String id = UUID.randomUUID().toString().substring(0, 10);
    @NotNull
    private String name;
    @NotNull
    private String lastName;
    private int age;
    @NotNull
    private String email;
    private List<Car> rentedCars = new ArrayList<>();

    public Driver(String name, String lastName, int age, String email) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
    }

    public Driver prueba(Car car){
        this.rentedCars.add(car);
        return this;
    }
}
