package com.nequi.pruebanequi.application.handler;

import com.nequi.pruebanequi.application.dto.BranchProductRequestDTO;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface IBranchProductHandler {
    Mono<Void> associateBranchProduct(BranchProductRequestDTO branchProductRequestDTO);
}
