package com.dev.emailService.exception;

public class CustomException extends RuntimeException {

    Integer mErrorCode;
    String mMessage;

    public CustomException(Integer errorCode, String message) {
        super(errorCode.toString(), new Throwable(message));
        mErrorCode = errorCode;
        mMessage = message;
    }

    public Integer getErrorCode() {
        return mErrorCode;
    }

    public String getMessage() {
        return mMessage;
    }
}
