package com.nequi.pruebanequi.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FranchiseCreateRequestDTO {
    private String franchiseName;
    private List<Integer> branchIds;
}
