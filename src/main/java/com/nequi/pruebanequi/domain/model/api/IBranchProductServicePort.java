package com.nequi.pruebanequi.domain.model.api;


import com.nequi.pruebanequi.domain.model.models.BranchProduct;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IBranchProductServicePort {
    Mono<BranchProduct> associateProductToBranch(BranchProduct branchProduct);
    Mono<List<BranchProduct>> getBranchProductByBranchId(Integer branchId);
}
