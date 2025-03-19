package com.nequi.pruebanequi.domain.model.spi;


import com.nequi.pruebanequi.domain.model.models.Franchise;
import reactor.core.publisher.Mono;

public interface IFranchisePersistencePort {
    Mono<Franchise> save(Franchise franchise);
    Mono<Void> deleteFranchiseById(Integer id);
}
