package com.nequi.pruebanequi.infrastructure.input.jpa.adapters;

import com.nequi.pruebanequi.domain.model.models.Branch;
import com.nequi.pruebanequi.domain.model.spi.IBranchPersistencePort;
import com.nequi.pruebanequi.infrastructure.input.jpa.entities.BranchEntity;
import com.nequi.pruebanequi.infrastructure.input.jpa.exceptions.DBErrorMessage;
import com.nequi.pruebanequi.infrastructure.input.jpa.exceptions.DBExceptions;
import com.nequi.pruebanequi.infrastructure.input.jpa.mappers.IBranchEntityMapper;
import com.nequi.pruebanequi.infrastructure.input.jpa.repositories.IBranchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DuplicateKeyException;
import reactor.core.publisher.Mono;
@RequiredArgsConstructor
@Slf4j
public class BranchR2dbcAdapter implements IBranchPersistencePort {
    private final IBranchRepository branchRepository;
    private final IBranchEntityMapper branchEntityMapper;
    @Override
    public Mono<Branch> save(Branch branch) {
        BranchEntity entity = branchEntityMapper.toEntity(branch);
        return branchRepository.findByName(entity.getName())
                .flatMap(existingBranch -> Mono.error(new DBExceptions(DBErrorMessage.BRANCH_ALREADY_EXISTS)))
                .defaultIfEmpty(entity)
                .flatMap(entityToSave ->
                    branchRepository.save(entity)
                        .map(branchEntityMapper::toModel)
                        .onErrorResume(DuplicateKeyException.class, ex -> Mono.error(new DBExceptions(DBErrorMessage.BRANCH_ALREADY_EXISTS)))
                        .onErrorResume(DataAccessResourceFailureException.class, ex -> Mono.error(new DBExceptions(DBErrorMessage.FAILED_CONNECTION)))
                );
    }

    @Override
    public Mono<Void> deleteBranchById(Integer id) {
        return branchRepository.findById(id)
                .flatMap(branch ->
                        branchRepository.deleteById(id)
                )
                .onErrorResume(ex -> Mono.error(new DBExceptions(DBErrorMessage.BRANCH_NOT_FOUND)));
    }

    @Override
    public Mono<Branch> getBranchById(Integer branchId) {
        return branchRepository.findById(branchId)
                .map(branchEntityMapper::toModel);
    }
}
