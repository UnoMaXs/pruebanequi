package com.nequi.pruebanequi.application.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface IBranchProductHandler {
    Mono<ServerResponse> associateBranchProduct(ServerRequest request);
}
