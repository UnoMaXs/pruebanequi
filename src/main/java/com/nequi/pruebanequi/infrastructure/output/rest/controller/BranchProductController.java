package com.nequi.pruebanequi.infrastructure.output.rest.controller;

import com.nequi.pruebanequi.application.dto.BranchProductRequestDTO;
import com.nequi.pruebanequi.application.handler.impl.BranchProductHandlerImpl;
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
@RequestMapping("/api" + Constants.PATH_BRANCH_PRODUCT)
@RequiredArgsConstructor
@Tag(name = "BranchProduct", description = "Endpoints para asociar productos a sucursales")
public class BranchProductController {

    private final BranchProductHandlerImpl branchProductHandler;

    @PostMapping
    @Operation(
            summary = "Asociar producto a sucursal",
            description = "Este endpoint permite asociar un producto a una sucursal.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Producto asociado a sucursal exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Error de negocio, asociación fallida")
            }
    )
    public Mono<ResponseEntity<Void>> associateBranchProduct(
            @RequestBody @Parameter(description = "Datos para asociar el producto a la sucursal") BranchProductRequestDTO requestDto) {
        return branchProductHandler.associateBranchProduct(requestDto)
                .thenReturn(ResponseEntity.status(Constants.STATUS_CREATED).<Void>build())
                .onErrorResume(BusinessException.class,
                        e -> Mono.just(ResponseEntity.status(Constants.STATUS_BAD_REQUEST).<Void>build()));
    }
}
