package com.nequi.pruebanequi.infrastructure.output.rest.controller;


import com.nequi.pruebanequi.application.handler.impl.FranchiseHandlerImpl;
import com.nequi.pruebanequi.infrastructure.output.rest.utils.AddRouterRestFranchiseInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;


import static com.nequi.pruebanequi.infrastructure.output.rest.constants.Constants.FRANCHISE_ROUTER_FUNCTION;
import static com.nequi.pruebanequi.infrastructure.output.rest.constants.Constants.PATH_FRANCHISE;
import static com.nequi.pruebanequi.infrastructure.output.rest.constants.Constants.PATH_PATTERN;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;

@Configuration
public class RouterRestFranchise {
    @Bean(name = FRANCHISE_ROUTER_FUNCTION)
    @AddRouterRestFranchiseInfo
    public RouterFunction<ServerResponse> routerFunction(FranchiseHandlerImpl handler) {
        return RouterFunctions.nest(
                path(PATH_FRANCHISE),
                RouterFunctions.route(POST(PATH_PATTERN), handler::createFranchise)
        );
    }
}
