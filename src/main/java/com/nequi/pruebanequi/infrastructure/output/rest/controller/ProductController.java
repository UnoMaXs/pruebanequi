package com.nequi.pruebanequi.infrastructure.output.rest.controller;

import com.nequi.pruebanequi.application.dto.ProductRequestDTO;
import com.nequi.pruebanequi.application.handler.impl.ProductHandlerImpl;
import com.nequi.pruebanequi.domain.model.models.Product;
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
@RequestMapping("/api" + Constants.PATH_PRODUCT)
@RequiredArgsConstructor
@Tag(name = "Product", description = "Endpoints para gestionar productos")
public class ProductController {

    private final ProductHandlerImpl productHandler;

    @PostMapping
    @Operation(
            summary = "Crear un producto",
            description = "Este endpoint permite crear un nuevo producto.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Producto creado exitosamente",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Product.class))),
                    @ApiResponse(responseCode = "400", description = "Error de negocio, producto no creado"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            }
    )
    public Mono<ResponseEntity<Product>> createProduct(
            @RequestBody @Parameter(description = "Datos necesarios para crear el producto") ProductRequestDTO requestDto) {
        return productHandler.createProduct(requestDto)
                .map(product -> ResponseEntity.status(Constants.STATUS_CREATED).body(product))
                .onErrorResume(BusinessException.class,
                        e -> Mono.just(ResponseEntity.status(Constants.STATUS_BAD_REQUEST).<Product>build()))
                .onErrorResume(RuntimeException.class,
                        e -> Mono.just(ResponseEntity.status(Constants.STATUS_INTERNAL_SERVER_ERROR).<Product>build()));
    }

    @DeleteMapping(Constants.PATH_DELETE_PATTERN)
    @Operation(
            summary = "Eliminar un producto",
            description = "Este endpoint permite eliminar un producto dado su ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
            }
    )
    public Mono<ResponseEntity<Void>> deleteProduct(
            @PathVariable(Constants.PATH_DELETE_VARIABLE) @Parameter(description = "ID del producto a eliminar") Integer productId) {
        return productHandler.deleteProduct(productId)
                .thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).<Void>build())
                .onErrorResume(BusinessException.class,
                        e -> Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).<Void>build()));
    }
}
