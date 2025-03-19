package com.nequi.pruebanequi.domain.usecase.usecases;

import com.nequi.pruebanequi.domain.model.models.FranchiseBranch;
import com.nequi.pruebanequi.domain.model.spi.IFranchiseBranchPersistencePort;
import com.nequi.pruebanequi.domain.usecase.exceptions.BusinessErrorMessage;
import com.nequi.pruebanequi.domain.usecase.exceptions.BusinessException;
import com.nequi.pruebanequi.domain.usecase.validations.FranchiseBranchBusinessValidations;
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
class FranchiseBranchUseCaseTest {
    @Mock
    private IFranchiseBranchPersistencePort franchiseBranchPersistencePort;
    @Mock
    private FranchiseBranchBusinessValidations franchiseBranchBusinessValidations;
    @Mock
    private FranchiseBranchUseCase franchiseBranchUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        franchiseBranchUseCase = new FranchiseBranchUseCase(franchiseBranchPersistencePort, franchiseBranchBusinessValidations);

        FranchiseBranch franchiseBranch = new FranchiseBranch();
        franchiseBranch.setId(1);
        franchiseBranch.setBranchId(100);
        franchiseBranch.setFranchiseId(200);

        lenient().when(franchiseBranchBusinessValidations.validateFranchiseBranch(any(FranchiseBranch.class)))
                .thenReturn(Mono.just(franchiseBranch));
        lenient().when(franchiseBranchPersistencePort.saveFranchiseBranch(any(FranchiseBranch.class)))
                .thenReturn(Mono.just(franchiseBranch));
    }

    @Test
    void associateBranchToFranchise_Successfully() {
        FranchiseBranch franchiseBranch = new FranchiseBranch();
        franchiseBranch.setId(1);
        franchiseBranch.setBranchId(100);
        franchiseBranch.setFranchiseId(200);

        Mono<Void> result = franchiseBranchUseCase.associateBranchToFranchise(franchiseBranch);

        StepVerifier.create(result)
                .verifyComplete();

        verify(franchiseBranchPersistencePort).saveFranchiseBranch(any(FranchiseBranch.class));
    }

    @Test
    void associateBranchToFranchise_InvalidData_ThrowsBusinessError() {
        FranchiseBranch franchiseBranch = new FranchiseBranch();
        franchiseBranch.setId(1);
        franchiseBranch.setBranchId(0);

        lenient().when(franchiseBranchBusinessValidations.validateFranchiseBranch(any(FranchiseBranch.class)))
                .thenReturn(Mono.error(new BusinessException(BusinessErrorMessage.MANDATORY_DATA)));

        Mono<Void> result = franchiseBranchUseCase.associateBranchToFranchise(franchiseBranch);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof BusinessException
                        && ((BusinessException) throwable).getErrorMessage().equals(BusinessErrorMessage.MANDATORY_DATA))
                .verify();

        verify(franchiseBranchBusinessValidations).validateFranchiseBranch(any(FranchiseBranch.class));
    }
}
