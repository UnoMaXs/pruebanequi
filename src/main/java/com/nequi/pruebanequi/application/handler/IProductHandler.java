package com.nequi.pruebanequi.application.handler;

import com.nequi.pruebanequi.application.dto.ProductRequestDTO;
import com.nequi.pruebanequi.domain.model.models.Product;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface IProductHandler {
    Mono<Product> createProduct(ProductRequestDTO productRequestDTO);

    Mono<Void> deleteProduct(Integer integer);
}
