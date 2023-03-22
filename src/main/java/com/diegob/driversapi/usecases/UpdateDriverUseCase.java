package com.diegob.driversapi.usecases;

import com.diegob.driversapi.domain.collection.Driver;
import com.diegob.driversapi.domain.dto.DriverDTO;
import com.diegob.driversapi.repository.IDriverRepository;
import com.diegob.driversapi.usecases.interfaces.UpdateDriver;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UpdateDriverUseCase implements UpdateDriver {

    private final IDriverRepository driverRepository;

    private final ModelMapper mapper;
    @Override
    public Mono<DriverDTO> update(String id, DriverDTO driverDTO) {
        return driverRepository.findById(id)
                .switchIfEmpty(Mono.error(new Throwable("Driver not found")))
                .flatMap(driver-> {
                    driverDTO.setId(driver.getId());
                    return driverRepository.save(mapper.map(driverDTO, Driver.class))
                            .map(s ->mapper.map(s, DriverDTO.class));
                })
                .onErrorResume(Mono::error);
    }
}
