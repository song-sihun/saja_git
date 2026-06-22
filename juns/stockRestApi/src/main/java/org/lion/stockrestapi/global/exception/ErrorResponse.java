package org.lion.stockrestapi.global.exception;

public record ErrorResponse(int status, String error, String message) {
}