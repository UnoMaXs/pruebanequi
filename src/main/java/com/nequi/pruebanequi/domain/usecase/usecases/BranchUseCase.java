package com.nequi.pruebanequi.domain.usecase.usecases;


import com.nequi.pruebanequi.domain.model.api.IBranchServicePort;
import com.nequi.pruebanequi.domain.model.models.Branch;
import com.nequi.pruebanequi.domain.model.spi.IBranchPersistencePort;
import com.nequi.pruebanequi.domain.usecase.validations.BranchBusinessValidations;
import reactor.core.publisher.Mono;

public class BranchUseCase implements IBranchServicePort {
    private final IBranchPersistencePort branchPersistencePort;
    private final BranchBusinessValidations branchBusinessValidations;

    public BranchUseCase(IBranchPersistencePort branchPersistencePort, BranchBusinessValidations branchBusinessValidations) {
        this.branchPersistencePort = branchPersistencePort;
        this.branchBusinessValidations = branchBusinessValidations;
    }

    @Override
    public Mono<Branch> createBranch(Branch branch) {
        return branchBusinessValidations.validateBranch(branch)
                .flatMap(branchPersistencePort::save);
    }

    @Override
    public Mono<Void> deleteBranchById(Integer id) {
        return branchPersistencePort.deleteBranchById(id);
    }

    @Override
    public Mono<Branch> getBranchById(Integer branchId) {
        return branchPersistencePort.getBranchById(branchId);
    }
}
