package com.nequi.pruebanequi.infrastructure.output.rest.utils;


import com.nequi.pruebanequi.application.dto.ProductRequestDTO;
import com.nequi.pruebanequi.domain.model.models.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.http.MediaType;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@RouterOperations({
        @RouterOperation(
                method = RequestMethod.POST,
                path = "/product/",
                operation =
                @Operation(
                        description = "Add product",
                        operationId = "addProduct",
                        tags = "product",
                        requestBody =
                        @RequestBody(
                                description = "Product to add",
                                required = true,
                                content = @Content(schema = @Schema(implementation = ProductRequestDTO.class,
                                        requiredProperties = {"name", "stock"}))),
                        responses = {
                                @ApiResponse(
                                        responseCode = "200",
                                        description = "Add Product response",
                                        content = {
                                                @Content(
                                                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                        schema = @Schema(implementation = Product.class))
                                        }),
                                @ApiResponse(
                                        responseCode = "400",
                                        description = "Bad Request response",
                                        content = {
                                                @Content(
                                                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                        schema = @Schema(implementation = ErrorResponse.class))
                                        })
                        })),
        @RouterOperation(
                method = RequestMethod.POST,
                path = "/product/{id}",
                operation =
                @Operation(
                        description = "Delete product",
                        operationId = "deleteProduct",
                        tags = "product",
                        requestBody =
                        @RequestBody(
                                description = "Product to delete",
                                required = true,
                                content = @Content(schema = @Schema(implementation = ProductRequestDTO.class,
                                        requiredProperties = {"{id}"}))),
                        responses = {
                                @ApiResponse(
                                        responseCode = "200",
                                        description = "Delete Product response",
                                        content = {
                                                @Content(
                                                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                        schema = @Schema(implementation = Product.class))
                                        }),
                                @ApiResponse(
                                        responseCode = "400",
                                        description = "Bad Request response",
                                        content = {
                                                @Content(
                                                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                        schema = @Schema(implementation = ErrorResponse.class))
                                        })
                        }))
})
public @interface AddRouterRestProductInfo {
}
