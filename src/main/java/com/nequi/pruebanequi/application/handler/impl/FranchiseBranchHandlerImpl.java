package com.nequi.pruebanequi.application.handler.impl;

import com.nequi.pruebanequi.application.handler.IFranchiseBranchHandler;
import com.nequi.pruebanequi.domain.model.api.IFranchiseBranchServicePort;
import com.nequi.pruebanequi.domain.model.models.FranchiseBranch;
import com.nequi.pruebanequi.domain.usecase.exceptions.BusinessErrorMessage;
import com.nequi.pruebanequi.domain.usecase.exceptions.BusinessException;
import com.nequi.pruebanequi.application.dto.FranchiseBranchRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class FranchiseBranchHandlerImpl implements IFranchiseBranchHandler {
    private final IFranchiseBranchServicePort franchiseBranchServicePort;

    @Override
    public Mono<Void> associateFranchiseToBranch(FranchiseBranchRequestDTO franchiseBranchRequestDTO) {
        if (franchiseBranchRequestDTO == null) {
            return Mono.error(new BusinessException(BusinessErrorMessage.EMPTY_REQUEST_BODY));
        }

        Integer franchiseId = franchiseBranchRequestDTO.getFranchiseId();
        List<Integer> branchIds = franchiseBranchRequestDTO.getBranchIds();

        return Flux.fromIterable(branchIds)
                .flatMap(branchId -> {
                    FranchiseBranch franchiseBranch = new FranchiseBranch(null, franchiseId, branchId);
                    return franchiseBranchServicePort.associateBranchToFranchise(franchiseBranch);
                })
                .then();
    }
}