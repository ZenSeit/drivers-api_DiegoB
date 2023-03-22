package com.diegob.driversapi.usecases;

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
class SaveDriverUseCaseTest {

    @Mock
    IDriverRepository repository;

    ModelMapper mapper;

    SaveDriverUseCase saveDriverUseCase;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        saveDriverUseCase = new SaveDriverUseCase(repository,mapper);
    }

    @Test
    void saveDriver(){

        var driver = new Driver(
                "Cristiano",
                "Rodando",
                38,
                "rodando@correo.com"
        );

        Mockito.when(repository.save(ArgumentMatchers.any(Driver.class))).thenReturn(Mono.just(driver));

        var response = saveDriverUseCase.save(mapper.map(driver, DriverDTO.class));

        StepVerifier.create(response)
                //.expectNext(mapper.map(driver, DriverDTO.class))
                .expectNextCount(1)
                .verifyComplete();

        Mockito.verify(repository).save(ArgumentMatchers.any(Driver.class));
    }

    @Test
    void saveDriver_Fail(){

        var driver = new Driver(
                "Cristiano",
                null,
                38,
                "rodando@correo.com"
        );

        Mockito.when(repository.save(ArgumentMatchers.any(Driver.class))).thenReturn(Mono.empty());

        var response = saveDriverUseCase.save(mapper.map(driver, DriverDTO.class));

        StepVerifier.create(response)
                .expectError(Throwable.class);

        Mockito.verify(repository).save(ArgumentMatchers.any(Driver.class));

    }

}