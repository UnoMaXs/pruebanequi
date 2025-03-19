package com.nequi.pruebanequi.application.handler;

import com.nequi.pruebanequi.application.dto.FranchiseBranchRequestDTO;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface IFranchiseBranchHandler {
    Mono<Void> associateFranchiseToBranch(FranchiseBranchRequestDTO franchiseBranchRequestDTO);
}
