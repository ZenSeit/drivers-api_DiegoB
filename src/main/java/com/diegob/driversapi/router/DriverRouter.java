package com.diegob.driversapi.router;

import com.diegob.driversapi.domain.dto.DriverDTO;
import com.diegob.driversapi.usecases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class DriverRouter {

    @Bean
    public RouterFunction<ServerResponse> getAllDrivers(GetAllDriversUseCase getAllDriversUseCase){
        return route(GET("/drivers"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllDriversUseCase.get(), DriverDTO.class))
                        .onErrorResume(throwable -> ServerResponse.noContent().build()));
    }

    @Bean
    public RouterFunction<ServerResponse> getDriverById(GetDriverByIdUseCase getDriverByIdUseCase){
        return route(GET("/drivers/{id}"),
                request -> getDriverByIdUseCase.apply(request.pathVariable("id"))
                        .flatMap(driverDTO -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(driverDTO))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage())));
    }

    @Bean
    public RouterFunction<ServerResponse> saveDriver(SaveDriverUseCase saveDriverUseCase){
        return route(POST("/drivers").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(DriverDTO.class)
                        .flatMap(driverDTO -> saveDriverUseCase.save(driverDTO)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))

                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).bodyValue(throwable.getMessage()))));
    }

    @Bean
    public RouterFunction<ServerResponse> updateDriver(UpdateDriverUseCase updateDriverUseCase){
        return route(PUT("/drivers/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(DriverDTO.class)
                        .flatMap(carDTO -> updateDriverUseCase.update(request.pathVariable("id"),carDTO)
                                .flatMap(result -> ServerResponse.status(200)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))

                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))));
    }

    @Bean
    public RouterFunction<ServerResponse> deleteDriver(DeleteDriverUseCase deleteDriverUseCase){
        return route(DELETE("/drivers/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request ->  deleteDriverUseCase.delete(request.pathVariable("id"))
                        .flatMap(result -> ServerResponse.noContent().build())
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage())));
    }

}
