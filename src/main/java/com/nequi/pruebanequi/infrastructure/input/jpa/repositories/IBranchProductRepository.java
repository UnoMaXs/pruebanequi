package com.nequi.pruebanequi.infrastructure.input.jpa.repositories;


import com.nequi.pruebanequi.infrastructure.input.jpa.entities.BranchProductEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface IBranchProductRepository extends ReactiveCrudRepository<BranchProductEntity, Integer> {
    Flux<BranchProductEntity> findByBranchId(Integer branchId);
}
