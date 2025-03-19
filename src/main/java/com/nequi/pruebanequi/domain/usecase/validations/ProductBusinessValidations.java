package com.nequi.pruebanequi.domain.usecase.validations;


import com.nequi.pruebanequi.domain.model.models.Product;
import com.nequi.pruebanequi.domain.usecase.exceptions.BusinessErrorMessage;
import com.nequi.pruebanequi.domain.usecase.exceptions.BusinessException;
import reactor.core.publisher.Mono;

public class ProductBusinessValidations {

    public Mono<Product> validateProduct(Product product) {
        return validateName(product)
                .then(validateStock(product))
                .then(Mono.just(product));
    }

    private Mono<Void> validateName(Product product) {
        if (product.getName().isEmpty()) {
            return Mono.error(new BusinessException(BusinessErrorMessage.MANDATORY_NAME));
        }
        return Mono.empty();
    }

    private Mono<Void> validateStock(Product product) {
        if (product.getStock() <= 0) {
            return Mono.error(new BusinessException(BusinessErrorMessage.MANDATORY_STOCK));
        }
        return Mono.empty();
    }
}
