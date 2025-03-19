package com.nequi.pruebanequi.domain.usecase.usecases;

import com.nequi.pruebanequi.domain.model.models.Franchise;
import com.nequi.pruebanequi.domain.model.spi.IFranchisePersistencePort;
import com.nequi.pruebanequi.domain.usecase.exceptions.BusinessErrorMessage;
import com.nequi.pruebanequi.domain.usecase.exceptions.BusinessException;
import com.nequi.pruebanequi.domain.usecase.validations.FranchiseBusinessValidations;
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
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FranchiseUseCaseTest {

    @Mock
    private IFranchisePersistencePort franchisePersistencePort;
    @Mock
    private FranchiseBusinessValidations franchiseBusinessValidations;
    @Mock
    private FranchiseUseCase franchiseUseCase;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        franchiseUseCase = new FranchiseUseCase(franchisePersistencePort, franchiseBusinessValidations);
        Franchise franchise = new Franchise();
        franchise.setId(1);
        franchise.setName("Franchise Test");

        lenient().when(franchiseBusinessValidations.validateBranch(any(Franchise.class)))
                .thenReturn(Mono.just(franchise));
        lenient().when(franchisePersistencePort.save(any(Franchise.class)))
                .thenReturn(Mono.just(franchise));
    }

    @Test
    void createFranchise_Successfully() {
        Franchise franchise = new Franchise();
        franchise.setId(1);
        franchise.setName("Franchise Test");

        Mono<Franchise> result = franchiseUseCase.createFranchise(franchise);

        StepVerifier.create(result)
                .expectNextMatches(f -> f.getId() == 1 && f.getName().equals("Franchise Test"))
                .verifyComplete();

        verify(franchisePersistencePort).save(any(Franchise.class));
    }

    @Test
    void createFranchise_InvalidData_ThrowsBusinessError() {
        Franchise franchise = new Franchise();
        franchise.setId(1);
        franchise.setName(null);

        lenient().when(franchiseBusinessValidations.validateBranch(any(Franchise.class)))
                .thenReturn(Mono.error(new BusinessException(BusinessErrorMessage.MANDATORY_NAME)));

        Mono<Franchise> result = franchiseUseCase.createFranchise(franchise);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof BusinessException
                        && ((BusinessException) throwable).getErrorMessage().equals(BusinessErrorMessage.MANDATORY_NAME))
                .verify();

        verify(franchiseBusinessValidations).validateBranch(any(Franchise.class));
    }

    @Test
    void deleteFranchiseById_Successfully() {
        lenient().when(franchisePersistencePort.deleteFranchiseById(any(Integer.class)))
                .thenReturn(Mono.empty());

        Mono<Void> result = franchiseUseCase.deleteFranchiseById(1);

        StepVerifier.create(result)
                .verifyComplete();

        verify(franchisePersistencePort).deleteFranchiseById(any(Integer.class));
    }

}
