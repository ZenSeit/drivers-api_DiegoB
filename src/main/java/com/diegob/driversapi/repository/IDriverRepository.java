package com.diegob.driversapi.repository;

import com.diegob.driversapi.domain.collection.Driver;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDriverRepository extends ReactiveMongoRepository<Driver,String> {
}
