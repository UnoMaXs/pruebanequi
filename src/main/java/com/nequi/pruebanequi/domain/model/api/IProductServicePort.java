package com.nequi.pruebanequi.domain.model.api;


import com.nequi.pruebanequi.domain.model.models.Product;
import reactor.core.publisher.Mono;

public interface IProductServicePort {
    Mono<Product> createProduct(Product product);
    Mono<Void> deleteProductById(Integer productId);
    Mono<Product> updateProduct(Product product);
    Mono<Product> getProductById(Integer integer);
}
