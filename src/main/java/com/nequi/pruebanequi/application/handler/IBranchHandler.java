package com.nequi.pruebanequi.application.handler;

import com.nequi.pruebanequi.application.dto.BranchCreatedResponseDto;
import com.nequi.pruebanequi.application.dto.BranchProductCreateRequestDto;
import com.nequi.pruebanequi.application.dto.BranchProductResponseDto;
import com.nequi.pruebanequi.application.dto.BranchProductUpdateRequestDto;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface IBranchHandler {
    Mono<BranchCreatedResponseDto> createBranch(BranchProductCreateRequestDto branchProductCreateRequestDto);

    Mono<Void> updateBranchProducts(BranchProductUpdateRequestDto branchProductUpdateRequestDto);

    Mono<BranchProductResponseDto> getBranchProductsByBranchId(Integer integer);
}
