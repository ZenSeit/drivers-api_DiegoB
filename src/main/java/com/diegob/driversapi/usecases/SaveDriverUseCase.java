package com.diegob.driversapi.usecases;

import com.diegob.driversapi.domain.collection.Driver;
import com.diegob.driversapi.domain.dto.DriverDTO;
import com.diegob.driversapi.repository.IDriverRepository;
import com.diegob.driversapi.usecases.interfaces.SaveDriver;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class SaveDriverUseCase implements SaveDriver {

    private final IDriverRepository driverRepository;

    private final ModelMapper mapper;
    @Override
    public Mono<DriverDTO> save(DriverDTO driverDTO) {
        return driverRepository.save(mapper.map(driverDTO, Driver.class))
                .switchIfEmpty(Mono.empty())
                .map(driver -> mapper.map(driver, DriverDTO.class))
                .onErrorResume(Mono::error);
    }
}
