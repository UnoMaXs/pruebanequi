package com.nequi.pruebanequi.domain.model.spi;


import com.nequi.pruebanequi.domain.model.models.FranchiseBranch;
import reactor.core.publisher.Mono;

public interface IFranchiseBranchPersistencePort {
    Mono<FranchiseBranch> saveFranchiseBranch(FranchiseBranch franchiseBranch);
}
