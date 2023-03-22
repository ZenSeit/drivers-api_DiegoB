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
class DeleteDriverUseCaseTest {

    @Mock
    IDriverRepository repository;

    ModelMapper mapper;

    DeleteDriverUseCase deleteDriverUseCase;

    @BeforeEach
    void init(){
        deleteDriverUseCase = new DeleteDriverUseCase(repository);
    }

    @Test
    void deleteDriver(){

        var driver = new Driver(
                "Cristiano",
                "Rodando",
                38,
                "rodando@correo.com"
        );

        Mockito.when(repository.findById(ArgumentMatchers.anyString())).thenReturn(Mono.just(driver));

        Mockito.when(repository.delete(ArgumentMatchers.any(Driver.class))).thenReturn(Mono.empty());

        var response = deleteDriverUseCase.delete("");

        StepVerifier.create(response)
                .expectNextCount(0)
                .verifyComplete();

        Mockito.verify(repository).delete(ArgumentMatchers.any(Driver.class));

    }

    @Test
    @DisplayName("deleteCar_Fail")
    void deleteCar_Fail(){

        Mockito.when(repository.findById(ArgumentMatchers.anyString())).thenReturn(Mono.empty());

        var response = deleteDriverUseCase.delete("");

        StepVerifier.create(response)
                .expectError(Throwable.class);

        Mockito.verify(repository).findById("");

    }

}