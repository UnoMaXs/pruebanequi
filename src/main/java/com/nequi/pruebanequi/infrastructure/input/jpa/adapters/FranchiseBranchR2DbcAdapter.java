package com.nequi.pruebanequi.infrastructure.input.jpa.adapters;

import com.nequi.pruebanequi.domain.model.models.FranchiseBranch;
import com.nequi.pruebanequi.domain.model.spi.IFranchiseBranchPersistencePort;
import com.nequi.pruebanequi.infrastructure.input.jpa.entities.FranchiseBranchEntity;
import com.nequi.pruebanequi.infrastructure.input.jpa.mappers.IFranchiseBranchEntityMapper;
import com.nequi.pruebanequi.infrastructure.input.jpa.repositories.IFranchiseBranchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
public class FranchiseBranchR2DbcAdapter implements IFranchiseBranchPersistencePort {
    private final IFranchiseBranchRepository franchiseBranchRepository;
    private final IFranchiseBranchEntityMapper franchiseBranchEntityMapper;

    @Override
    public Mono<FranchiseBranch> saveFranchiseBranch(FranchiseBranch franchiseBranch) {
        FranchiseBranchEntity entity = franchiseBranchEntityMapper.toEntity(franchiseBranch);
        return franchiseBranchRepository.save(entity)
                .map(franchiseBranchEntityMapper::toModel);
    }
}
