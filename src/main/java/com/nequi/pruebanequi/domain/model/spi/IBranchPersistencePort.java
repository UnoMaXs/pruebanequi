package com.nequi.pruebanequi.domain.model.spi;


import com.nequi.pruebanequi.domain.model.models.Branch;
import reactor.core.publisher.Mono;

public interface IBranchPersistencePort {
    Mono<Branch> save(Branch branch);
    Mono<Void> deleteBranchById(Integer id);
    Mono<Branch> getBranchById(Integer branchId);
}
