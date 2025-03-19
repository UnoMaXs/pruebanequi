package com.nequi.pruebanequi.infrastructure.input.jpa.mappers;

import com.nequi.pruebanequi.domain.model.models.FranchiseBranch;
import com.nequi.pruebanequi.infrastructure.input.jpa.entities.FranchiseBranchEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IFranchiseBranchEntityMapper {
    FranchiseBranch toModel(FranchiseBranchEntity entity);
    FranchiseBranchEntity toEntity(FranchiseBranch franchiseBranch);

}
