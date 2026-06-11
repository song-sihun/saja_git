package org.lion.restexam.config;

public enum ExceptionType {
    TodoNotFound("Not Found Todo"),
    ;

    private final String message;
    ExceptionType(String message){
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }
}
