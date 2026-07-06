package org.lion.todo.config.exception;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum JwtExceptionCode {

    UNKNOWN_ERROR("UNKNOWN_ERROR", "알수없어요ㅠㅠ"),
    NOT_FOUND_TOKEN("NOT_FOUND_TOKEN","Headers에서 토큰 형식의 값을 찾지 못했어요."),
    INVALID_TOKEN("INVALID_TOKEN","유효하지 않은 토큰"),
    EXPIRED_TOKEN("EXPIRED_TOKEN","기간이 만료된 토큰"),
    UNSUPPORTED_TOKEN("UNSUPPORTED_TOKEN","지원하지 않는 토큰");

    private final String code;
    private final String message;

    JwtExceptionCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static JwtExceptionCode fromCode(String code) {
        return  Arrays.stream(JwtExceptionCode.values())
                .filter(c -> c.getCode().equals(code)).findFirst().orElse(null);
    }

}