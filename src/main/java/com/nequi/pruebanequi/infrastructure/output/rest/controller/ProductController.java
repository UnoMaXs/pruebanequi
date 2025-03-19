package com.nequi.pruebanequi.infrastructure.output.rest.controller;

import com.nequi.pruebanequi.application.dto.ProductRequestDTO;
import com.nequi.pruebanequi.application.handler.impl.ProductHandlerImpl;
import com.nequi.pruebanequi.domain.model.models.Product;
import com.nequi.pruebanequi.domain.usecase.exceptions.BusinessException;
import com.nequi.pruebanequi.infrastructure.output.rest.constants.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api" + Constants.PATH_PRODUCT)
@RequiredArgsConstructor
public class ProductController {

    private final ProductHandlerImpl productHandler;

    @PostMapping
    public Mono<ResponseEntity<Product>> createProduct(@RequestBody ProductRequestDTO requestDto) {
        return productHandler.createProduct(requestDto)
                .map(product -> ResponseEntity.status(Constants.STATUS_CREATED).body(product))
                .onErrorResume(BusinessException.class,
                        e -> Mono.just(ResponseEntity.status(Constants.STATUS_BAD_REQUEST).<Product>build()))
                .onErrorResume(RuntimeException.class,
                        e -> Mono.just(ResponseEntity.status(Constants.STATUS_INTERNAL_SERVER_ERROR).<Product>build()));
    }

    @DeleteMapping(Constants.PATH_DELETE_PATTERN)
    public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable(Constants.PATH_DELETE_VARIABLE) Integer productId) {
        return productHandler.deleteProduct(productId)
                .thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).<Void>build())
                .onErrorResume(BusinessException.class,
                        e -> Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).<Void>build()));
    }
}