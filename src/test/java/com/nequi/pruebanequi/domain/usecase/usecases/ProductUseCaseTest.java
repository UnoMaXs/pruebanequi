package com.nequi.pruebanequi.domain.usecase.usecases;

import com.nequi.pruebanequi.domain.model.models.Product;
import com.nequi.pruebanequi.domain.model.spi.IProductPersistencePort;
import com.nequi.pruebanequi.domain.usecase.exceptions.BusinessErrorMessage;
import com.nequi.pruebanequi.domain.usecase.exceptions.BusinessException;
import com.nequi.pruebanequi.domain.usecase.validations.ProductBusinessValidations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductUseCaseTest {
    @Mock
    private ProductUseCase productUseCase;
    @Mock
    private IProductPersistencePort productPersistencePort;
    @Mock
    private ProductBusinessValidations productBusinessValidations;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productUseCase = new ProductUseCase(productPersistencePort,productBusinessValidations);

        Product product = new Product();
        product.setId(1);
        product.setName("ttt");
        product.setStock(1);

        lenient().when(productBusinessValidations.validateProduct(any(Product.class)))
                .thenReturn(Mono.just(product));
        lenient().when(productPersistencePort.save(any(Product.class)))
                .thenReturn(Mono.just(product));
    }

    @Test
    void createProduct_successful() {
        Product input = new Product();
        Mono<Product> result = productUseCase.createProduct(input);


        StepVerifier.create(result)
                .expectNextMatches(product ->
                        product.getId() == 1 &&
                                "ttt".equals(product.getName()) &&
                        product.getStock() == 1)
                .expectComplete()
                .verify();

    }

    @Test
    void createProduct_nullName_shouldThrowMandatoryNameError() {
        Product input = new Product();
        input.setStock(10);

        when(productBusinessValidations.validateProduct(any(Product.class)))
                .thenReturn(Mono.error(new BusinessException(BusinessErrorMessage.MANDATORY_NAME)));

        Mono<Product> result = productUseCase.createProduct(input);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals(BusinessErrorMessage.MANDATORY_NAME.getMessage()))
                .verify();
    }

    @Test
    void createProduct_zeroStock_shouldThrowMandatoryStock() {
        Product input = new Product();
        input.setName("Producto Test");
        input.setStock(0);

        when(productBusinessValidations.validateProduct(any(Product.class)))
                .thenReturn(Mono.error(new BusinessException(BusinessErrorMessage.MANDATORY_STOCK)));

        Mono<Product> result = productUseCase.createProduct(input);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals(BusinessErrorMessage.MANDATORY_STOCK.getMessage()))
                .verify();
    }

    @Test
    void deleteProductById_successful() {
        Integer productId = 1;

        when(productPersistencePort.deleteProductById(productId)).thenReturn(Mono.empty());

        Mono<Void> result = productUseCase.deleteProductById(productId);

        StepVerifier.create(result)
                .expectComplete()
                .verify();

        verify(productPersistencePort).deleteProductById(productId);
    }

    @Test
    void deleteProductById_notFound() {
        Integer productId = 1;

        when(productPersistencePort.deleteProductById(productId)).thenReturn(Mono.error(new BusinessException(BusinessErrorMessage.PRODUCT_NOT_FOUND)));

        Mono<Void> result = productUseCase.deleteProductById(productId);

        StepVerifier.create(result)
                .expectError(BusinessException.class)
                .verify();

        verify(productPersistencePort).deleteProductById(productId);
    }

    @Test
    void updateProduct_NotFound() {
        Product product = new Product();
        product.setId(1);
        product.setStock(20);

        when(productPersistencePort.findProductById(product.getId()))
                .thenReturn(Mono.error(new BusinessException(BusinessErrorMessage.PRODUCT_NOT_FOUND)));

        Mono<Product> result = productUseCase.updateProduct(product);

        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof BusinessException &&
                                throwable.getMessage().equals(BusinessErrorMessage.PRODUCT_NOT_FOUND.getMessage())
                )
                .verify();

        verify(productPersistencePort).findProductById(product.getId());
        verify(productPersistencePort, never()).save(any(Product.class));
    }

    @Test
    void updateProduct_Successfully() {
        Product product = new Product();
        product.setId(1);
        product.setName("Product 1");
        product.setStock(10);

        Product updatedProduct = new Product();
        updatedProduct.setId(1);
        updatedProduct.setName("Product 1");
        updatedProduct.setStock(20);

        when(productPersistencePort.findProductById(product.getId())).thenReturn(Mono.just(product));
        when(productPersistencePort.save(any(Product.class))).thenReturn(Mono.just(updatedProduct));

        Mono<Product> result = productUseCase.updateProduct(updatedProduct);

        StepVerifier.create(result)
                .expectNextMatches(p ->
                        p.getId().equals(updatedProduct.getId()) &&
                                p.getStock() == updatedProduct.getStock() // Usar == para comparar int
                )
                .verifyComplete();

        verify(productPersistencePort).findProductById(product.getId());
        verify(productPersistencePort).save(any(Product.class));
    }

    @Test
    void getProductById_Successfully() {
        Integer productId = 1;
        Product product = new Product();
        product.setId(productId);
        product.setName("Product 1");
        product.setStock(10);

        when(productPersistencePort.findProductById(productId)).thenReturn(Mono.just(product));

        Mono<Product> result = productUseCase.getProductById(productId);

        StepVerifier.create(result)
                .expectNextMatches(p ->
                        p.getId().equals(productId) &&
                                "Product 1".equals(p.getName()) &&
                                p.getStock() == 10
                )
                .verifyComplete();

        verify(productPersistencePort).findProductById(productId);
    }

    @Test
    void getProductById_NotFound() {
        Integer productId = 1;

        when(productPersistencePort.findProductById(productId))
                .thenReturn(Mono.error(new BusinessException(BusinessErrorMessage.PRODUCT_NOT_FOUND)));

        Mono<Product> result = productUseCase.getProductById(productId);

        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof BusinessException &&
                                throwable.getMessage().equals(BusinessErrorMessage.PRODUCT_NOT_FOUND.getMessage())
                )
                .verify();

        verify(productPersistencePort).findProductById(productId);
    }
}
