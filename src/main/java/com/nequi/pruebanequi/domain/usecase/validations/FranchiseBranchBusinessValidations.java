package com.nequi.pruebanequi.domain.usecase.validations;


import com.nequi.pruebanequi.domain.model.models.FranchiseBranch;
import com.nequi.pruebanequi.domain.usecase.exceptions.BusinessErrorMessage;
import com.nequi.pruebanequi.domain.usecase.exceptions.BusinessException;
import reactor.core.publisher.Mono;

public class FranchiseBranchBusinessValidations {
    public Mono<FranchiseBranch> validateFranchiseBranch(FranchiseBranch franchiseBranch) {
        if (franchiseBranch.getFranchiseId() == null || franchiseBranch.getBranchId() == null) {
            return Mono.error(new BusinessException(BusinessErrorMessage.MANDATORY_DATA));
        }
        return Mono.just(franchiseBranch);
    }
}
