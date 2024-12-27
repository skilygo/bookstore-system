package com.lty.bookstore.system.backend.exception;

public class AuthenticationFailedException extends RuntimeException {

    public AuthenticationFailedException() {
        super("Server failed to authenticate the request.");
    }
}
