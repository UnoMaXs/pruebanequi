package com.nequi.pruebanequi.infrastructure.output.rest.controller;

import com.nequi.pruebanequi.application.dto.BranchCreatedResponseDto;
import com.nequi.pruebanequi.application.dto.BranchProductCreateRequestDto;
import com.nequi.pruebanequi.application.dto.BranchProductResponseDto;
import com.nequi.pruebanequi.application.dto.BranchProductUpdateRequestDto;
import com.nequi.pruebanequi.application.handler.impl.BranchHandlerImpl;
import com.nequi.pruebanequi.domain.usecase.exceptions.BusinessException;
import com.nequi.pruebanequi.infrastructure.output.rest.constants.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api" + Constants.PATH_BRANCH)
@RequiredArgsConstructor
@Tag(name = "Branch", description = "Endpoints para gestionar sucursales y productos")
public class BranchController {

    private final BranchHandlerImpl branchHandler;

    @PostMapping
    @Operation(
            summary = "Crear nueva sucursal",
            description = "Este endpoint permite crear una nueva sucursal con los productos asociados.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Sucursal creada exitosamente",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BranchCreatedResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Error de negocio, sucursal no creada")
            }
    )
    public Mono<ResponseEntity<BranchCreatedResponseDto>> createBranch(
            @RequestBody @Parameter(description = "Datos de la sucursal a crear") BranchProductCreateRequestDto requestDto) {
        return branchHandler.createBranch(requestDto)
                .map(response -> ResponseEntity.status(Constants.STATUS_CREATED).body(response))
                .onErrorResume(BusinessException.class,
                        e -> Mono.just(ResponseEntity.status(Constants.STATUS_BAD_REQUEST).body(
                                new BranchCreatedResponseDto(e.getMessage()))));
    }

    @PutMapping
    @Operation(
            summary = "Actualizar productos de la sucursal",
            description = "Este endpoint permite actualizar los productos asociados a una sucursal.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Productos de la sucursal actualizados exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Error de negocio, actualización fallida")
            }
    )
    public Mono<ResponseEntity<Void>> updateBranchProducts(
            @RequestBody @Parameter(description = "Datos de la sucursal y productos a actualizar") BranchProductUpdateRequestDto requestDto) {
        return branchHandler.updateBranchProducts(requestDto)
                .thenReturn(ResponseEntity.ok().<Void>build())
                .onErrorResume(BusinessException.class,
                        e -> Mono.just(ResponseEntity.status(Constants.STATUS_BAD_REQUEST).<Void>build()));
    }

    @GetMapping(Constants.PATH_DELETE_PATTERN + "/products")
    @Operation(
            summary = "Obtener productos por ID de sucursal",
            description = "Este endpoint permite obtener los productos asociados a una sucursal mediante su ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Productos de la sucursal obtenidos exitosamente",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BranchProductResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
            }
    )
    public Mono<ResponseEntity<BranchProductResponseDto>> getBranchProductsByBranchId(
            @PathVariable(Constants.PATH_DELETE_VARIABLE) @Parameter(description = "ID de la sucursal") Integer branchId) {
        return branchHandler.getBranchProductsByBranchId(branchId)
                .map(ResponseEntity::ok)
                .onErrorResume(BusinessException.class,
                        e -> Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).<BranchProductResponseDto>build()));
    }
}
