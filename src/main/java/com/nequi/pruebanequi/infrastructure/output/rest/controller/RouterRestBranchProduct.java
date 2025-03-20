package com.nequi.pruebanequi.infrastructure.output.rest.controller;


import com.nequi.pruebanequi.application.handler.impl.BranchProductHandlerImpl;
import com.nequi.pruebanequi.infrastructure.output.rest.utils.AddRouterBranchProductInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.nequi.pruebanequi.infrastructure.output.rest.constants.Constants.BRANCH_PRODUCT_ROUTER_FUNCTION;
import static com.nequi.pruebanequi.infrastructure.output.rest.constants.Constants.PATH_BRANCH_PRODUCT;
import static com.nequi.pruebanequi.infrastructure.output.rest.constants.Constants.PATH_PATTERN;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;

@Configuration
public class RouterRestBranchProduct {
    @Bean(name = BRANCH_PRODUCT_ROUTER_FUNCTION)
    @AddRouterBranchProductInfo
    public RouterFunction<ServerResponse> routerFunction(BranchProductHandlerImpl handler) {
        return RouterFunctions.nest(
                path(PATH_BRANCH_PRODUCT),
                RouterFunctions.route(POST(PATH_PATTERN), handler::associateBranchProduct)
        );
    }
}
