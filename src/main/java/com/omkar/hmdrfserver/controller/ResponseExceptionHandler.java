package com.omkar.hmdrfserver.controller;

import com.omkar.hmdrfserver.exception.InsufficientBalance;
import com.omkar.hmdrfserver.exchanges.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class ResponseExceptionHandler {

    @ExceptionHandler(value = { BadCredentialsException.class })
    public ApiResponse handleBadCredentials(BadCredentialsException ex, WebRequest request) {
        return new ApiResponse(false,ex.getMessage());
    }

    @ExceptionHandler(value = { InsufficientBalance.class })
    public ApiResponse handleInsufficientBalance(InsufficientBalance ex, WebRequest request) {
        return new ApiResponse(false,ex.getMessage());
    }
}