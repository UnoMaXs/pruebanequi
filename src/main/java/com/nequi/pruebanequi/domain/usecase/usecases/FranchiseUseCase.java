package com.nequi.pruebanequi.domain.usecase.usecases;


import com.nequi.pruebanequi.domain.model.api.IFranchiseServicePort;
import com.nequi.pruebanequi.domain.model.models.Franchise;
import com.nequi.pruebanequi.domain.model.spi.IFranchisePersistencePort;
import com.nequi.pruebanequi.domain.usecase.validations.FranchiseBusinessValidations;
import reactor.core.publisher.Mono;

public class FranchiseUseCase implements IFranchiseServicePort {
    private final IFranchisePersistencePort franchisePersistencePort;
    private final FranchiseBusinessValidations franchiseBusinessValidations;

    public FranchiseUseCase(IFranchisePersistencePort franchisePersistencePort, FranchiseBusinessValidations franchiseBusinessValidations) {
        this.franchisePersistencePort = franchisePersistencePort;
        this.franchiseBusinessValidations = franchiseBusinessValidations;
    }

    @Override
    public Mono<Franchise> createFranchise(Franchise franchise) {
        return franchiseBusinessValidations.validateBranch(franchise)
                .flatMap(franchisePersistencePort::save);
    }

    @Override
    public Mono<Void> deleteFranchiseById(Integer id) {
        return franchisePersistencePort.deleteFranchiseById(id);
    }

}
