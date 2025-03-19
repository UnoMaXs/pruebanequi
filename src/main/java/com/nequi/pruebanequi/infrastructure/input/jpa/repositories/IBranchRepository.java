package com.nequi.pruebanequi.infrastructure.input.jpa.repositories;

import com.nequi.pruebanequi.infrastructure.input.jpa.entities.BranchEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface IBranchRepository extends ReactiveCrudRepository<BranchEntity, Integer> {
    Mono<BranchEntity> findByName(String name);

}
