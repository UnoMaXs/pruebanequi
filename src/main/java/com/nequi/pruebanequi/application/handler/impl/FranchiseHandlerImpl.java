package com.nequi.pruebanequi.application.handler.impl;

import com.nequi.pruebanequi.application.handler.IFranchiseHandler;
import com.nequi.pruebanequi.domain.model.api.IFranchiseBranchServicePort;
import com.nequi.pruebanequi.domain.model.api.IFranchiseServicePort;
import com.nequi.pruebanequi.domain.model.models.Franchise;
import com.nequi.pruebanequi.domain.model.models.FranchiseBranch;
import com.nequi.pruebanequi.domain.usecase.exceptions.BusinessErrorMessage;
import com.nequi.pruebanequi.domain.usecase.exceptions.BusinessException;
import com.nequi.pruebanequi.application.dto.FranchiseBranchRequestDTO;
import com.nequi.pruebanequi.application.dto.FranchiseCreateRequestDTO;
import com.nequi.pruebanequi.application.dto.FranchiseCreatedResponseDTO;
import com.nequi.pruebanequi.infrastructure.output.rest.mappers.IFranchiseBranchRequestMapper;
import com.nequi.pruebanequi.infrastructure.output.rest.mappers.IFranchiseRequestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class FranchiseHandlerImpl implements IFranchiseHandler {
    private final IFranchiseServicePort franchiseServicePort;
    private final IFranchiseRequestMapper franchiseRequestMapper;
    private final IFranchiseBranchServicePort franchiseBranchServicePort;
    private final IFranchiseBranchRequestMapper franchiseBranchRequestMapper;

    @Override
    public Mono<FranchiseCreatedResponseDTO> createFranchise(FranchiseCreateRequestDTO franchiseRequest) {
        if (franchiseRequest == null) {
            return Mono.error(new BusinessException(BusinessErrorMessage.EMPTY_REQUEST_BODY));
        }

        Franchise franchise = franchiseRequestMapper.toDomain(franchiseRequest);
        List<Integer> branchIds = franchiseRequest.getBranchIds();

        return franchiseServicePort.createFranchise(franchise)
                .flatMap(savedFranchise -> {
                    FranchiseBranchRequestDTO franchiseBranchRequestDto = new FranchiseBranchRequestDTO();
                    franchiseBranchRequestDto.setFranchiseId(savedFranchise.getId());
                    franchiseBranchRequestDto.setBranchIds(branchIds);

                    return associateBranchesWithFranchise(franchiseBranchRequestDto)
                            .thenReturn(new FranchiseCreatedResponseDTO(savedFranchise.getName()))
                            .onErrorResume(associationError ->
                                    franchiseServicePort.deleteFranchiseById(savedFranchise.getId())
                                            .then(Mono.error(new BusinessException(BusinessErrorMessage.ASSOCIATE_FRANCHISE_BRANCH_ERROR)))
                            );
                });
    }

    private Mono<Void> associateBranchesWithFranchise(FranchiseBranchRequestDTO franchiseBranchRequestDto) {
        List<FranchiseBranch> franchiseBranches = franchiseBranchRequestMapper.toDomain(franchiseBranchRequestDto);
        return Flux.fromIterable(franchiseBranches)
                .flatMap(franchiseBranchServicePort::associateBranchToFranchise)
                .then();
    }
}