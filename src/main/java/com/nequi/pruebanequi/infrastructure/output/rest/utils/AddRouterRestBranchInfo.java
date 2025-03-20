package com.nequi.pruebanequi.infrastructure.output.rest.utils;

import com.nequi.pruebanequi.application.dto.BranchProductUpdateRequestDto;
import com.nequi.pruebanequi.application.dto.BranchRequestDTO;
import com.nequi.pruebanequi.domain.model.models.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
                path = "/branch/",
                operation =
                @Operation(
                        description = "Add branch",
                        operationId = "addBranch",
                        tags = "branch",
                        requestBody =
                        @RequestBody(
                                description = "Branch to add",
                                required = true,
                                content = @Content(schema = @Schema(implementation = BranchRequestDTO.class,
                                        requiredProperties = {"name", "products"}))),
                        responses = {
                                @ApiResponse(
                                        responseCode = "200",
                                        description = "Add Branch response",
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
                method = RequestMethod.PUT,
                path = "/branch/update",
                operation =
                @Operation(
                        description = "Update branch",
                        operationId = "updateBranch",
                        tags = "updateBranch",
                        requestBody =
                        @RequestBody(
                                description = "Branch to update",
                                required = true,
                                content = @Content(schema = @Schema(implementation = BranchProductUpdateRequestDto.class,
                                        requiredProperties = {"branchId", "product"}))),
                        responses = {
                                @ApiResponse(
                                        responseCode = "200",
                                        description = "Update Branch response",
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
                method = RequestMethod.GET,
                path = "/branch/{branchId}/products",
                operation = @Operation(
                        description = "Get products by branch ID",
                        operationId = "getProductsByBranchId",
                        tags = "Branch Product",
                        parameters = @Parameter(
                                name = "branchId",
                                description = "ID of the branch",
                                required = true,
                                in = ParameterIn.PATH,
                                schema = @Schema(type = "integer", example = "1")
                        ),
                        responses = {
                                @ApiResponse(
                                        responseCode = "200",
                                        description = "Successfully retrieved products",
                                        content = @Content(
                                                mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                array = @ArraySchema(schema = @Schema(implementation = Product.class))
                                        )
                                ),
                                @ApiResponse(
                                        responseCode = "400",
                                        description = "Invalid branch ID",
                                        content = @Content(
                                                mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                schema = @Schema(implementation = ErrorResponse.class)
                                        )
                                ),
                                @ApiResponse(
                                        responseCode = "404",
                                        description = "Branch not found",
                                        content = @Content(
                                                mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                schema = @Schema(implementation = ErrorResponse.class))
                                        )
                        }))
})
public @interface AddRouterRestBranchInfo {
}
