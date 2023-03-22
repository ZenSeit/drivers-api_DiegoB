package com.diegob.driversapi.usecases;

import com.diegob.driversapi.domain.collection.Driver;
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
class GetDriverByIdUseCaseTest {

    @Mock
    IDriverRepository repository;

    ModelMapper mapper;

    GetDriverByIdUseCase getDriverByIdUseCase;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        getDriverByIdUseCase = new GetDriverByIdUseCase(repository,mapper);
    }

    @Test
    void getDriverById(){

        var driver = Mono.just(new Driver(
                "Cristiano",
                "Rodando",
                38,
                "rodando@correo.com"
        ));

        Mockito.when(repository.findById(ArgumentMatchers.anyString())).thenReturn(driver);

        var response = getDriverByIdUseCase.apply("");

        StepVerifier.create(response)
                .expectNextCount(1)
                .verifyComplete();

        Mockito.verify(repository).findById("");
    }

    @Test
    @DisplayName("getDriverById_fail")
    void getCarByIdNotValid(){

        Mockito.when(repository.findById(ArgumentMatchers.anyString())).thenReturn(Mono.empty());

        var response = getDriverByIdUseCase.apply("");

        StepVerifier.create(response)
                .expectError(Throwable.class);

        Mockito.verify(repository).findById("");

    }

}