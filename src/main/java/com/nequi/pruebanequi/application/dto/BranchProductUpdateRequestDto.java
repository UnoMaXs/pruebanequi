package com.nequi.pruebanequi.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchProductUpdateRequestDto {
    private Integer branchId;
    private List<ProductDto> productList;
}
