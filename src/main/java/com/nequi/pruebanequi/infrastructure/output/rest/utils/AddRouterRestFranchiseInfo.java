package com.nequi.pruebanequi.infrastructure.output.rest.utils;

import com.nequi.pruebanequi.application.dto.BranchRequestDTO;
import com.nequi.pruebanequi.application.dto.FranchiseCreateRequestDTO;
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
                path = "/franchise/",
                operation =
                @Operation(
                        description = "Add franchise",
                        operationId = "addFranchise",
                        tags = "franchise",
                        requestBody =
                        @RequestBody(
                                description = "Franchise to add",
                                required = true,
                                content = @Content(schema = @Schema(implementation = FranchiseCreateRequestDTO.class,
                                        requiredProperties = {"name", "products"}))),
                        responses = {
                                @ApiResponse(
                                        responseCode = "200",
                                        description = "Add Franchise response",
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
public @interface AddRouterRestFranchiseInfo {
}
