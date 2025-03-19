package com.nequi.pruebanequi.domain.model.api;

import com.nequi.pruebanequi.domain.model.models.Branch;
import reactor.core.publisher.Mono;

public interface IBranchServicePort {
    Mono<Branch> createBranch(Branch branch);
    Mono<Void> deleteBranchById(Integer id);
    Mono<Branch> getBranchById(Integer branchId);
}
