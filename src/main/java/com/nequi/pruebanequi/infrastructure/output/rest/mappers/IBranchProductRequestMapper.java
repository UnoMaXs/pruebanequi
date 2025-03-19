package com.nequi.pruebanequi.infrastructure.output.rest.mappers;


import com.nequi.pruebanequi.domain.model.models.BranchProduct;
import com.nequi.pruebanequi.domain.model.models.Product;
import com.nequi.pruebanequi.application.dto.BranchProductRequestDTO;
import com.nequi.pruebanequi.application.dto.BranchProductUpdateRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IBranchProductRequestMapper {
    default List<BranchProduct> toDomain(BranchProductRequestDTO branchProductRequestDTO) {
        return branchProductRequestDTO.getProductIds()
                .stream()
                .map(productId -> new BranchProduct(null, branchProductRequestDTO.getBranchId(), productId))
                .collect(Collectors.toList());
    }
    default List<Product> toProductDomain(BranchProductUpdateRequestDto branchProductUpdateRequestDto) {
        return branchProductUpdateRequestDto.getProductList()
                .stream()
                .map(productDto -> new Product(productDto.getId(), productDto.getStock()))
                .collect(Collectors.toList());
    }
}
