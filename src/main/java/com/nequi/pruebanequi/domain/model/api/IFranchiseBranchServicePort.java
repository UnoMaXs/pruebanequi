package com.nequi.pruebanequi.domain.model.api;

import com.nequi.pruebanequi.domain.model.models.FranchiseBranch;
import reactor.core.publisher.Mono;

public interface IFranchiseBranchServicePort {
    Mono<Void> associateBranchToFranchise(FranchiseBranch franchiseBranch);
}
