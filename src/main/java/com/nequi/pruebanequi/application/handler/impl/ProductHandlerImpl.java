package com.nequi.pruebanequi.application.handler.impl;

import com.nequi.pruebanequi.application.dto.ProductRequestDTO;
import com.nequi.pruebanequi.application.handler.IProductHandler;
import com.nequi.pruebanequi.domain.model.api.IProductServicePort;
import com.nequi.pruebanequi.domain.model.models.Product;
import com.nequi.pruebanequi.domain.usecase.exceptions.BusinessErrorMessage;
import com.nequi.pruebanequi.domain.usecase.exceptions.BusinessException;
import com.nequi.pruebanequi.infrastructure.output.rest.mappers.IProductRequestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductHandlerImpl implements IProductHandler {
    private final IProductServicePort productServicePort;
    private final IProductRequestMapper productRequestMapper;

    @Override
    public Mono<Product> createProduct(ProductRequestDTO productRequestDTO) {
        if (productRequestDTO == null) {
            return Mono.error(new BusinessException(BusinessErrorMessage.EMPTY_REQUEST_BODY));
        }

        return Mono.just(productRequestDTO)
                .map(productRequestMapper::toDomain)
                .flatMap(productServicePort::createProduct);
    }

    @Override
    public Mono<Void> deleteProduct(Integer integer) {
        return productServicePort.deleteProductById(integer);
    }
}