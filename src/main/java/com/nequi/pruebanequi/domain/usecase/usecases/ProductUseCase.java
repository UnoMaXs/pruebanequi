package com.nequi.pruebanequi.domain.usecase.usecases;


import com.nequi.pruebanequi.domain.model.api.IProductServicePort;
import com.nequi.pruebanequi.domain.model.models.Product;
import com.nequi.pruebanequi.domain.model.spi.IProductPersistencePort;
import com.nequi.pruebanequi.domain.usecase.validations.ProductBusinessValidations;
import reactor.core.publisher.Mono;

public class ProductUseCase implements IProductServicePort {

    private final IProductPersistencePort productPersistencePort;
    private final ProductBusinessValidations productBusinessValidations;
    public ProductUseCase(IProductPersistencePort productPersistencePort, ProductBusinessValidations productBusinessValidations) {
        this.productPersistencePort = productPersistencePort;
        this.productBusinessValidations = productBusinessValidations;
    }

    @Override
    public Mono<Product> createProduct(Product productMono) {
        return productBusinessValidations.validateProduct(productMono)
                .flatMap(productPersistencePort::save);
    }

    @Override
    public Mono<Void> deleteProductById(Integer productId) {
        return productPersistencePort.deleteProductById(productId);
    }

    @Override
    public Mono<Product> updateProduct(Product product) {
        return productPersistencePort.findProductById(product.getId())
                .flatMap(existingProduct -> {
                    existingProduct.setStock(product.getStock());
                    return productPersistencePort.save(existingProduct);
                });
    }

    @Override
    public Mono<Product> getProductById(Integer integer) {
        return productPersistencePort.findProductById(integer);
    }

}
