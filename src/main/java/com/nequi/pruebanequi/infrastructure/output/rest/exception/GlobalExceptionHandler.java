package com.nequi.pruebanequi.infrastructure.output.rest.exception;

import com.nequi.pruebanequi.domain.usecase.exceptions.BusinessErrorMessage;
import com.nequi.pruebanequi.domain.usecase.exceptions.BusinessException;
import com.nequi.pruebanequi.infrastructure.input.jpa.exceptions.DBExceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import static com.nequi.pruebanequi.infrastructure.output.rest.constants.Constants.HANDLING_ERROR;


@Order(-2)
@Component
public class GlobalExceptionHandler extends AbstractErrorWebExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    public GlobalExceptionHandler(ErrorAttributes errorAttributes,
                                  ApplicationContext applicationContext,
                                  ServerCodecConfigurer serverCodecConfigurer) {
        super(errorAttributes, new WebProperties.Resources(), applicationContext);
        this.setMessageWriters(serverCodecConfigurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::buildErrorResponse);
    }

    private Mono<ServerResponse> buildErrorResponse(ServerRequest serverRequest) {
        return Mono.just(serverRequest)
                .map(this::getError)
                .doOnNext(error -> log.error(HANDLING_ERROR, error.getClass().getName(), error))
                .flatMap(Mono::error)
                .onErrorResume(BusinessException.class, this::buildResponseBody)
                .onErrorResume(DBExceptions.class, this::buildResponseBody)
                .onErrorResume(this::buildResponseBody)
                .cast(Tuple2.class)
                .flatMap(tuple -> this.buildResponse((ErrorResponseBodyDTO) tuple.getT1(), (HttpStatus) tuple.getT2()));
    }


    private Mono<Tuple2<ErrorResponseBodyDTO, HttpStatus>> buildResponseBody(DBExceptions dbException) {
        return Mono.just(ErrorResponseBodyDTO.builder()
                        .message(dbException.getErrorMessage().getMessage())
                        .build())
                .zipWith(Mono.just(HttpStatus.CONFLICT));
    }

    private Mono<Tuple2<ErrorResponseBodyDTO, HttpStatus>> buildResponseBody(BusinessException businessException) {
        return Mono.just(ErrorResponseBodyDTO.builder()
                        .message(businessException.getErrorMessage().getMessage())
                        .build())
                .zipWith(Mono.just(HttpStatus
                        .resolve(businessException.getErrorMessage().getHttpStatusCode())));
    }

    private Mono<Tuple2<ErrorResponseBodyDTO, HttpStatus>> buildResponseBody(Throwable throwable) {
        return Mono.just(ErrorResponseBodyDTO.builder()
                        .message(BusinessErrorMessage.UNEXPECTED_EXCEPTION.getMessage())
                        .build())
                .zipWith(Mono.just(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    public Mono<ServerResponse> buildResponse(ErrorResponseBodyDTO body, HttpStatus httpStatus) {
        return ServerResponse.status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(body), ErrorResponseBodyDTO.class);
    }

}
