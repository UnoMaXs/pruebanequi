package com.nequi.pruebanequi.domain.usecase.validations;


import com.nequi.pruebanequi.domain.model.models.Branch;
import com.nequi.pruebanequi.domain.usecase.exceptions.BusinessErrorMessage;
import com.nequi.pruebanequi.domain.usecase.exceptions.BusinessException;
import reactor.core.publisher.Mono;

public class BranchBusinessValidations {
    public Mono<Branch> validateBranch(Branch branch) {
        return validateName(branch)
                .then(Mono.just(branch));
    }

    private Mono<Void> validateName(Branch branch) {
        if (branch.getName().isEmpty()) {
            return Mono.error(new BusinessException(BusinessErrorMessage.MANDATORY_NAME));
        }
        return Mono.empty();
    }
}
