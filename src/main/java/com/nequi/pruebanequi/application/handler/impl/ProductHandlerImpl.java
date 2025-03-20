package com.nequi.pruebanequi.application.handler.impl;


import com.nequi.pruebanequi.application.dto.ProductRequestDTO;
import com.nequi.pruebanequi.application.handler.IProductHandler;
import com.nequi.pruebanequi.domain.model.api.IProductServicePort;
import com.nequi.pruebanequi.domain.usecase.exceptions.BusinessErrorMessage;
import com.nequi.pruebanequi.domain.usecase.exceptions.BusinessException;
import com.nequi.pruebanequi.infrastructure.output.rest.mappers.IProductRequestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static com.nequi.pruebanequi.infrastructure.output.rest.constants.Constants.MESSAGE_INTERNAL_SERVER_ERROR;
import static com.nequi.pruebanequi.infrastructure.output.rest.constants.Constants.PATH_DELETE_VARIABLE;
import static com.nequi.pruebanequi.infrastructure.output.rest.constants.Constants.STATUS_BAD_REQUEST;
import static com.nequi.pruebanequi.infrastructure.output.rest.constants.Constants.STATUS_CREATED;
import static com.nequi.pruebanequi.infrastructure.output.rest.constants.Constants.STATUS_INTERNAL_SERVER_ERROR;
import static org.springframework.boot.actuate.endpoint.web.WebEndpointResponse.STATUS_NOT_FOUND;
import static org.springframework.boot.actuate.endpoint.web.WebEndpointResponse.STATUS_NO_CONTENT;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductHandlerImpl implements IProductHandler {
    private final IProductServicePort productServicePort;
    private final IProductRequestMapper productRequestMapper;
    @Override
    public Mono<ServerResponse> createProduct(ServerRequest request) {
        return request.bodyToMono(ProductRequestDTO.class)
                .switchIfEmpty(Mono.error(new BusinessException(BusinessErrorMessage.EMPTY_REQUEST_BODY)))
                .flatMap(productRequest -> {
                    if (productRequest == null) {
                        return Mono.error(new BusinessException(BusinessErrorMessage.EMPTY_REQUEST_BODY));
                    }
                    return Mono.just(productRequest);
                })
                .map(productRequestMapper::toDomain)
                .flatMap(productServicePort::createProduct)
                .flatMap(result -> ServerResponse
                        .status(STATUS_CREATED)
                        .bodyValue(result))
                .onErrorResume(BusinessException.class, e ->
                        ServerResponse
                                .status(STATUS_BAD_REQUEST)
                                .bodyValue(e.getMessage()))
                .onErrorResume(RuntimeException.class, e ->
                ServerResponse
                        .status(STATUS_INTERNAL_SERVER_ERROR)
                        .bodyValue(MESSAGE_INTERNAL_SERVER_ERROR));
    }

    @Override
    public Mono<ServerResponse> deleteProduct(ServerRequest request) {
        Integer productId = Integer.valueOf(request.pathVariable(PATH_DELETE_VARIABLE));

        return productServicePort.deleteProductById(productId)
                .then(ServerResponse
                        .status(STATUS_NO_CONTENT)
                        .build())
                .onErrorResume(BusinessException.class, e ->
                        ServerResponse
                                .status(STATUS_NOT_FOUND)
                                .bodyValue(e.getMessage()));
    }

}
