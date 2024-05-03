package com.example.Login.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@NoArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION("Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY("Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED("User existed", HttpStatus.BAD_REQUEST),
    INVALID_NAME("Username must be at least 3 characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD("Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED("User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED("Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("You do not have permission", HttpStatus.FORBIDDEN),
    EMAIL_INVALID("Email must has @gmail.com", HttpStatus.BAD_REQUEST),
    INVALID_PHONE("Phone over 10 characters, starting with 0", HttpStatus.BAD_REQUEST),
    POST_NOT_FOUND("Post not found", HttpStatus.BAD_REQUEST),
    CMT_NOT_FOUND("Error cmt", HttpStatus.BAD_REQUEST),
    COMMENT_NOT_FOUND("Not find id comment", HttpStatus.BAD_REQUEST),
    PASSWORD_FALSE("Wrong password", HttpStatus.BAD_REQUEST),
    ;

    ErrorCode(String message, HttpStatusCode statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    private String message;
    private HttpStatusCode statusCode;
}