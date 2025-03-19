package com.nequi.pruebanequi.application.handler;

import com.nequi.pruebanequi.application.dto.FranchiseCreateRequestDTO;
import com.nequi.pruebanequi.application.dto.FranchiseCreatedResponseDTO;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface IFranchiseHandler {
    Mono<FranchiseCreatedResponseDTO> createFranchise(FranchiseCreateRequestDTO request);
}
