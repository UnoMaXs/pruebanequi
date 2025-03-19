package com.nequi.pruebanequi.infrastructure.output.rest.mappers;


import com.nequi.pruebanequi.domain.model.models.FranchiseBranch;
import com.nequi.pruebanequi.application.dto.FranchiseBranchRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IFranchiseBranchRequestDto {
    default List<FranchiseBranch> toDomain(FranchiseBranchRequestDTO franchiseBranchRequestDto){
        return franchiseBranchRequestDto.getBranchIds()
                .stream()
                .map(branchId -> new FranchiseBranch(null, franchiseBranchRequestDto.getFranchiseId(), branchId))
                .toList();
    }
}
