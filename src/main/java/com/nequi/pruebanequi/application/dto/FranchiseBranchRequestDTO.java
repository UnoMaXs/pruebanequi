package com.nequi.pruebanequi.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FranchiseBranchRequestDTO {
    private Integer franchiseId;
    private List<Integer> branchIds;
}
