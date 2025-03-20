package com.nequi.pruebanequi.infrastructure.output.rest.controller;


import com.nequi.pruebanequi.application.handler.impl.FranchiseBranchHandlerImpl;
import com.nequi.pruebanequi.infrastructure.output.rest.utils.AddRouterRestFranchiseBranchInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.nequi.pruebanequi.infrastructure.output.rest.constants.Constants.FRANCHISE_BRANCH_ROUTER_FUNCTION;
import static com.nequi.pruebanequi.infrastructure.output.rest.constants.Constants.PATH_FRANCHISE_BRANCH_PRODUCT;
import static com.nequi.pruebanequi.infrastructure.output.rest.constants.Constants.PATH_PATTERN;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;

@Configuration
public class RouterRestFranchiseBranch {
    @Bean(name = FRANCHISE_BRANCH_ROUTER_FUNCTION)
    @AddRouterRestFranchiseBranchInfo
    public RouterFunction<ServerResponse> routerFunction(FranchiseBranchHandlerImpl handler) {
        return RouterFunctions.nest(
                path(PATH_FRANCHISE_BRANCH_PRODUCT),
                RouterFunctions.route(POST(PATH_PATTERN), handler::associateFranchiseToBranch)
        );
    }
}
