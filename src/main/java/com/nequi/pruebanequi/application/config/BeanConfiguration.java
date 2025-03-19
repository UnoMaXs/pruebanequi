package com.nequi.pruebanequi.application.config;

import com.nequi.pruebanequi.domain.model.api.IBranchProductServicePort;
import com.nequi.pruebanequi.domain.model.api.IBranchServicePort;
import com.nequi.pruebanequi.domain.model.api.IFranchiseBranchServicePort;
import com.nequi.pruebanequi.domain.model.api.IFranchiseServicePort;
import com.nequi.pruebanequi.domain.model.api.IProductServicePort;
import com.nequi.pruebanequi.domain.model.spi.IBranchPersistencePort;
import com.nequi.pruebanequi.domain.model.spi.IBranchProductPersistencePort;
import com.nequi.pruebanequi.domain.model.spi.IFranchiseBranchPersistencePort;
import com.nequi.pruebanequi.domain.model.spi.IFranchisePersistencePort;
import com.nequi.pruebanequi.domain.model.spi.IProductPersistencePort;
import com.nequi.pruebanequi.domain.usecase.usecases.BranchProductUseCase;
import com.nequi.pruebanequi.domain.usecase.usecases.BranchUseCase;
import com.nequi.pruebanequi.domain.usecase.usecases.FranchiseBranchUseCase;
import com.nequi.pruebanequi.domain.usecase.usecases.FranchiseUseCase;
import com.nequi.pruebanequi.domain.usecase.usecases.ProductUseCase;
import com.nequi.pruebanequi.domain.usecase.validations.BranchBusinessValidations;
import com.nequi.pruebanequi.domain.usecase.validations.BranchProductBusinessValidations;
import com.nequi.pruebanequi.domain.usecase.validations.FranchiseBranchBusinessValidations;
import com.nequi.pruebanequi.domain.usecase.validations.FranchiseBusinessValidations;
import com.nequi.pruebanequi.domain.usecase.validations.ProductBusinessValidations;
import com.nequi.pruebanequi.infrastructure.input.jpa.adapters.BranchProductR2DbcAdapter;
import com.nequi.pruebanequi.infrastructure.input.jpa.adapters.BranchR2dbcAdapter;
import com.nequi.pruebanequi.infrastructure.input.jpa.adapters.FranchiseBranchR2DbcAdapter;
import com.nequi.pruebanequi.infrastructure.input.jpa.adapters.FranchiseR2dbcAdapter;
import com.nequi.pruebanequi.infrastructure.input.jpa.adapters.ProductR2dbcAdapter;
import com.nequi.pruebanequi.infrastructure.input.jpa.mappers.IBranchEntityMapper;
import com.nequi.pruebanequi.infrastructure.input.jpa.mappers.IBranchProductEntityMapper;
import com.nequi.pruebanequi.infrastructure.input.jpa.mappers.IFranchiseBranchEntityMapper;
import com.nequi.pruebanequi.infrastructure.input.jpa.mappers.IFranchiseEntityMapper;
import com.nequi.pruebanequi.infrastructure.input.jpa.mappers.IProductEntityMapper;
import com.nequi.pruebanequi.infrastructure.input.jpa.repositories.IBranchProductRepository;
import com.nequi.pruebanequi.infrastructure.input.jpa.repositories.IBranchRepository;
import com.nequi.pruebanequi.infrastructure.input.jpa.repositories.IFranchiseBranchRepository;
import com.nequi.pruebanequi.infrastructure.input.jpa.repositories.IFranchiseRepository;
import com.nequi.pruebanequi.infrastructure.input.jpa.repositories.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IProductRepository productRepository;
    private final IProductEntityMapper productEntityMapper;
    private final IBranchEntityMapper branchEntityMapper;
    private final IBranchRepository branchRepository;
    private final IFranchiseRepository franchiseRepository;
    private final IFranchiseEntityMapper franchiseEntityMapper;
    private final IBranchProductRepository productBranchRepository;
    private final IBranchProductEntityMapper productBranchEntityMapper;
    private final IFranchiseBranchRepository franchiseBranchRepository;
    private final IFranchiseBranchEntityMapper franchiseBranchEntityMapper;

    @Bean
    public IProductPersistencePort productPersistencePort() {
        return new ProductR2dbcAdapter(productRepository, productEntityMapper);
    }
    @Bean
    public IBranchPersistencePort branchPersistencePort() {
        return new BranchR2dbcAdapter(branchRepository, branchEntityMapper);
    }
    @Bean
    public IFranchisePersistencePort franchisePersistencePort() {
        return new FranchiseR2dbcAdapter(franchiseRepository, franchiseEntityMapper);
    }
    @Bean
    public IBranchProductPersistencePort productBranchPersistencePort() {
        return new BranchProductR2DbcAdapter(productBranchRepository, productBranchEntityMapper);
    }
    @Bean
    public IFranchiseBranchPersistencePort franchiseBranchPersistencePort() {
        return new FranchiseBranchR2DbcAdapter(franchiseBranchRepository, franchiseBranchEntityMapper);
    }
    @Bean
    public ProductBusinessValidations productBusinessValidations() {
        return new ProductBusinessValidations();
    }
    @Bean
    public BranchBusinessValidations branchBusinessValidations() {
        return new BranchBusinessValidations();
    }
    @Bean
    public FranchiseBusinessValidations franchiseBusinessValidations() {
        return new FranchiseBusinessValidations();
    }
    @Bean
    public BranchProductBusinessValidations productBranchBusinessValidations() {
        return new BranchProductBusinessValidations();
    }
    @Bean
    public FranchiseBranchBusinessValidations franchiseBranchBusinessValidations() {
        return new FranchiseBranchBusinessValidations();
    }
    @Bean
    public IProductServicePort productServicePort(IProductPersistencePort productPersistencePort, ProductBusinessValidations productBusinessValidations) {
        return new ProductUseCase(productPersistencePort, productBusinessValidations);
    }
    @Bean
    public IBranchServicePort branchServicePort(IBranchPersistencePort branchPersistencePort, BranchBusinessValidations branchBusinessValidations) {
        return new BranchUseCase(branchPersistencePort, branchBusinessValidations);
    }
    @Bean
    public IFranchiseServicePort franchiseServicePort(IFranchisePersistencePort franchisePersistencePort, FranchiseBusinessValidations franchiseBusinessValidations) {
        return new FranchiseUseCase(franchisePersistencePort, franchiseBusinessValidations);
    }
    @Bean
    public IBranchProductServicePort productBranchServicePort(IBranchProductPersistencePort productBranchPersistencePort, BranchProductBusinessValidations branchProductBusinessValidations) {
        return new BranchProductUseCase(productBranchPersistencePort, branchProductBusinessValidations);
    }
    @Bean
    public IFranchiseBranchServicePort franchiseBranchServicePort(IFranchiseBranchPersistencePort franchiseBranchPersistencePort, FranchiseBranchBusinessValidations franchiseBranchBusinessValidations) {
        return new FranchiseBranchUseCase(franchiseBranchPersistencePort, franchiseBranchBusinessValidations);
    }
}
