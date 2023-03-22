package com.diegob.driversapi.usecases;

import com.diegob.driversapi.domain.collection.Driver;
import com.diegob.driversapi.domain.dto.DriverDTO;
import com.diegob.driversapi.repository.IDriverRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
class UpdateDriverUseCaseTest {

    @Mock
    IDriverRepository repository;

    ModelMapper mapper;

    UpdateDriverUseCase updateDriverUseCase;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        updateDriverUseCase = new UpdateDriverUseCase(repository,mapper);
    }

    @Test
    void updateDriver(){

        var driver = new Driver(
                "Cristiano",
                "Rodando",
                38,
                "rodando@correo.com"
        );

        var driverUpdated = new Driver(
                "Cristiano",
                "Ronaldo",
                38,
                "ronaldo@correo.com"
        );

        driverUpdated.setId(driver.getId());

        Mockito.when(repository.findById(ArgumentMatchers.anyString())).thenReturn(Mono.just(driver));

        Mockito.when(repository.save(ArgumentMatchers.any(Driver.class))).thenReturn(Mono.just(mapper.map(driverUpdated, Driver.class)));

        var response = updateDriverUseCase.update("",mapper.map(driver, DriverDTO.class));

        StepVerifier.create(response)
                .expectNext(mapper.map(driverUpdated, DriverDTO.class))
                .expectNextCount(0)
                .verifyComplete();

        Mockito.verify(repository).findById(ArgumentMatchers.anyString());
        Mockito.verify(repository).save(ArgumentMatchers.any(Driver.class));

    }

    @Test
    @DisplayName("updateDriverInvalidId_Fail")
    void updateCarInvalidId_Fail() {

        var driver = new Driver(
                "Cristiano",
                "Rodando",
                38,
                "rodando@correo.com"
        );

        Mockito.when(repository.findById(ArgumentMatchers.anyString())).thenReturn(Mono.empty());

        var response = updateDriverUseCase.update("",mapper.map(driver, DriverDTO.class));

        StepVerifier.create(response)
                .expectError(Throwable.class);

        Mockito.verify(repository).findById(ArgumentMatchers.anyString());

    }


}