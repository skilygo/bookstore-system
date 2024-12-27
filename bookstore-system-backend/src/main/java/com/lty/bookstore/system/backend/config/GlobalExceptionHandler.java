package com.lty.bookstore.system.backend.config;

import com.lty.bookstore.system.backend.base.Response;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle generic Exception
     */
    @ExceptionHandler(Exception.class)
    public Response<?> handleException(Exception e) {
        return Response.fail("An unexpected error occurred: " + e.getMessage());
    }
}
