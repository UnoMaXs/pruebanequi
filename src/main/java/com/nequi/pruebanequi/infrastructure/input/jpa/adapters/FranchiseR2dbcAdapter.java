package com.nequi.pruebanequi.infrastructure.input.jpa.adapters;

import com.nequi.pruebanequi.domain.model.models.Franchise;
import com.nequi.pruebanequi.domain.model.spi.IFranchisePersistencePort;
import com.nequi.pruebanequi.infrastructure.input.jpa.entities.FranchiseEntity;
import com.nequi.pruebanequi.infrastructure.input.jpa.exceptions.DBErrorMessage;
import com.nequi.pruebanequi.infrastructure.input.jpa.exceptions.DBExceptions;
import com.nequi.pruebanequi.infrastructure.input.jpa.mappers.IFranchiseEntityMapper;
import com.nequi.pruebanequi.infrastructure.input.jpa.repositories.IFranchiseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DuplicateKeyException;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
public class FranchiseR2dbcAdapter implements IFranchisePersistencePort {

    private final IFranchiseRepository franchiseRepository;
    private final IFranchiseEntityMapper franchiseEntityMapper;
    @Override
    public Mono<Franchise> save(Franchise franchise) {
        FranchiseEntity entity = franchiseEntityMapper.toEntity(franchise);
        return franchiseRepository.findByName(entity.getName())
                .flatMap(existingBranch -> Mono.error(new DBExceptions(DBErrorMessage.BRANCH_ALREADY_EXISTS)))
                .defaultIfEmpty(entity)
                .flatMap(entityToSave ->
                        franchiseRepository.save(entity)
                                .map(franchiseEntityMapper::toModel)
                                .onErrorResume(DuplicateKeyException.class, ex -> Mono.error(new DBExceptions(DBErrorMessage.BRANCH_ALREADY_EXISTS)))
                                .onErrorResume(DataAccessResourceFailureException.class, ex -> Mono.error(new DBExceptions(DBErrorMessage.FAILED_CONNECTION)))
                );
    }

    @Override
    public Mono<Void> deleteFranchiseById(Integer id) {
        return franchiseRepository.findById(id)
                .flatMap(branch ->
                        franchiseRepository.deleteById(id)
                )
                .switchIfEmpty(Mono.error(new DBExceptions(DBErrorMessage.FRANCHISE_NOT_FOUND)));
    }
}
