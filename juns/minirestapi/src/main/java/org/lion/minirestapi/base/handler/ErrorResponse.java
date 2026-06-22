package org.lion.minirestapi.base.handler;

public record ErrorResponse(int status, String error, String message) {
}
