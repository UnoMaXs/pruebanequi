package com.nequi.pruebanequi.infrastructure.input.jpa.adapters;

import com.nequi.pruebanequi.domain.model.models.BranchProduct;
import com.nequi.pruebanequi.domain.model.spi.IBranchProductPersistencePort;
import com.nequi.pruebanequi.infrastructure.input.jpa.entities.BranchProductEntity;
import com.nequi.pruebanequi.infrastructure.input.jpa.exceptions.DBErrorMessage;
import com.nequi.pruebanequi.infrastructure.input.jpa.exceptions.DBExceptions;
import com.nequi.pruebanequi.infrastructure.input.jpa.mappers.IBranchProductEntityMapper;
import com.nequi.pruebanequi.infrastructure.input.jpa.repositories.IBranchProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Slf4j
public class BranchProductR2DbcAdapter implements IBranchProductPersistencePort {
    private final IBranchProductRepository productBranchRepository;
    private final IBranchProductEntityMapper productBranchEntityMapper;
    @Override
    public Mono<BranchProduct> saveProductBranch(BranchProduct branchProduct) {
        BranchProductEntity entity = productBranchEntityMapper.toEntity(branchProduct);
        return productBranchRepository.save(entity)
                .map(productBranchEntityMapper::toModel);
    }

    @Override
    public Mono<List<BranchProduct>> findBranchProductByBranchId(Integer branchId) {
        return productBranchRepository.findByBranchId(branchId)
                .collectList()
                .flatMap(branchProductEntities -> {
                    if (branchProductEntities.isEmpty()) {
                        return Mono.error(new DBExceptions(DBErrorMessage.PRODUCT_NOT_FOUND));
                    }
                    List<BranchProduct> branchProducts = branchProductEntities.stream()
                            .map(productBranchEntityMapper::toModel)
                            .collect(toList());
                    return Mono.just(branchProducts);
                });
    }

}
