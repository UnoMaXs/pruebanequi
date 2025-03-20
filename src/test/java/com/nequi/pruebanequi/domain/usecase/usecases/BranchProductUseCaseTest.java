package com.nequi.pruebanequi.domain.usecase.usecases;

import com.nequi.pruebanequi.domain.model.models.BranchProduct;
import com.nequi.pruebanequi.domain.model.spi.IBranchProductPersistencePort;
import com.nequi.pruebanequi.domain.usecase.exceptions.BusinessErrorMessage;
import com.nequi.pruebanequi.domain.usecase.exceptions.BusinessException;
import com.nequi.pruebanequi.domain.usecase.validations.BranchProductBusinessValidations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BranchProductUseCaseTest {
    @Mock
    private IBranchProductPersistencePort branchProductPersistencePort;
    @Mock
    private BranchProductBusinessValidations branchProductBusinessValidations;
    @Mock
    private com.nequi.pruebanequi.domain.usecase.usecases.BranchProductUseCase branchProductUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        branchProductUseCase = new BranchProductUseCase(branchProductPersistencePort,branchProductBusinessValidations);
        BranchProduct branchProduct = new BranchProduct();
        branchProduct.setId(1);
        branchProduct.setBranchId(1);
        branchProduct.setProductId(1);

        lenient().when(branchProductBusinessValidations.validateProductBranch(any(BranchProduct.class)))
                .thenReturn(Mono.justOrEmpty(branchProduct));
        lenient().when(branchProductPersistencePort.saveProductBranch(any(BranchProduct.class)))
                .thenReturn(Mono.justOrEmpty(branchProduct));
    }

    @Test
    void createBranchProduct_Successfully() {
        BranchProduct branchProduct = new BranchProduct();
        branchProduct.setId(1);
        branchProduct.setBranchId(1);
        branchProduct.setProductId(1);

        Mono<BranchProduct> result = branchProductUseCase.associateProductToBranch(branchProduct);

        StepVerifier.create(result)
                .expectNextMatches(bp -> bp.getId() == 1 && bp.getBranchId() == 1 && bp.getProductId() == 1)
                .verifyComplete();

        verify(branchProductPersistencePort).saveProductBranch(any(BranchProduct.class));
    }
    @Test
    void createBranchProduct_IsNull_ThrowsMandatoryFieldException() {

        BranchProduct branchProduct = new BranchProduct();
        branchProduct.setId(1);
        branchProduct.setBranchId(0);
        branchProduct.setProductId(0);

        Mockito.when(branchProductBusinessValidations.validateProductBranch(any(BranchProduct.class)))
                .thenReturn(Mono.error(new BusinessException(BusinessErrorMessage.MANDATORY_DATA)));

        Mono<BranchProduct> result = branchProductUseCase.associateProductToBranch(branchProduct);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals(BusinessErrorMessage.MANDATORY_DATA.getMessage()))
                .verify();

        verify(branchProductPersistencePort, never()).saveProductBranch(any(BranchProduct.class));
    }

    @Test
    void getBranchProductByBranchId_Successfully() {
        BranchProduct branchProduct = new BranchProduct();
        branchProduct.setId(1);
        branchProduct.setBranchId(1);
        branchProduct.setProductId(1);

        List<BranchProduct> branchProductList = new ArrayList<>();
        branchProductList.add(branchProduct);

        lenient().when(branchProductPersistencePort.findBranchProductByBranchId(any(Integer.class)))
                .thenReturn(Mono.just(branchProductList));

        Mono<List<BranchProduct>> result = branchProductUseCase.getBranchProductByBranchId(1);

        StepVerifier.create(result)
                .expectNextMatches(list -> list.size() == 1 && list.get(0).getBranchId() == 1)
                .verifyComplete();

        verify(branchProductPersistencePort).findBranchProductByBranchId(1);
    }

    @Test
    void getBranchProductByBranchId_EmptyList() {
        List<BranchProduct> branchProductList = new ArrayList<>();

        lenient().when(branchProductPersistencePort.findBranchProductByBranchId(any(Integer.class)))
                .thenReturn(Mono.just(branchProductList));

        Mono<List<BranchProduct>> result = branchProductUseCase.getBranchProductByBranchId(1);

        StepVerifier.create(result)
                .expectNextMatches(List::isEmpty)
                .verifyComplete();

        verify(branchProductPersistencePort).findBranchProductByBranchId(1);
    }

    @Test
    void getBranchProductByBranchId_Error() {
        lenient().when(branchProductPersistencePort.findBranchProductByBranchId(any(Integer.class)))
                .thenReturn(Mono.error(new RuntimeException("Error retrieving data")));

        Mono<List<BranchProduct>> result = branchProductUseCase.getBranchProductByBranchId(1);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Error retrieving data"))
                .verify();

        verify(branchProductPersistencePort).findBranchProductByBranchId(1);
    }

}