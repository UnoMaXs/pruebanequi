package com.nequi.pruebanequi.infrastructure.output.rest.controller;

import com.nequi.pruebanequi.application.dto.BranchCreatedResponseDto;
import com.nequi.pruebanequi.application.dto.BranchProductCreateRequestDto;
import com.nequi.pruebanequi.application.dto.BranchProductResponseDto;
import com.nequi.pruebanequi.application.dto.BranchProductUpdateRequestDto;
import com.nequi.pruebanequi.application.handler.impl.BranchHandlerImpl;
import com.nequi.pruebanequi.domain.usecase.exceptions.BusinessException;
import com.nequi.pruebanequi.infrastructure.output.rest.constants.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api" + Constants.PATH_BRANCH)
@RequiredArgsConstructor
public class BranchController {

    private final BranchHandlerImpl branchHandler;

    @PostMapping
    public Mono<ResponseEntity<BranchCreatedResponseDto>> createBranch(@RequestBody BranchProductCreateRequestDto requestDto) {
        return branchHandler.createBranch(requestDto)
                .map(response -> ResponseEntity.status(Constants.STATUS_CREATED).body(response))
                .onErrorResume(BusinessException.class,
                        e -> Mono.just(ResponseEntity.status(Constants.STATUS_BAD_REQUEST).body(
                                new BranchCreatedResponseDto(e.getMessage()))));
    }

    @PutMapping
    public Mono<ResponseEntity<Void>> updateBranchProducts(@RequestBody BranchProductUpdateRequestDto requestDto) {
        return branchHandler.updateBranchProducts(requestDto)
                .thenReturn(ResponseEntity.ok().<Void>build())
                .onErrorResume(BusinessException.class,
                        e -> Mono.just(ResponseEntity.status(Constants.STATUS_BAD_REQUEST).<Void>build()));
    }

    @GetMapping(Constants.PATH_DELETE_PATTERN + "/products")
    public Mono<ResponseEntity<BranchProductResponseDto>> getBranchProductsByBranchId(@PathVariable(Constants.PATH_DELETE_VARIABLE) Integer branchId) {
        return branchHandler.getBranchProductsByBranchId(branchId)
                .map(ResponseEntity::ok)
                .onErrorResume(BusinessException.class,
                        e -> Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).<BranchProductResponseDto>build()));
    }
}
