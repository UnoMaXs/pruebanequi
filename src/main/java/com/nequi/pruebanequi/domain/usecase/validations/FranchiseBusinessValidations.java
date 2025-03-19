package com.nequi.pruebanequi.domain.usecase.validations;


import com.nequi.pruebanequi.domain.model.models.Franchise;
import com.nequi.pruebanequi.domain.usecase.exceptions.BusinessErrorMessage;
import com.nequi.pruebanequi.domain.usecase.exceptions.BusinessException;
import reactor.core.publisher.Mono;

public class FranchiseBusinessValidations {
    public Mono<Franchise> validateBranch(Franchise franchise) {
        return validateName(franchise)
                .then(Mono.just(franchise));
    }

    private Mono<Void> validateName(Franchise franchise) {
        if (franchise.getName().isEmpty()) {
            return Mono.error(new BusinessException(BusinessErrorMessage.MANDATORY_NAME));
        }
        return Mono.empty();
    }
}
