package com.nequi.pruebanequi.infrastructure.output.rest.controller;


import com.nequi.pruebanequi.application.handler.impl.BranchHandlerImpl;
import com.nequi.pruebanequi.infrastructure.output.rest.utils.AddRouterRestBranchInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.nequi.pruebanequi.infrastructure.output.rest.constants.Constants.BRANCH_ROUTER_FUNCTION;
import static com.nequi.pruebanequi.infrastructure.output.rest.constants.Constants.PATH_BRANCH;
import static com.nequi.pruebanequi.infrastructure.output.rest.constants.Constants.PATH_GET_BRANCH_PATTERN;
import static com.nequi.pruebanequi.infrastructure.output.rest.constants.Constants.PATH_PATTERN;
import static com.nequi.pruebanequi.infrastructure.output.rest.constants.Constants.PATH_UPDATE_PATTERN;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;

@Configuration
public class RouterRestBranch {
    @Bean(name = BRANCH_ROUTER_FUNCTION)
    @AddRouterRestBranchInfo
    public RouterFunction<ServerResponse> routerFunction(BranchHandlerImpl handler) {
        return RouterFunctions.nest(
                path(PATH_BRANCH),
                RouterFunctions.route(POST(PATH_PATTERN), handler::createBranch)
                        .andRoute(PUT(PATH_UPDATE_PATTERN), handler::updateBranchProducts)
                        .andRoute(GET(PATH_GET_BRANCH_PATTERN), handler::getBranchProductsByBranchId)
        );
    }
}
