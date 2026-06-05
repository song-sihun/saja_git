package jdbc03;

public enum ErrorCode {
    NOT_FOUND_DEPT("부서를 찾을 수 없습니다."),
    INVALID_DEPT_NAME("부서명이 올바르지 않습니다."),
    INVALID_DEPT_LOCATION( "부서 위치가 올바르지 않습니다."),
    INVALID_DEPT_ID("부서 아이디가 올바르지 않습니다."),
    DEPT_INSERT_FAILED("부서 등록에 실패했습니다."),
    DEPT_UPDATE_FAILED("부서 수정에 실패했습니다."),
    DEPT_DELETE_FAILED("부서 삭제에 실패했습니다.");

    private final String message;

    ErrorCode(String s) {
        this.message = s;
    }

    public String getMessage() {
        return message;
    }
}
