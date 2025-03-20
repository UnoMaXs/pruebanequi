package com.nequi.pruebanequi.infrastructure.output.rest.controller;

import com.nequi.pruebanequi.application.dto.FranchiseCreateRequestDTO;
import com.nequi.pruebanequi.application.dto.FranchiseCreatedResponseDTO;
import com.nequi.pruebanequi.application.handler.impl.FranchiseHandlerImpl;
import com.nequi.pruebanequi.domain.usecase.exceptions.BusinessException;
import com.nequi.pruebanequi.infrastructure.output.rest.constants.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@RequestMapping("/api" + Constants.PATH_FRANCHISE)
@RequiredArgsConstructor
@Tag(name = "Franchise", description = "Endpoints para gestionar franquicias")
public class FranchiseController {

    private final FranchiseHandlerImpl franchiseHandler;

    @PostMapping
    @Operation(
            summary = "Crear franquicia",
            description = "Este endpoint permite crear una nueva franquicia.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Franquicia creada exitosamente",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = FranchiseCreatedResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Error de negocio, franquicia no creada")
            }
    )
    public Mono<ResponseEntity<FranchiseCreatedResponseDTO>> createFranchise(
            @RequestBody @Parameter(description = "Datos necesarios para crear la franquicia") FranchiseCreateRequestDTO requestDto) {
        return franchiseHandler.createFranchise(requestDto)
                .map(response -> ResponseEntity.status(Constants.STATUS_CREATED).body(response))
                .onErrorResume(BusinessException.class,
                        e -> Mono.just(ResponseEntity.status(Constants.STATUS_BAD_REQUEST).body(
                                new FranchiseCreatedResponseDTO(e.getMessage()))));
    }
}
