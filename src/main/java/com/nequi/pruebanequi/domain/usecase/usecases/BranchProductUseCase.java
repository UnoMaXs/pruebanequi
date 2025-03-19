package com.nequi.pruebanequi.domain.usecase.usecases;

import com.nequi.pruebanequi.domain.model.api.IBranchProductServicePort;
import com.nequi.pruebanequi.domain.model.models.BranchProduct;
import com.nequi.pruebanequi.domain.model.spi.IBranchProductPersistencePort;
import com.nequi.pruebanequi.domain.usecase.validations.BranchProductBusinessValidations;
import reactor.core.publisher.Mono;

import java.util.List;

public class BranchProductUseCase implements IBranchProductServicePort {
    private final IBranchProductPersistencePort productBranchPersistencePort;
    private final BranchProductBusinessValidations branchProductBusinessValidations;

    public BranchProductUseCase(IBranchProductPersistencePort productBranchPersistencePort, BranchProductBusinessValidations branchProductBusinessValidations) {
        this.productBranchPersistencePort = productBranchPersistencePort;
        this.branchProductBusinessValidations = branchProductBusinessValidations;
    }

    @Override
    public Mono<BranchProduct> associateProductToBranch(BranchProduct branchProduct) {
        return branchProductBusinessValidations.validateProductBranch(branchProduct)
                .flatMap(productBranchPersistencePort::saveProductBranch);
    }

    @Override
    public Mono<List<BranchProduct>> getBranchProductByBranchId(Integer branchId) {
        return productBranchPersistencePort.findBranchProductByBranchId(branchId);
    }

}
