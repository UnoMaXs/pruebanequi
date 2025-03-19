package com.nequi.pruebanequi.infrastructure.output.rest.mappers;

import com.nequi.pruebanequi.domain.model.models.Branch;
import com.nequi.pruebanequi.application.dto.BranchProductResponseDto;
import com.nequi.pruebanequi.application.dto.ProductResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IBranchProductResponseDto {
    default BranchProductResponseDto toDto(Branch branch, List<ProductResponseDto> productResponseDtos) {
        BranchProductResponseDto responseDto = new BranchProductResponseDto();
        responseDto.setBranchName(branch.getName());
        responseDto.setProductResponseDtoList(productResponseDtos);
        return responseDto;
    }
}
