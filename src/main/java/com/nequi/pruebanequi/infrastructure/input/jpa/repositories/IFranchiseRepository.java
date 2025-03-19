package com.nequi.pruebanequi.infrastructure.input.jpa.repositories;

import com.nequi.pruebanequi.infrastructure.input.jpa.entities.FranchiseEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface IFranchiseRepository extends ReactiveCrudRepository<FranchiseEntity, Integer> {
    Mono<FranchiseEntity> findByName(String name);
}
