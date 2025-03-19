package com.nequi.pruebanequi.infrastructure.input.jpa.mappers;

import com.nequi.pruebanequi.domain.model.models.Branch;
import com.nequi.pruebanequi.infrastructure.input.jpa.entities.BranchEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IBranchEntityMapper {
    Branch toModel(BranchEntity entity);
    BranchEntity toEntity(Branch branch);
}
