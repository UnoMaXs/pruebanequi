package com.nequi.pruebanequi.domain.usecase.validations;

import com.nequi.pruebanequi.domain.model.models.BranchProduct;
import com.nequi.pruebanequi.domain.usecase.exceptions.BusinessErrorMessage;
import com.nequi.pruebanequi.domain.usecase.exceptions.BusinessException;
import reactor.core.publisher.Mono;

public class BranchProductBusinessValidations {
    public Mono<BranchProduct> validateProductBranch(BranchProduct branchProduct) {
        if (branchProduct.getProductId() == null || branchProduct.getBranchId() == null) {
            return Mono.error(new BusinessException(BusinessErrorMessage.MANDATORY_DATA));
        }
        return Mono.just(branchProduct);
    }
}
