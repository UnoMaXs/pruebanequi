package com.nequi.pruebanequi.infrastructure.output.rest.mappers;


import com.nequi.pruebanequi.domain.model.models.Product;
import com.nequi.pruebanequi.application.dto.ProductDto;
import com.nequi.pruebanequi.application.dto.ProductRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IProductRequestMapper {

    Product toDomain(ProductRequestDTO productRequestDto);

    default Product toProductDomain(ProductDto productDto) {
        return new Product(productDto.getId(), productDto.getStock());
    }

}
