package com.nequi.pruebanequi.application.handler.impl;

import com.nequi.pruebanequi.application.handler.IBranchProductHandler;
import com.nequi.pruebanequi.domain.model.api.IBranchProductServicePort;
import com.nequi.pruebanequi.domain.model.models.BranchProduct;
import com.nequi.pruebanequi.domain.usecase.exceptions.BusinessErrorMessage;
import com.nequi.pruebanequi.domain.usecase.exceptions.BusinessException;
import com.nequi.pruebanequi.application.dto.BranchProductRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class BranchProductHandlerImpl implements IBranchProductHandler {

    private final IBranchProductServicePort branchProductServicePort;

    @Override
    public Mono<Void> associateBranchProduct(BranchProductRequestDTO branchProductRequestDTO) {
        if (branchProductRequestDTO == null) {
            return Mono.error(new BusinessException(BusinessErrorMessage.EMPTY_REQUEST_BODY));
        }

        Integer branchId = branchProductRequestDTO.getBranchId();
        List<Integer> productIds = branchProductRequestDTO.getProductIds();

        return Flux.fromIterable(productIds)
                .flatMap(productId -> {
                    BranchProduct branchProduct = new BranchProduct(null, branchId, productId);
                    return branchProductServicePort.associateProductToBranch(branchProduct);
                })
                .then();
    }
}