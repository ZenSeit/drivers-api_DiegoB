package com.diegob.driversapi.usecases;

import com.diegob.driversapi.repository.IDriverRepository;
import com.diegob.driversapi.usecases.interfaces.DeleteDriver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class DeleteDriverUseCase implements DeleteDriver {

    private final IDriverRepository driverRepository;

    @Override
    public Mono<Void> delete(String id) {
        return driverRepository.findById(id)
                .switchIfEmpty(Mono.error(new Throwable("Driver not found")))
                .flatMap(driverRepository::delete);
    }
}
