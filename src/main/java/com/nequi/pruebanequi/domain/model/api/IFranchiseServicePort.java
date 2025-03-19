package com.nequi.pruebanequi.domain.model.api;


import com.nequi.pruebanequi.domain.model.models.Franchise;
import reactor.core.publisher.Mono;

public interface IFranchiseServicePort {
    Mono<Franchise> createFranchise(Franchise franchise);
    Mono<Void> deleteFranchiseById(Integer id);
}
