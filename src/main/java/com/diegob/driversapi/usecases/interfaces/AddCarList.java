package com.diegob.driversapi.usecases.interfaces;

import com.diegob.driversapi.domain.car.Car;
import com.diegob.driversapi.domain.dto.DriverDTO;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface AddCarList {

    Mono<DriverDTO> addCar(String idDriver, Car car);
}
