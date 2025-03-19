package com.nequi.pruebanequi.application.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface IBranchHandler {
    Mono<ServerResponse> createBranch(ServerRequest request);

    Mono<ServerResponse> updateBranchProducts(ServerRequest request);

    Mono<ServerResponse> getBranchProductsByBranchId(ServerRequest request);
}
