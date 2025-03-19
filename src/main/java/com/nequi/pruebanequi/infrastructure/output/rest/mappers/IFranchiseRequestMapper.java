package com.nequi.pruebanequi.infrastructure.output.rest.mappers;


import com.nequi.pruebanequi.domain.model.models.Branch;
import com.nequi.pruebanequi.domain.model.models.Franchise;
import com.nequi.pruebanequi.application.dto.FranchiseCreateRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IFranchiseRequestMapper {
    default Franchise toDomain(FranchiseCreateRequestDTO franchiseCreateRequestDTO){
        List<Branch> branches = mapBranchesIds(franchiseCreateRequestDTO.getBranchIds());
        return new Franchise(
                null,
                franchiseCreateRequestDTO.getFranchiseName(),
                branches
        );

    }

    private List<Branch> mapBranchesIds(List<Integer> branches) {
        return branches == null ? List.of() : branches.stream()
                .map(branchId -> new Branch(branchId, null, null))
                .toList();
    }
}
