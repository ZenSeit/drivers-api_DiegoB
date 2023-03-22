package com.diegob.driversapi.usecases;

import com.diegob.driversapi.domain.dto.DriverDTO;
import com.diegob.driversapi.repository.IDriverRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Service
@AllArgsConstructor
public class GetAllDriversUseCase implements Supplier<Flux<DriverDTO>> {

    private final IDriverRepository driverRepository;

    private final ModelMapper mapper;

    @Override
    public Flux<DriverDTO> get() {
        return driverRepository.findAll()
                .switchIfEmpty(Flux.empty())
                .map(driver -> mapper.map(driver, DriverDTO.class));
    }
}
