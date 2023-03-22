package com.diegob.driversapi.usecases.interfaces;

import com.diegob.driversapi.domain.dto.DriverDTO;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface UpdateDriver {

    Mono<DriverDTO> update(String id, DriverDTO driverDTO);
}
