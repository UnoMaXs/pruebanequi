package com.nequi.pruebanequi.infrastructure.output.rest.controller;

import com.nequi.pruebanequi.application.handler.impl.ProductHandlerImpl;
import com.nequi.pruebanequi.infrastructure.output.rest.utils.AddRouterRestProductInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.nequi.pruebanequi.infrastructure.output.rest.constants.Constants.PATH_DELETE_PATTERN;
import static com.nequi.pruebanequi.infrastructure.output.rest.constants.Constants.PATH_PATTERN;
import static com.nequi.pruebanequi.infrastructure.output.rest.constants.Constants.PATH_PRODUCT;
import static com.nequi.pruebanequi.infrastructure.output.rest.constants.Constants.PRODUCT_ROUTER_FUNCTION;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;

@Configuration
public class RouterRestProduct {
    @Bean(PRODUCT_ROUTER_FUNCTION)
    @AddRouterRestProductInfo
    public RouterFunction<ServerResponse> routerFunction(ProductHandlerImpl handler) {
        return RouterFunctions.nest(
                path(PATH_PRODUCT),
                RouterFunctions.route(POST(PATH_PATTERN), handler::createProduct)
                        .andRoute(DELETE(PATH_DELETE_PATTERN), handler::deleteProduct)
        );
    }
}
