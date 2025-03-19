package com.nequi.pruebanequi.domain.model.spi;


import com.nequi.pruebanequi.domain.model.models.Product;
import reactor.core.publisher.Mono;

public interface IProductPersistencePort {
    Mono<Product> save(Product product);
    Mono<Void> deleteProductById(Integer productId);
    Mono<Product> findProductById(Integer id);
}
