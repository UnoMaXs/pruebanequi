package com.nequi.pruebanequi.infrastructure.input.jpa.adapters;


import com.nequi.pruebanequi.domain.model.models.Product;
import com.nequi.pruebanequi.domain.model.spi.IProductPersistencePort;
import com.nequi.pruebanequi.infrastructure.input.jpa.entities.ProductEntity;
import com.nequi.pruebanequi.infrastructure.input.jpa.exceptions.DBErrorMessage;
import com.nequi.pruebanequi.infrastructure.input.jpa.exceptions.DBExceptions;
import com.nequi.pruebanequi.infrastructure.input.jpa.mappers.IProductEntityMapper;
import com.nequi.pruebanequi.infrastructure.input.jpa.repositories.IProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DuplicateKeyException;
import reactor.core.publisher.Mono;
@RequiredArgsConstructor
@Slf4j
public class ProductR2dbcAdapter implements IProductPersistencePort {

    private final IProductRepository productRepository;
    private final IProductEntityMapper productEntityMapper;
    @Override
    public Mono<Product> save(Product product) {
        ProductEntity entity = productEntityMapper.toEntity(product);
        return productRepository.save(entity)
            .map(productEntityMapper::toModel)
            .onErrorResume(DuplicateKeyException.class, ex -> Mono.error(new DBExceptions(DBErrorMessage.PRODUCT_ALREADY_EXISTS)))
            .onErrorResume(DataAccessResourceFailureException.class, ex -> Mono.error(new DBExceptions(DBErrorMessage.FAILED_CONNECTION)));
    }

    @Override
    public Mono<Void> deleteProductById(Integer productId) {
        return productRepository.findById(productId)
                .flatMap(branch ->
                    productRepository.deleteById(productId)
                )
                .onErrorResume(ex -> Mono.error(new DBExceptions(DBErrorMessage.PRODUCT_NOT_FOUND)));
    }

    @Override
    public Mono<Product> findProductById(Integer id) {
        return productRepository.findById(id)
                .map(productEntityMapper::toModel)
                .onErrorResume(ex -> Mono.error(new DBExceptions(DBErrorMessage.PRODUCT_NOT_FOUND)));
    }
}
