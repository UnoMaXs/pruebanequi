package com.nequi.pruebanequi.infrastructure.input.jpa.mappers;

import com.nequi.pruebanequi.domain.model.models.Product;
import com.nequi.pruebanequi.infrastructure.input.jpa.entities.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IProductEntityMapper {
    Product toModel(ProductEntity entity);
    ProductEntity toEntity(Product product);
}
