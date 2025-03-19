package com.nequi.pruebanequi.infrastructure.input.jpa.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DBErrorMessage {
    PRODUCT_ALREADY_EXISTS("Producto ya existe", 400),
    BRANCH_NOT_FOUND("Sucursal no encontrada", 400),
    UNEXPECTED_EXCEPTION("Sucedió un error inesperado", 500),
    FAILED_CONNECTION("Falló la conexión", 500),
    BRANCH_ALREADY_EXISTS("Esta sucursal ya existe", 400),
    PRODUCT_NOT_FOUND("Producto no encontrado", 404),
    FRANCHISE_NOT_FOUND("Franquicia no encontrado", 404);
    private final String message;
    private final int httpStatusCode;
}
