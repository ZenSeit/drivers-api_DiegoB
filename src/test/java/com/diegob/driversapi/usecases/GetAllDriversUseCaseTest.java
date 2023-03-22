package com.diegob.driversapi.usecases;

import com.diegob.driversapi.domain.collection.Driver;
import com.diegob.driversapi.repository.IDriverRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GetAllDriversUseCaseTest {

    @Mock
    IDriverRepository repository;

    ModelMapper mapper;

    GetAllDriversUseCase getAllDriversUseCase;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        getAllDriversUseCase = new GetAllDriversUseCase(repository,mapper);
    }

    @Test
    void getAllDrivers(){

        var driver1 = new Driver(
                "Diego",
                "Maradona",
                59,
                "diemar@correo.com"
        );

        var driver2 = new Driver(
                "Cristiano",
                "Rodando",
                38,
                "rodando@correo.com"
        );


        var fluxDrivers = Flux.just(driver1, driver2);

        Mockito.when(repository.findAll()).thenReturn(fluxDrivers);

        var response = getAllDriversUseCase.get();

        StepVerifier.create(response)
                .expectNextCount(2)
                .verifyComplete();

        Mockito.verify(repository).findAll();

    }

}