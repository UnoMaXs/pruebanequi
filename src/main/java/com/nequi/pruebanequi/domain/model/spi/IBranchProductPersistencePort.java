package com.nequi.pruebanequi.domain.model.spi;


import com.nequi.pruebanequi.domain.model.models.BranchProduct;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IBranchProductPersistencePort {
    Mono<BranchProduct> saveProductBranch(BranchProduct branchProduct);
    Mono<List<BranchProduct>>findBranchProductByBranchId(Integer branchId);
}
