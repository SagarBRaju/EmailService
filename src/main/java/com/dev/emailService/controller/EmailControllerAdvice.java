package com.dev.emailService.controller;

import com.dev.emailService.exception.CustomException;
import com.dev.emailService.helper.BasicResponse;
import com.dev.emailService.service.EmailServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class EmailControllerAdvice {

    private final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @ExceptionHandler(CustomException.class)
    public Map<String, Object> handleCustomException(CustomException exception) {
        logger.error(exception.getMessage());
        return new BasicResponse(exception.getErrorCode(), exception.getMessage(), null).error();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> handleCustomException(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        return new BasicResponse(801, exception.getLocalizedMessage(), null).error();
    }
}
