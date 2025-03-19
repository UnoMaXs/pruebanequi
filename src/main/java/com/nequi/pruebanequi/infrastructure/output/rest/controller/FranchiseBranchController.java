package com.nequi.pruebanequi.infrastructure.output.rest.controller;

import com.nequi.pruebanequi.application.dto.FranchiseBranchRequestDTO;
import com.nequi.pruebanequi.application.handler.impl.FranchiseBranchHandlerImpl;
import com.nequi.pruebanequi.domain.usecase.exceptions.BusinessException;
import com.nequi.pruebanequi.infrastructure.output.rest.constants.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api" + Constants.PATH_FRANCHISE_BRANCH_PRODUCT)
@RequiredArgsConstructor
public class FranchiseBranchController {

    private final FranchiseBranchHandlerImpl franchiseBranchHandler;

    @PostMapping
    public Mono<ResponseEntity<Void>> associateFranchiseToBranch(@RequestBody FranchiseBranchRequestDTO requestDto) {
        return franchiseBranchHandler.associateFranchiseToBranch(requestDto)
                .thenReturn(ResponseEntity.status(Constants.STATUS_CREATED).<Void>build())
                .onErrorResume(BusinessException.class,
                        e -> Mono.just(ResponseEntity.status(Constants.STATUS_BAD_REQUEST).<Void>build()));
    }
}