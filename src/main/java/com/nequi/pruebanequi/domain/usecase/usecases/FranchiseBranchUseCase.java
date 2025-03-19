package com.nequi.pruebanequi.domain.usecase.usecases;


import com.nequi.pruebanequi.domain.model.api.IFranchiseBranchServicePort;
import com.nequi.pruebanequi.domain.model.models.FranchiseBranch;
import com.nequi.pruebanequi.domain.model.spi.IFranchiseBranchPersistencePort;
import com.nequi.pruebanequi.domain.usecase.validations.FranchiseBranchBusinessValidations;
import reactor.core.publisher.Mono;

public class FranchiseBranchUseCase implements IFranchiseBranchServicePort {
    private final IFranchiseBranchPersistencePort franchiseBranchPersistencePort;
    private final FranchiseBranchBusinessValidations franchiseBranchBusinessValidations;

    public FranchiseBranchUseCase(IFranchiseBranchPersistencePort franchiseBranchPersistencePort, FranchiseBranchBusinessValidations franchiseBranchBusinessValidations) {
        this.franchiseBranchPersistencePort = franchiseBranchPersistencePort;
        this.franchiseBranchBusinessValidations = franchiseBranchBusinessValidations;
    }

    @Override
    public Mono<Void> associateBranchToFranchise(FranchiseBranch franchiseBranch) {
        return franchiseBranchBusinessValidations.validateFranchiseBranch(franchiseBranch)
                .flatMap(franchiseBranchPersistencePort::saveFranchiseBranch)
                .then();
    }
}
