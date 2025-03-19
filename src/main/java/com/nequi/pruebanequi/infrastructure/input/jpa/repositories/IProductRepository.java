package com.nequi.pruebanequi.infrastructure.input.jpa.repositories;

import com.nequi.pruebanequi.infrastructure.input.jpa.entities.ProductEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IProductRepository extends ReactiveCrudRepository<ProductEntity, Integer> {
}
