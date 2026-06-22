package org.lion.minirestapi.base.handler;

import lombok.Getter;

@Getter
public enum ErrorString {
    USER_NOT_FOUND("유저를 찾을 수 없습니다."),
    POST_NOT_FOUND("게시글울 찾을 수 없습니다."),
    COMMENT_NOT_FOUND("댓글울 찾을 수 없습니다."),
    ACCESS_DENIED("권한이 없습니다."),
    DUPLICATED("이미 사용 중인 아이디/이메일 입니다."),
    ROLE_NOT_FOUND("롤을 찾을 수 없습니다."),
    ;
    private final String message;

    ErrorString(String message) {
        this.message = message;
    }
}
