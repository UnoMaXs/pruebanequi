package com.nequi.pruebanequi.infrastructure.input.jpa.mappers;

import com.nequi.pruebanequi.domain.model.models.Franchise;
import com.nequi.pruebanequi.infrastructure.input.jpa.entities.FranchiseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IFranchiseEntityMapper {
    Franchise toModel(FranchiseEntity entity);
    FranchiseEntity toEntity(Franchise franchise);
}
