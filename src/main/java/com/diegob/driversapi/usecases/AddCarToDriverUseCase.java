package com.diegob.driversapi.usecases;

import com.diegob.driversapi.domain.car.Car;
import com.diegob.driversapi.domain.collection.Driver;
import com.diegob.driversapi.domain.dto.DriverDTO;
import com.diegob.driversapi.repository.IDriverRepository;
import com.diegob.driversapi.usecases.interfaces.AddCarList;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@AllArgsConstructor
public class AddCarToDriverUseCase implements AddCarList {

    private final IDriverRepository driverRepository;

    private final ModelMapper mapper;

    @Override
    public Mono<DriverDTO> addCar(String idDriver, Car car) {

        System.out.println(idDriver);
        System.out.println(car);

        return driverRepository
                .findById(idDriver)
                .switchIfEmpty(Mono.error(new Throwable("Driver not found")))
                .flatMap(driver -> {
                    var listOfCars = driver.getRentedCars();
                    listOfCars.add(car);
                    driver.setRentedCars(listOfCars);
                    return driverRepository.save(driver);
                })
                .map(driver -> mapper.map(driver, DriverDTO.class));
    }
}
