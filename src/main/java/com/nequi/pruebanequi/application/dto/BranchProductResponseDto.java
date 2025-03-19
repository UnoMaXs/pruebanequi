package com.nequi.pruebanequi.application.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class BranchProductResponseDto {
    private String branchName;
    private List<ProductResponseDto> productResponseDtoList;
}
