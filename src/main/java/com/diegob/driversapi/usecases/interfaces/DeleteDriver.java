package com.diegob.driversapi.usecases.interfaces;

import reactor.core.publisher.Mono;

@FunctionalInterface
public interface DeleteDriver {
    Mono<Void> delete(String id);
}
