package com.nequi.pruebanequi.infrastructure.output.rest.controller;

import com.nequi.pruebanequi.application.dto.FranchiseBranchRequestDTO;
import com.nequi.pruebanequi.application.handler.impl.FranchiseBranchHandlerImpl;
import com.nequi.pruebanequi.domain.usecase.exceptions.BusinessException;
import com.nequi.pruebanequi.infrastructure.output.rest.constants.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "FranchiseBranch", description = "Endpoints para asociar franquicias a sucursales")
public class FranchiseBranchController {

    private final FranchiseBranchHandlerImpl franchiseBranchHandler;

    @PostMapping
    @Operation(
            summary = "Asociar franquicia a sucursal",
            description = "Este endpoint permite asociar una franquicia a una sucursal específica.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Franquicia asociada a sucursal exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Error de negocio, asociación fallida")
            }
    )
    public Mono<ResponseEntity<Void>> associateFranchiseToBranch(
            @RequestBody @Parameter(description = "Datos para asociar una franquicia a una sucursal") FranchiseBranchRequestDTO requestDto) {
        return franchiseBranchHandler.associateFranchiseToBranch(requestDto)
                .thenReturn(ResponseEntity.status(Constants.STATUS_CREATED).<Void>build())
                .onErrorResume(BusinessException.class,
                        e -> Mono.just(ResponseEntity.status(Constants.STATUS_BAD_REQUEST).<Void>build()));
    }
}
