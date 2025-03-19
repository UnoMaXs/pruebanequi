package com.nequi.pruebanequi.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BranchProductRequestDTO {
    private Integer branchId;
    private List<Integer> productIds;
}
