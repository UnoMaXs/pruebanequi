package com.nequi.pruebanequi.infrastructure.output.rest.exception;

import lombok.Builder;

@Builder
public record ErrorResponseBodyDTO(String message){
}
