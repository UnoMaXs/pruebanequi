package com.nequi.pruebanequi.infrastructure.output.rest.controller;

import com.nequi.pruebanequi.application.dto.FranchiseCreateRequestDTO;
import com.nequi.pruebanequi.application.dto.FranchiseCreatedResponseDTO;
import com.nequi.pruebanequi.application.handler.impl.FranchiseHandlerImpl;
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
@RequestMapping("/api" + Constants.PATH_FRANCHISE)
@RequiredArgsConstructor
public class FranchiseController {

    private final FranchiseHandlerImpl franchiseHandler;

    @PostMapping
    public Mono<ResponseEntity<FranchiseCreatedResponseDTO>> createFranchise(@RequestBody FranchiseCreateRequestDTO requestDto) {
        return franchiseHandler.createFranchise(requestDto)
                .map(response -> ResponseEntity.status(Constants.STATUS_CREATED).body(response))
                .onErrorResume(BusinessException.class,
                        e -> Mono.just(ResponseEntity.status(Constants.STATUS_BAD_REQUEST).body(
                                new FranchiseCreatedResponseDTO(e.getMessage()))));
    }
}