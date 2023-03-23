package com.diegob.driversapi.usecases;

import com.diegob.driversapi.domain.car.Car;
import com.diegob.driversapi.domain.collection.Driver;
import com.diegob.driversapi.domain.dto.DriverDTO;
import com.diegob.driversapi.repository.IDriverRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AddCarToDriverUseCaseTest {

    @Mock
    IDriverRepository repository;

    ModelMapper mapper;

    AddCarToDriverUseCase addCarToDriverUseCase;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        addCarToDriverUseCase = new AddCarToDriverUseCase(repository,mapper);
    }

    @Test
    void AddCarToDriver(){

        var car = new Car(
                "abcd4563",
                "Mazda",
                "White",
                2019,
                "CBD186",
                true
        );

        var driver = new Driver(
                "Diego",
                "Becerra",
                29,
                "diego@correo.com"
        );

        Mockito.when(repository.findById(ArgumentMatchers.anyString())).thenReturn(Mono.just(driver));

        Mockito.when(repository.save(ArgumentMatchers.any(Driver.class))).thenReturn(Mono.just(mapper.map(driver, Driver.class)));

        var response = addCarToDriverUseCase.addCar("",car);

        StepVerifier.create(response)
                .expectNextMatches(d -> d.getRentedCars().size()==1)
                .verifyComplete();

        Mockito.verify(repository).findById(ArgumentMatchers.anyString());
        Mockito.verify(repository).save(ArgumentMatchers.any(Driver.class));

    }

    @Test
    void AddCarToDriver_Fail(){

        var car = new Car(
                "abcd4563",
                "Mazda",
                "White",
                2019,
                "CBD186",
                true
        );

        Mockito.when(repository.findById(ArgumentMatchers.anyString())).thenReturn(Mono.empty());

        var response = addCarToDriverUseCase.addCar("",car);

        StepVerifier.create(response)
                .expectError(Throwable.class);

        Mockito.verify(repository).findById(ArgumentMatchers.anyString());

    }

}