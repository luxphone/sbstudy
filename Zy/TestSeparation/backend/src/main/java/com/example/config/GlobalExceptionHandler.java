package com.example.config;

import com.example.entity.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result<Void> exceptionHandler(Exception e) {
        e.printStackTrace();
        return Result.error("network error");
    }
}

