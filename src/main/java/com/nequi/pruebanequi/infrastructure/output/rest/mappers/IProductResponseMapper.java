package com.nequi.pruebanequi.infrastructure.output.rest.mappers;


import com.nequi.pruebanequi.domain.model.models.Product;
import com.nequi.pruebanequi.application.dto.ProductResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IProductResponseMapper {
    default ProductResponseDto toProductResponseDto(Product product) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setName(product.getName());
        dto.setStock(product.getStock());
        return dto;
    }
}
