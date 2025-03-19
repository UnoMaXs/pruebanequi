package com.nequi.pruebanequi.application.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductResponseDto {
    private String name;
    private int stock;
}
