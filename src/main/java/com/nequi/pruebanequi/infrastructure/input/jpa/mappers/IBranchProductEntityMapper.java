package com.nequi.pruebanequi.infrastructure.input.jpa.mappers;

import com.nequi.pruebanequi.domain.model.models.BranchProduct;
import com.nequi.pruebanequi.infrastructure.input.jpa.entities.BranchProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IBranchProductEntityMapper {
    BranchProduct toModel(BranchProductEntity entity);
    BranchProductEntity toEntity(BranchProduct branchProduct);
}
