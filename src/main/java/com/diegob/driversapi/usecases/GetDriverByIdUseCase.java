package com.diegob.driversapi.usecases;

import com.diegob.driversapi.domain.dto.DriverDTO;
import com.diegob.driversapi.repository.IDriverRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class GetDriverByIdUseCase implements Function<String, Mono<DriverDTO>> {

    private final IDriverRepository driverRepository;

    private final ModelMapper mapper;

    @Override
    public Mono<DriverDTO> apply(String id) {
        return driverRepository.findById(id)
                .switchIfEmpty(Mono.error(new Throwable("Driver not found")))
                .map(driver -> mapper.map(driver, DriverDTO.class))
                .onErrorResume(Mono::error);
    }
}
