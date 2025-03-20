package com.nequi.pruebanequi.application.handler.impl;

import com.nequi.pruebanequi.application.dto.BranchProductResponseDto;
import com.nequi.pruebanequi.application.dto.ProductResponseDto;
import com.nequi.pruebanequi.application.handler.IBranchHandler;
import com.nequi.pruebanequi.domain.model.api.IBranchProductServicePort;
import com.nequi.pruebanequi.domain.model.api.IBranchServicePort;
import com.nequi.pruebanequi.domain.model.api.IProductServicePort;
import com.nequi.pruebanequi.domain.model.models.Branch;
import com.nequi.pruebanequi.domain.model.models.BranchProduct;
import com.nequi.pruebanequi.domain.model.models.Product;
import com.nequi.pruebanequi.domain.usecase.exceptions.BusinessErrorMessage;
import com.nequi.pruebanequi.domain.usecase.exceptions.BusinessException;
import com.nequi.pruebanequi.application.dto.BranchCreatedResponseDto;
import com.nequi.pruebanequi.application.dto.BranchProductCreateRequestDto;
import com.nequi.pruebanequi.application.dto.BranchProductRequestDTO;
import com.nequi.pruebanequi.application.dto.BranchProductUpdateRequestDto;
import com.nequi.pruebanequi.application.dto.ProductDto;
import com.nequi.pruebanequi.infrastructure.output.rest.mappers.IBranchProductRequestMapper;
import com.nequi.pruebanequi.infrastructure.output.rest.mappers.IBranchRequestMapper;
import com.nequi.pruebanequi.infrastructure.output.rest.mappers.IProductRequestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class BranchHandlerImpl implements IBranchHandler{
    private final IBranchServicePort branchServicePort;
    private final IBranchProductServicePort branchProductServicePort;
    private final IProductServicePort productServicePort;
    private final IBranchRequestMapper branchRequestMapper;
    private final IProductRequestMapper productRequestMapper;
    private final IBranchProductRequestMapper branchProductRequestMapper;
    @Override
    public Mono<ServerResponse> createBranch(ServerRequest request) {
        return request.bodyToMono(BranchProductCreateRequestDto.class)
                .switchIfEmpty(Mono.error(new BusinessException(BusinessErrorMessage.EMPTY_REQUEST_BODY)))
                .flatMap(branchRequest -> {
                    Branch branch = branchRequestMapper.toDomain(branchRequest);
                    List<Integer> productIds = branchRequest.getProductIds();

                    return branchServicePort.createBranch(branch)
                            .flatMap(savedBranch -> {

                                BranchProductRequestDTO branchRequestDto = new BranchProductRequestDTO();
                                branchRequestDto.setBranchId(savedBranch.getId());
                                branchRequestDto.setProductIds(productIds);

                                return associateProductsWithBranch(branchRequestDto)
                                        .then(ServerResponse.status(HttpStatus.CREATED).bodyValue(new BranchCreatedResponseDto(
                                                savedBranch.getName()
                                        )))
                                        .onErrorResume(associationError ->
                                                branchServicePort.deleteBranchById(savedBranch.getId())
                                                        .then(Mono.error(new BusinessException(BusinessErrorMessage.ASSOCIATE_BRANCH_PRODUCT_ERROR))));
                            });

                })
                .onErrorResume(BusinessException.class, e ->
                        ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(e.getMessage()));
    }


    private Mono<Void> associateProductsWithBranch(BranchProductRequestDTO branchProductRequestDto) {
        List<BranchProduct> branchProducts = branchProductRequestMapper.toDomain(branchProductRequestDto);
        return Flux.fromIterable(branchProducts)
                .flatMap(branchProductServicePort::associateProductToBranch)
                .then();
    }

    @Override
    public Mono<ServerResponse> updateBranchProducts(ServerRequest request) {
        return request.bodyToMono(BranchProductUpdateRequestDto.class)
                .switchIfEmpty(Mono.error(new BusinessException(BusinessErrorMessage.EMPTY_REQUEST_BODY)))
                .flatMap(branchProductUpdateRequest -> {
                    Integer branchId = branchProductUpdateRequest.getBranchId();
                    List<ProductDto> productDtos = branchProductUpdateRequest.getProductList();

                    return validateAndUpdateProducts(branchId, productDtos);
                });
    }

    private Mono<ServerResponse> validateAndUpdateProducts(Integer branchId, List<ProductDto> productDtos) {
        List<Product> productsToUpdate = productDtos.stream()
                .map(productRequestMapper::toProductDomain)
                .toList();

        return getProductsByBranch(branchId)
                .flatMap(branchProducts -> {
                    List<Product> validProductsToUpdate = filterValidProducts(productsToUpdate, branchProducts);

                    return Flux.fromIterable(validProductsToUpdate)
                            .flatMap(product -> productServicePort.updateProduct(product)
                                    .onErrorResume(BusinessException.class, e ->
                                            Mono.error(new BusinessException(BusinessErrorMessage.UPDATE_FAILED))
                                    )
                            )
                            .then(ServerResponse.ok().build());
                });
    }

    private Mono<List<BranchProduct>> getProductsByBranch(Integer branchId) {
        return branchProductServicePort.getBranchProductByBranchId(branchId);
    }

    private List<Product> filterValidProducts(List<Product> productsToUpdate, List<BranchProduct> branchProducts) {
        return productsToUpdate.stream()
                .filter(product -> branchProducts.stream()
                        .anyMatch(branchProduct -> branchProduct.getProductId().equals(product.getId())))
                .toList();
    }

    @Override
    public Mono<ServerResponse> getBranchProductsByBranchId(ServerRequest request) {
        Integer branchId = Integer.valueOf(request.pathVariable("id"));

        return getBranchById(branchId)
                .zipWith(getBranchProducts(branchId))
                .flatMap(tuple -> {
                    Branch branch = tuple.getT1();
                    List<BranchProduct> branchProducts = tuple.getT2();

                    List<Integer> productIds = extractProductIds(branchProducts);

                    return getProductsByIds(productIds)
                            .flatMap(products -> buildBranchProductResponse(branch, products))
                            .flatMap(responseDto -> ServerResponse.ok().bodyValue(responseDto));
                })
                .onErrorResume(this::handleError);
    }
    private Mono<Branch> getBranchById(Integer branchId) {
        return branchServicePort.getBranchById(branchId)
                .switchIfEmpty(Mono.error(new BusinessException(BusinessErrorMessage.BRANCH_NOT_FOUND)));
    }
    private Mono<List<BranchProduct>> getBranchProducts(Integer branchId) {
        return branchProductServicePort.getBranchProductByBranchId(branchId)
                .switchIfEmpty(Mono.error(new BusinessException(BusinessErrorMessage.PRODUCT_NOT_FOUND)));
    }

    private List<Integer> extractProductIds(List<BranchProduct> branchProducts) {
        return branchProducts.stream()
                .map(BranchProduct::getProductId)
                .toList();
    }

    private Mono<List<Product>> getProductsByIds(List<Integer> productIds) {
        return Flux.fromIterable(productIds)
                .flatMap(productServicePort::getProductById)
                .collectList()
                .switchIfEmpty(Mono.error(new BusinessException(BusinessErrorMessage.PRODUCT_NOT_FOUND)));
    }
    private Mono<BranchProductResponseDto> buildBranchProductResponse(Branch branch, List<Product> products) {
        List<ProductResponseDto> productResponseDtos = products.stream()
                .map(this::mapToProductResponseDto)
                .toList();

        BranchProductResponseDto responseDto = new BranchProductResponseDto();
        responseDto.setBranchName(branch.getName());
        responseDto.setProductResponseDtoList(productResponseDtos);

        return Mono.just(responseDto);
    }

    private ProductResponseDto mapToProductResponseDto(Product product) {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setName(product.getName());
        productResponseDto.setStock(product.getStock());
        return productResponseDto;
    }
    private Mono<ServerResponse> handleError(Throwable e) {
        if (e instanceof BusinessException) {
            return ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(e.getMessage());
        }
        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


}
